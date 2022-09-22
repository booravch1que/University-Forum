package com.example.Forum.DAO;


import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        DBWorker worker = new DBWorker();
        Connection connection = worker.getConnection();
            try {

                DatabaseMetaData dbmd = connection.getMetaData();
                String[] types = {"TABLE"};
                ResultSet rs = dbmd.getTables(null, null, "%", types);
                while (rs.next()) {
                    System.out.println(rs.getString("TABLE_NAME"));
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        }

