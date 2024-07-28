package ru.netology.data;
import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import ru.netology.dto.VerificationCode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlHelper {
    private static final QueryRunner QUERY_RUNNER = new QueryRunner();

    private SqlHelper() {
    }

    private static Connection getConnect() throws SQLException {
        return DriverManager.getConnection(System.getProperty("db.url"), "app", "pass");
    }

    @SneakyThrows
    public static VerificationCode getVerificationCode(String login) {
        var codeSQL = String.format("SELECT code FROM auth_codes WHERE " +
                "user_id = (SELECT id FROM users where login = '%s') ORDER BY created DESC limit 1", login);
        var connect = getConnect();
        return new VerificationCode(QUERY_RUNNER.query(connect, codeSQL, new ScalarHandler<>()));
    }

    @SneakyThrows
    public static void cleanDataBase() {
        var connect = getConnect();
        cleanAuthCode();
        QUERY_RUNNER.execute(connect, "DELETE FROM card_transactions");
        QUERY_RUNNER.execute(connect, "DELETE FROM cards");
        QUERY_RUNNER.execute(connect, "DELETE FROM users");
    }

    @SneakyThrows
    public static void cleanAuthCode() {
        var connect = getConnect();
        QUERY_RUNNER.execute(connect, "DELETE FROM auth_codes");

    }
}
