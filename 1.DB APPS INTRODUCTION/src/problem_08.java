import java.sql.*;
import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;

public class problem_08 {
    public static void main(String[] args) throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "plazma");
        String connectionQuery = "jdbc:mysql://localhost:3306/minions_db";
        Connection connection = DriverManager.getConnection(connectionQuery, props);

        Scanner scanner = new Scanner(System.in);
        int[] ids = Arrays.stream(scanner.nextLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
        int currentId = 0;
        String changeData = "UPDATE minions SET age = age+1, `name` = LOWER(`name`) WHERE id = ?";
        PreparedStatement increaseAgeStatement = connection.prepareStatement(changeData);
        for (int i = 0; i < ids.length; i++) {
            currentId = ids[i];
            increaseAgeStatement.setInt(1, currentId);
            increaseAgeStatement.executeUpdate();
        }
        String printQuery = "SELECT m.name, m.age FROM minions m";
        PreparedStatement printStatement = connection.prepareStatement(printQuery);
        ResultSet resultSet = printStatement.executeQuery();
        while (resultSet.next()){
            System.out.printf("%s %d%n", resultSet.getString("name"), resultSet.getInt("age"));
        }
    }
}
