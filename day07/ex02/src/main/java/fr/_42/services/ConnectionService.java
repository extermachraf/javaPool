package fr._42.services;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionService {
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5000/db";
    private static final String USERNAME = "achraf";
    private static final String PASSWORD = "123";
    private static final int MAX_POOL_SIZE = 10;


    public static DataSource createConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(JDBC_URL);
        config.setUsername(USERNAME);
        config.setPassword(PASSWORD);
        config.setMaximumPoolSize(MAX_POOL_SIZE);
        return new HikariDataSource(config);
    }
}