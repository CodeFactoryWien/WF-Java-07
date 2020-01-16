package university;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CreateConnection {

    private static Connection connection;
    private  static Statement statement;
    private static ResultSet resultSet;

    public ResultSet resultSet(String query) throws Exception{
        String url = "jdbc:mysql://localhost:3306/cr6_barnabas_doebroessy_school?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String uname = "root";
        String pass = "admin";
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url, uname, pass);
        statement = connection.createStatement();
        resultSet = statement.executeQuery(query);
        return resultSet;
    }
/*    public static Connection giveConnection() throws Exception{
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cr6_barnabas_doebroessy_school?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "admin");
        return connection;
    }
    public static Statement giveStatement() throws Exception{
        statement = connection.createStatement();
        return statement;*/
    }
