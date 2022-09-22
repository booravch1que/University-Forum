package com.example.Forum.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.postgresql.Driver;

public class DBWorker {

final private static String URL = "jdbc:postgresql://localhost:5432/forum_db";
final private static String USER = "postgres";
final private static String PASSWORD = "3714";
private Connection connection;

    public DBWorker() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL,USER,PASSWORD);
            org.postgresql.Driver driver = new Driver();
            DriverManager.registerDriver(driver);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection() {
        try {
            this.connection = DriverManager.getConnection(URL,USER,PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
