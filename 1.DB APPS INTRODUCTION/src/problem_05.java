import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class problem_05 {
    public static void main(String[] args) throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "plazma");
        String connectionURL = "jdbc:mysql://localhost:3306/minions_db";
        Connection connection = DriverManager.getConnection(connectionURL, properties);

        Scanner scanner = new Scanner(System.in);
        String country = scanner.nextLine();

        String townNameUpdate = "UPDATE towns t " +
                "SET t.name = UPPER(t.name) WHERE t.country = ?;";
        PreparedStatement updateStatement = connection.prepareStatement(townNameUpdate);
        updateStatement.setString(1, country);
        updateStatement.execute();
        String towns = "SELECT t.name FROM towns t " +
                "WHERE t.country = ?;";
        PreparedStatement townsStatement = connection.prepareStatement(towns);
        townsStatement.setString(1, country);
        ResultSet townsSet = townsStatement.executeQuery();
        List townNames = new ArrayList();

        while (townsSet.next()) {
            townNames.add(townsSet.getString("name"));
        }
        if (townNames.isEmpty()) {
            System.out.println("No town names were affected.");
        } else {
            System.out.printf("%d town names were affected.%n", townNames.size());
            System.out.println(townNames.toString());
        }
    }
}
