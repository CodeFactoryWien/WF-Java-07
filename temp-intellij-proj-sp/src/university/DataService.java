package university;

import university.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataService {
    Connection connection;

    DataService(String dbname) {
        _createConnection(dbname);
    }

    public List<Course> getCourses() {
        return runSimpleQuery(Queries.getCoursesData(),
                rset -> new Course(rset.getInt(1),
                        rset.getString(2),
                        rset.getString(3),
                        CourseType.valueOf(rset.getString(4).toUpperCase()),
                        rset.getString(5),
                        rset.getInt(6)));
    }

    public void addCourseEventDataToCourse(Course course) {
        List<CourseEvent> courseEvents = runSimpleQuery(Queries.getCourseEventsOfCourse(course.getId()), rset ->
                new CourseEvent(rset.getInt(1), rset.getString(2), rset.getDate(3)));
        for (CourseEvent cev : courseEvents) {
            List<CourseUnit> courseUnits = runSimpleQuery(Queries.getCourseUnitsOfCourseEvent(cev.getId()), rset ->
                    new CourseUnit(rset.getInt(1), rset.getDate(2), rset.getTime(3)));
            cev.getCourseUnits().addAll(courseUnits);
            course.addCourseEvent(cev);
        }
    }

    public List<Person> getProfessors() {
        return runSimpleQuery(Queries.getAll("professors"), rset ->
                new Person(rset.getInt(1),
                        rset.getString(2),
                        rset.getString(3),
                        rset.getString(4),
                        rset.getString(5)));
    }

    <T> List<T> runSimpleQuery(String query, FunWithSql<ResultSet, T> resultSetElmtProcessor) {
        try {
            Statement s = connection.createStatement();
            ResultSet r = s.executeQuery(query);
            List<T> result = new ArrayList<>();
            while (r.next()) {
                T resultElmt = resultSetElmtProcessor.apply(r);
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
