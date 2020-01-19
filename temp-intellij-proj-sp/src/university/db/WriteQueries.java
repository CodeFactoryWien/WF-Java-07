package university.db;

public class WriteQueries {
    /**
     * Returns a query string for PreparedStatement that takes two SQL params, the first being the new value itself
     * and the second being the unique ID field value that identifies the record to be updated.
     */
    static String changeRowFieldValueAtId(String tableName, String targetFieldName, String targetIdName) {
        return String.format("update %s set %s = ? where %s = ?", tableName, targetFieldName, targetIdName);
    }
    private WriteQueries() {};
}
