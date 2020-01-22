package university.db;

import java.sql.SQLException;

@FunctionalInterface
public interface ProcWithSql<T> {
    void apply(T t) throws SQLException;
}
