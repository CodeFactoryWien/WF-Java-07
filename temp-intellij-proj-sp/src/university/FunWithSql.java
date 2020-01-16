package university;

import java.sql.SQLException;

@FunctionalInterface
public interface FunWithSql<T, R> {
    R apply(T t) throws SQLException;
}
