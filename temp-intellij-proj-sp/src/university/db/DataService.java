package university.db;

import university.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataService {
    Connection connection;

    public DataService(String dbname) {
        createConnection(dbname);
    }

    public List<Course> getCourses() {
        return runSimpleQuery(ReadQueries.getCoursesData(),
                rset -> new Course(rset.getInt(1),
                        rset.getString(2),
                        rset.getString(3),
                        CourseType.valueOf(rset.getString(4).toUpperCase()),
                        rset.getString(5),
                        rset.getInt(6)));
    }

    public void addCourseEventDataToCourse(Course course) {
        course.getCourseEvents().clear(); // fixes getting duplicates when clicking on same course again - TODO: better fix?

        List<CourseEvent> courseEvents = runSimpleQuery(ReadQueries.getCourseEventsOfCourse(course.getId()), rset ->
                new CourseEvent(rset.getInt(1), rset.getString(2), rset.getDate(3)));
        for (CourseEvent cev : courseEvents) {
            List<CourseUnit> courseUnits = runSimpleQuery(ReadQueries.getCourseUnitsOfCourseEvent(cev.getId()), rset ->
                    new CourseUnit(rset.getInt(1), rset.getDate(2), rset.getTime(3)));
            List<Person> professors = runSimpleQuery(ReadQueries.getProfsOfCourseEvent(cev.getId()), rset ->
                    new Person(rset.getInt(1), rset.getString(2), rset.getString(3),
                            rset.getString(4), rset.getString(5)));
            List<Person> students = runSimpleQuery(ReadQueries.getStudentsOfCourseEvent(cev.getId()), rset ->
                    new Person(rset.getInt(1), rset.getString(2), rset.getString(3),
                            rset.getString(4), rset.getString(5)));

            cev.getCourseUnits().addAll(courseUnits);
            cev.getProfessors().addAll(professors);
            cev.getStudents().addAll(students);

            course.addCourseEvent(cev);
        }
    }

    public List<Person> getProfessors() {
        return runSimpleQuery(ReadQueries.getAll("professors"), rset ->
                new Person(rset.getInt(1),
                        rset.getString(2),
                        rset.getString(3),
                        rset.getString(4),
                        rset.getString(5)));
    }

    public int updateCourseDescription(Integer courseId, String value) {
        return runParameterizedUpdate(WriteQueries.changeRowFieldValueAtId("courses",
                "course_description", "course_id"),
                    ps -> {
                        ps.setString(1, value);
                        ps.setInt(2, courseId);
                    });
    }

    public void insertProfessorToCourseEvent(Integer professorId, Integer courseEventId) {

    }

    private <T> List<T> runSimpleQuery(String query, FunWithSql<ResultSet, T> resultSetElmtProcessor) {
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

    private int runSimpleUpdate(String sql) {
        try {
            Statement s = connection.createStatement();
            int changeCount = s.executeUpdate(sql);
            s.close();
            return changeCount;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private int runParameterizedUpdate(String paramSql, ProcWithSql<PreparedStatement> prepStmtProcessor) {
        try {
            PreparedStatement ps = connection.prepareStatement(paramSql);
            prepStmtProcessor.apply(ps);

            System.out.print("executing write-query: ");
            System.out.println(ps);
            int changeCount = ps.executeUpdate();

            ps.close();
            return changeCount;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void close() {
        try {
            System.out.print("Closing database connection...");
            connection.close();
            System.out.println("...done.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createConnection(String dbname) {
        try {
            System.out.print("Connecting to database...");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + dbname,
                    "root",
                    "");
            System.out.println("...done.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
