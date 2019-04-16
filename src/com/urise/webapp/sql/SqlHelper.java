package com.urise.webapp.sql;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private static ConnectionFactory connectionFactory;

    public static void getConnection(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public static <T> T sqlExecute(String sqlString, SqlRequest<T> sqlRequest) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlString)) {
            return sqlRequest.execute(ps);
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                throw new ExistStorageException("Resume already exist");
            }
            throw new StorageException(e);
        }
    }

    @FunctionalInterface
    public interface SqlRequest<T> {
        T execute(PreparedStatement ps) throws SQLException;
    }

}
