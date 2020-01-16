package university;

import university.model.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class DataService {
    Connection connection;

    DataService(String dbname) {
        _createConnection(dbname);
    }

    public List<Person> getProfessors() {
        return execSimpleQuery("select * from professors", rset -> {
                    try {
                        return new Person(rset.getInt(1),
                                rset.getString(2),
                                rset.getString(3),
                                rset.getString(4),
                                rset.getString(5));
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return null;
                    }});
    }

    <T> List<T> execSimpleQuery(String query, Function<ResultSet, T> callback) {
        try {
            Statement s = connection.createStatement();
            ResultSet r = s.executeQuery(query);
            List<T> result = new ArrayList<>();
            while (r.next()) {
                T resultElmt = callback.apply(r);
                result.add(resultElmt);
            }
            s.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void _createConnection(String dbname) {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + dbname,
                    "root",
                    "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
