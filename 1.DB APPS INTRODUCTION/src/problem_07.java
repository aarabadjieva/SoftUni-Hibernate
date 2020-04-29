import java.sql.Connection;
import java.util.Properties;
import java.sql.*;

public class problem_07 {
    public static void main(String[] args) throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "plazma");
        String conQuery = "jdbc:mysql://localhost:3306/minions_db";
        Connection con = DriverManager.getConnection(conQuery, properties);

        int maxID = 0;
        String getMaxId = "SELECT m.id FROM minions m " +
                "ORDER BY m.id DESC LIMIT 1;";
        PreparedStatement getMaxIdStatement = con.prepareStatement(getMaxId);
        ResultSet maxIDresult = getMaxIdStatement.executeQuery();
        while (maxIDresult.next()) {
            maxID = maxIDresult.getInt("id");
        }

        String getNameQuery = "SELECT m.name FROM minions m " +
                "WHERE m.id = ?;";
        int minID = 1;
        PreparedStatement getNameStatement = con.prepareStatement(getNameQuery);

        while (minID<=maxID){
            getNameStatement.setInt(1, minID);
            ResultSet nameResult = getNameStatement.executeQuery();
            String name = "";
            while (nameResult.next()){
                name = nameResult.getString("name");
            }
            System.out.println(name);
            if (minID==maxID){
                return;
            }else {
                getNameStatement.setInt(1, maxID);
                nameResult = getNameStatement.executeQuery();
                while (nameResult.next()) {
                    name = nameResult.getString("name");
                }
                System.out.println(name);
            }
            minID++;
            maxID--;
        }
    }
}
