package org.example.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static final String JDBC_URL = "jdbc:sqlite:goods.db";

    static {
        try (Connection connection = getConnection()) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS goods ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "name VARCHAR(255) NOT NULL, "
                    + "description VARCHAR(255), "
                    + "price DOUBLE NOT NULL)";
            connection.createStatement().execute(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize database", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL);
    }


}
