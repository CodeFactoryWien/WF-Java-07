package university.db;

import university.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataService {
    Connection connection;

    public Connection getConnection() { return connection; }

    public DataService(String dbname, String username, String password) {
        createConnection(dbname, username, password);
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

    public List<Person> getStudents() {
        return runSimpleQuery(ReadQueries.getAll("students"), rset ->
                new Person(rset.getInt(1),
                        rset.getString(2),
                        rset.getString(3),
                        rset.getString(4),
                        rset.getString(5)));
    }

    public Grade getGrade(Integer studentId, Integer courseId) {
        List<Grade> xs = runSimpleQuery(ReadQueries.getGrade(studentId, courseId),
                rset -> new Grade(rset.getInt(1), rset.getString(2)));
        if (xs.size() == 0) {
            return null;
        } else if (xs.size() == 1) {
            return xs.get(0);
        } else {
            throw new IllegalStateException("data violates compound key in gradings table");
        }
    }

    public void addOrUpdateGrading(Integer studentId, Integer courseId, Integer gradeValue, String gradeComment) {
        if (getGrade(studentId, courseId) != null) {
            runParameterizedUpdate(WriteQueries.updateGrading(), ps -> {
                ps.setInt(1, gradeValue);
                ps.setString(2, gradeComment);
                ps.setInt(3, studentId);
                ps.setInt(4, courseId);
            });
        } else {
            runParameterizedUpdate(WriteQueries.insertGrading(), ps -> {
                ps.setInt(1, studentId);
                ps.setInt(2, courseId);
                ps.setInt(3, gradeValue);
                ps.setString(4, gradeComment);
            });
        }
    }

    public List<Person> getProfsNotDoingEvent(Integer eventId) {
        return runSimpleQuery(ReadQueries.getProfessorsNotTeachingEvent(eventId), rset ->
                new Person(rset.getInt(1), rset.getString(2), rset.getString(3)));
    }

    public List<Person> getStudsNotDoingEvent(Integer eventId) {
        return runSimpleQuery(ReadQueries.getStudentsNotAttendingEvent(eventId), rset ->
                new Person(rset.getInt(1), rset.getString(2), rset.getString(3)));
    }

    public int updateCourseDescription(Integer courseId, String value) {
        return runParameterizedUpdate(WriteQueries.changeRowFieldValueAtId("courses",
                "course_description", "course_id"),
                    ps -> {
                        ps.setString(1, value);
                        ps.setInt(2, courseId);
                    });
    }

    public int addProfToCourseEvent(Integer profId, Integer eventId) {
        return runParameterizedUpdate(WriteQueries.insertProfDoingEvent(), ps -> {
            ps.setInt(1, eventId);
            ps.setInt(2, profId);
        });
    }

    public int deleteProfFromCourseEvent(Integer profId, Integer eventId) {
        return runParameterizedUpdate(WriteQueries.deleteRowByTwoCols(
                "professors_doing_course_events", "professor_id", "course_event_id"),
                ps -> {
                    ps.setInt(1, profId);
                    ps.setInt(2, eventId);
                });
    }

    public int addStudToCourseEvent(Integer studId, Integer eventId) {
        return runParameterizedUpdate(WriteQueries.insertStudDoingEvent(), ps -> {
            ps.setInt(1, studId);
            ps.setInt(2, eventId);
        });
    }

    public int deleteStudFromCourseEvent(Integer studId, Integer eventId) {
        return runParameterizedUpdate(WriteQueries.deleteRowByTwoCols(
                "students_attending_course_events", "student_id", "course_event_id"),
                ps -> {
                    ps.setInt(1, studId);
                    ps.setInt(2, eventId);
                });
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

    private void createConnection(String dbname, String username, String password) {
        try {
            System.out.print("Connecting to database...");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + dbname, username, password);
            System.out.println("...done.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
