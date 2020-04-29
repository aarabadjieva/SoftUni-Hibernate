import com.mysql.cj.protocol.Resultset;

import java.sql.*;
import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;

public class problem_04 {
    public static void main(String[] args) throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "plazma");

        String connectionURL = "jdbc:mysql://localhost:3306/minions_db";
        Connection connection = DriverManager.getConnection(connectionURL, properties);
        Scanner scanner = new Scanner(System.in);

        String[] minionData = scanner.nextLine().split(" ");
        String minionName = minionData[1];
        int age = Integer.parseInt(minionData[2]);
        String city = minionData[3];
        String[] villainData = scanner.nextLine().split(" ");
        String villainName = villainData[1];

        String townQuery = "SELECT t.name FROM towns t WHERE t.name = ?;";
        PreparedStatement townStatement = connection.prepareStatement(townQuery);
        townStatement.setString(1, city);
        ResultSet townResult = townStatement.executeQuery();
        if (!townResult.next()){
            String insertTown = "INSERT INTO towns(name, country) VALUES(?,'Somewhere');";
            PreparedStatement insertTownStatement = connection.prepareStatement(insertTown);
            insertTownStatement.setString(1, city);
            insertTownStatement.executeUpdate();
            System.out.printf("Town %s was added to the database.%n", city);
        }

        String villainQuery = "SELECT v.name FROM villains v WHERE v.name = ?;";
        PreparedStatement villainStatement = connection.prepareStatement(villainQuery);
        villainStatement.setString(1, villainName);
        ResultSet villainResult = villainStatement.executeQuery();
        if (!villainResult.next()){
            String insertVillain = "INSERT INTO villains(name, evilness_factor) VALUES(?, 'evil');";
            PreparedStatement insertVillainStatement = connection.prepareStatement(insertVillain);
            insertVillainStatement.setString(1, villainName);
            insertVillainStatement.executeUpdate();
            System.out.printf("Villain %s was added to the database.%n", villainName);
        }

        String getTownId = "SELECT t.id FROM towns t WHERE t.name = ?;";
        PreparedStatement getTownIdStatement = connection.prepareStatement(getTownId);
        getTownIdStatement.setString(1, city);
        ResultSet townIdResult = getTownIdStatement.executeQuery();
        townIdResult.next();
        int townId = townIdResult.getInt("id");

        String getVillainId = "SELECT v.id FROM villains v WHERE v.name = ?;";
        PreparedStatement getVillainIdStatement = connection.prepareStatement(getVillainId);
        getVillainIdStatement.setString(1, villainName);
        ResultSet villainIdResult = getVillainIdStatement.executeQuery();
        villainIdResult.next();
        int villainId = villainIdResult.getInt("id");

        String addMinion = "INSERT INTO minions(name, age, town_id) VALUES(?, ?, ?);";
        PreparedStatement addMinionStatement = connection.prepareStatement(addMinion);
        addMinionStatement.setString(1, minionName);
        addMinionStatement.setInt(2, age);
        addMinionStatement.setInt(3, townId);
        addMinionStatement.executeUpdate();

        String getMinionId = "SELECT m.id FROM minions m WHERE m.name = ?;";
        PreparedStatement getMinionIdStatement = connection.prepareStatement(getMinionId);
        getMinionIdStatement.setString(1, minionName);
        ResultSet minionIdResult = getMinionIdStatement.executeQuery();
        minionIdResult.next();
        int minionId = minionIdResult.getInt("id");

        String addMinionVillain = "INSERT INTO minions_villains(minion_id, villain_id) VALUES(?, ?);";
        PreparedStatement addMinionVillainStatement = connection.prepareStatement(addMinionVillain);
        addMinionVillainStatement.setInt(1, minionId);
        addMinionVillainStatement.setInt(2, villainId);
        addMinionVillainStatement.executeUpdate();
        System.out.printf("Successfully added %s to be minion of %s%n",minionName, villainName);
    }
}
