package orm;

import entities.User;
import orm.anotations.Column;
import orm.anotations.PrimaryKey;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class EntityManager<E> implements DbContext<E>{
    private static final String INSERT_STATEMENT = "INSERT INTO %s (%s) VALUES(%s)";
    private static final String UPDATE_STATEMENT = "UPDATE %s SET %s WHERE %s";
    private static final String SELECT_FIRST_WITH_WHERE = "SELECT * FROM %s WHERE 1 %s LIMIT 1";
    private static final String SELECT_ALL_WITH_WHERE = "SELECT * FROM %s WHERE 1 %s";

    private Connection connection;

    public EntityManager(Connection connection) throws SQLException {
        this.connection = connection;
        if(this.ifTableExist()){
            //this.updateTable();
        }else {
           this.createTable();
        }
    }

    private boolean ifTableExist() throws SQLException {
        String query = String.format("SELECT TABLE_NAME FROM information.schema.TABLES WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = '%s'",
                this.getTableName(User.class));
        PreparedStatement statement = this.connection.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        return rs.next();
    }

    public boolean persist(E entity) throws IllegalAccessException, SQLException {
        Field idField = getId(entity.getClass());
        idField.setAccessible(true);
        Object idValue = idField.get(entity);

        if (idValue == null || (int)idValue <=0){
            return doInsert(entity, idField);
        }

        return doUpdate(entity, idField);
    }


    public Iterable<E> find(Class<E> table) throws SQLException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        return find(table, null);
    }

    public Iterable<E> find(Class<E> table, String where) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String tableName = this.getTableName(table);

        Statement statement = connection.createStatement();
        String query = String.format(SELECT_ALL_WITH_WHERE,
                tableName,
                where == null ? "" : "AND " + where);

        ResultSet rs = statement.executeQuery(query);
        List<E> result = new ArrayList<>();
        while (rs.next()){
            E current = this.mapResultToEntity(rs, table);
            result.add(current);
        }
        return result;
    }

    public E findFirst(Class<E> table) throws SQLException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        return findFirst(table, null);
    }

    public E findFirst(Class<E> table, String where) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return find(table,where == null ? " 1 LIMIT 1" : where + " LIMIT 1").iterator().next();
    }

    private E mapResultToEntity(ResultSet rs, Class<E> table) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        E entity = table.getDeclaredConstructor().newInstance();
        Arrays.stream(table.getDeclaredFields())
                .forEach(f->{
                    f.setAccessible(true);
                    String name = this.getNormalizedName(f.getName());
                    Object value = null;
                    try {
                        value = this.getValueFromResultSet(rs, name, f.getType());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    try {
                        f.set(entity, value);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
        return entity;
    }

    private Object getValueFromResultSet(ResultSet rs, String name, Class type) throws SQLException {
        Object result = null;
        if (type == int.class){
            result = rs.getInt(name);
        }else if (type==String.class){
            result = rs.getString(name);
        }else if (type==Date.class){
            result = rs.getDate(name);
        }
        return result;
    }

    private Field getId(Class entity) {
      return   Arrays.stream(entity.getDeclaredFields())
                .filter(f->f.isAnnotationPresent(PrimaryKey.class))
                .findFirst()
                .orElseThrow( ()->
                        new UnsupportedOperationException("Entity does not have primary key")
                );
    }

    private boolean doInsert(E entity, Field idField) throws SQLException {
        String tableName = this.getTableName(entity.getClass());
        String[] tableFields = this.getTableFields(entity);
        String [] tableValues = this.getTableValues(entity);

        String query = String.format(INSERT_STATEMENT, tableName,
                String.join(",", tableFields), String.join(",", tableValues));

        return this.connection.prepareStatement(query).execute();
    }

    private String[] getTableValues(E entity) {
        return Arrays.stream(entity
                .getClass()
                .getDeclaredFields())
                .filter(f->f.isAnnotationPresent(Column.class))
                .map(f-> {
                    f.setAccessible(true);
                    try {
                        Object value = f.get(entity);
                        return "'"+value.toString()+"'";
                    }catch (IllegalAccessException e){
                        e.printStackTrace();
                        return "";
                    }

                })
                .toArray(String[]::new);
    }

    private String[] getTableFields(E entity) {
        return Arrays.stream(entity
                .getClass()
                .getDeclaredFields())
                .filter(f->f.isAnnotationPresent(Column.class))
                .map(f-> {
                    f.setAccessible(true);
                    return this.getNormalizedName(f.getName());
                })
                .toArray(String[]::new);
    }

    private String getNormalizedName(String name) {
        return name.replaceAll("([A-Z])", "_$1").toLowerCase();
    }

    private String getTableName(Class aClass) {
        return aClass.getSimpleName().toLowerCase().concat("s");
    }

    private boolean doUpdate(E entity, Field idField) throws IllegalAccessException, SQLException {
        String tableName = getTableName(entity.getClass());
        String[] tableFields = getTableFields(entity);
        String[] tableValues = getTableValues(entity);

        String[] fieldValuePairs = IntStream.range(0, tableFields.length)
                .mapToObj(i->"`"+tableFields[i]+"`="+tableValues[i])
                .toArray(String[]::new);

        String whereId = "`"+idField.getName()+"`='"+idField.get(entity)+"'";

        String query = String.format(UPDATE_STATEMENT, tableName,
                String.join(",", fieldValuePairs), whereId);
        return this.connection.prepareStatement(query).execute();
    }

    private void createTable(){
    }
}
