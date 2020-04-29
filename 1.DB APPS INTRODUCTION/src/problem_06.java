import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class problem_06 {
    public static void main(String[] args) throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "plazma");
        String connectionURL = "jdbc:mysql://localhost:3306/minions_db";
        Connection con = DriverManager.getConnection(connectionURL, properties);

        Scanner scanner = new Scanner(System.in);
        int villainId = Integer.parseInt(scanner.nextLine());
        String villainQuery = "SELECT v.name FROM villains v " +
                "WHERE v.id = ?;";
        PreparedStatement villainStatement = con.prepareStatement(villainQuery);
        villainStatement.setInt(1, villainId);
        ResultSet villainResult = villainStatement.executeQuery();

        if (!villainResult.next()){
            System.out.println("No such villain was found");
        }else {
            String villainName = villainResult.getString("name");
            String countMinions = "SELECT COUNT(mv.minion_id) as minion_count " +
                    "FROM minions_villains mv " +
                    "GROUP BY mv.villain_id " +
                    "HAVING villain_id = ?;";
            PreparedStatement countMinionsStatement = con.prepareStatement(countMinions);
            countMinionsStatement.setInt(1, villainId);
            ResultSet minionCountResult = countMinionsStatement.executeQuery();
            int minionCount = 0;
            while (minionCountResult.next()) {
                minionCount = minionCountResult.getInt("minion_count");
            }
            String releaseMinions = "DELETE " +
                    "FROM minions_villains mv " +
                    "WHERE mv.villain_id = ?;";
            PreparedStatement deleteStatement = con.prepareStatement(releaseMinions);
            deleteStatement.setInt(1, villainId);
            deleteStatement.executeUpdate();
            String deleteVillain = "DELETE FROM villains v WHERE v.id = ?;";
            PreparedStatement deleteVillainStatement = con.prepareStatement(deleteVillain);
            deleteVillainStatement.setInt(1, villainId);
            deleteVillainStatement.executeUpdate();
            System.out.printf("%s was deleted\n" +
                    "%d minions released\n", villainName, minionCount);
        }
    }
}
