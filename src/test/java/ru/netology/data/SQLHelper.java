package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    private static final QueryRunner runner = new QueryRunner();

    private SQLHelper() {
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/app", "app", "pass");
    }

    @SneakyThrows
    public static DataHelper.VerificationCode getVerificationCode() {
        var codeSQL = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1";
        var conn = getConnection();
        var code = runner.query(conn, codeSQL, new ScalarHandler<String>());
        return new DataHelper.VerificationCode(code);
    }

    @SneakyThrows
    public static void cleanDataBase() {
        var conn = getConnection();
        runner.execute(conn, "DELETE FROM auth_codes");
        runner.execute(conn, "DELETE FROM users");
        runner.execute(conn, "DELETE FROM cards");
        runner.execute(conn, "DELETE FROM cards_transactions");
    }

    @SneakyThrows
    public static void cleanAuthCodes() {
        var conn = getConnection();
        runner.execute(conn, "DELETE FROM auth_codes");
    }
}
