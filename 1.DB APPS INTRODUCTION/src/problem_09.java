import java.util.Properties;
import java.sql.*;
import java.util.Scanner;

public class problem_09 {
    public static void main(String[] args) throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "plazma");
        String conQuery = "jdbc:mysql://localhost:3306/minions_db";
        Connection con = DriverManager.getConnection(conQuery, props);

        Scanner scanner = new Scanner(System.in);
        int id = Integer.parseInt(scanner.nextLine());
        String useProcedureQuery = "CALL usp_get_older(?);";
        PreparedStatement preparedStatement = con.prepareStatement(useProcedureQuery);
        preparedStatement.setInt(1, id);
        preparedStatement.executeQuery();
        String printQuery = "SELECT m.name, m.age FROM minions m WHERE m.id = ?;";
        preparedStatement = con.prepareStatement(printQuery);
        preparedStatement.setInt(1,id);
        ResultSet result = preparedStatement.executeQuery();
        while (result.next()){
            System.out.println(result.getString("name")+" "+result.getInt("age"));
        }
    }
}
