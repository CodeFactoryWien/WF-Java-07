package university.db;

public class WriteQueries {
    /**
     * Returns a query string for PreparedStatement taking two SQL params: course event id, professor id
     */
    static String insertProfDoingEvent() { return insertMtoM("professors_doing_course_events"); }

    /**
     * Returns a query string for PreparedStatement taking two SQL params: student id, course event id
     */
    static String insertStudDoingEvent() { return insertMtoM("students_attending_course_events"); }

    static String insertMtoM(String tableName) {
        return String.format("insert into %s values (?, ?)", tableName);
    }

    /**
     * Returns a query string for PreparedStatement that takes two SQL params, the first being the new value itself
     * and the second being the unique ID field value that identifies the record to be updated.
     */
    static String changeRowFieldValueAtId(String tableName, String targetFieldName, String idColname) {
        return String.format("update %s set %s = ? where %s = ?", tableName, targetFieldName, idColname);
    }

    /**
     * Returns a query string for PreparedStatement that takes one SQL param, the ID field value that identifies
     * the record to be deleted.
     */
    static String deleteRowAtId(String tableName, String idColname) {
        return String.format("delete from %s where %s = ?", tableName, idColname);
    }

    /**
     * Returns a query string for PreparedStatement that takes two SQL params, the two columns names that together
     * make up the compound key that identifies the record to be deleted.
     */
    static String deleteRowByTwoCols(String tableName, String idColname1, String idColname2) {
        return String.format("delete from %s where %s = ? and %s = ?", tableName, idColname1, idColname2);
    }

    /**
     * Returns query string for PreparedStatement that takes four SQL params: student id, course id, grade value,
     * grading comment
     */
    static String insertGrading() {
        return "insert into gradings values (?, ?, ?, ?)";
    }

    /**
     * Returns query string for PreparedStatement that takes four SQL params: grade value, grade comment,
     * student id, course id
     */
    static String updateGrading() {
        return "update gradings set grade = ?, grading_comment = ? where student_id = ? and course_id = ?";
    }

    private WriteQueries() {};
}
