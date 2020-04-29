import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class problem_03 {
    public static void main(String[] args) throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "plazma");

        String connectionURL = "jdbc:mysql://localhost:3306/minions_db";
        Connection connection = DriverManager.getConnection(connectionURL, properties);
        Scanner scanner = new Scanner(System.in);
        int input = Integer.parseInt(scanner.nextLine());
        String query = "SELECT v.name FROM villains as v " +
                "WHERE v.id = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, input);
        ResultSet result = preparedStatement.executeQuery();
        if (!result.next()){
            System.out.printf("No villain with ID %d exists in the database.%n", input);
        }else {
            System.out.printf("Villain: %s%n", result.getString("name"));
            String query2 = "SELECT m.name, m.age FROM minions as m\n" +
                    "                    JOIN minions_villains as mv\n" +
                    "                    ON mv.minion_id = m.id\n" +
                    "join villains v on mv.villain_id = v.id\n" +
                    "where villain_id = ?\n" +
                    "group by m.id;";
            PreparedStatement preparedStatement1 = connection.prepareStatement(query2);
            preparedStatement1.setInt(1, input);
            ResultSet resultSet = preparedStatement1.executeQuery();
            int minionNumber = 1;
            while (resultSet.next()){
                System.out.printf("%d. %s %d%n", minionNumber, resultSet.getString("name"),
                        resultSet.getInt("age"));
                minionNumber++;
            }
        }
    }
}
