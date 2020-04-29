import entities.User;
import orm.Connector;
import orm.EntityManager;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        Connector.createConnection("root", "plazma", "users");
        Connection connection = Connector.getConnection();

        User user = new User("userName1", "1234", 14, Date.valueOf("2017-05-06"));
        EntityManager<User> userManager = new EntityManager<>(connection);

        User found = userManager.findFirst(User.class, "username='userName1'");
        System.out.println(found.getRegistrationDate());

        //userManager.find(User.class, "username='user2'").forEach(u-> System.out.println(u.getUsername()));
    }
}
