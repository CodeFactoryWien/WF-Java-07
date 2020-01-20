package university.db;

public class WriteQueries {
    /**
     * Returns a query string for PreparedStatement taking two SQL params: course event id, professor id
     */
    static String insertProfDoingEvent() {
        return "insert into professors_doing_course_events values (?, ?)";
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

    private WriteQueries() {};
}
