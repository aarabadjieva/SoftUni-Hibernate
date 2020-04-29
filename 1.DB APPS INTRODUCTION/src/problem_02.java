import java.sql.*;
import java.util.Properties;

public class problem_02 {
        public static void main(String[] args) throws SQLException, ClassNotFoundException {
            Properties properties = new Properties();
            properties.setProperty("user", "root");
            properties.setProperty("password", "plazma");
            String connectionURL = "jdbc:mysql://localhost:3306/minions_db";
            Connection connection = DriverManager.getConnection(connectionURL, properties);

            String query = "SELECT v.name, COUNT(m.id) AS min_count\n" +
                    "FROM villains AS v\n" +
                    "JOIN minions_villains AS mv\n" +
                    "ON v.id = mv.villain_id\n" +
                    "JOIN minions as m\n" +
                    "ON m.id = mv.minion_id\n" +
                    "GROUP BY v.name\n" +
                    "HAVING min_count>?\n" +
                    "ORDER BY min_count DESC ;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, 15);

            ResultSet result = preparedStatement.executeQuery();

            while(result.next()){
                System.out.printf("%s %d",
                        result.getString("name"),
                        result.getInt("min_count")).println();
            }
        }
    }

