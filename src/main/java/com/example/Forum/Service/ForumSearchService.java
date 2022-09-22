package com.example.Forum.Service;

import com.example.Forum.DAO.DBWorker;
import com.example.Forum.Model.Theme;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
public class ForumSearchService implements SearchService<Theme>{

    private final DBWorker worker = new DBWorker();
   private final Connection connection = worker.getConnection();
    ResultSet resultSet;

    @Override
    public boolean search(String name) throws SQLException {
        resultSet = connection.getMetaData().getTables(null,null,"%",new String[]{"TABLE"});
        while(resultSet.next()) {
            if (resultSet.getString("TABLE_NAME").toLowerCase().contains(name.toLowerCase())){return true;}
        }
        return false;
    }
    public boolean exactSearch(String name) throws SQLException {
        resultSet = connection.getMetaData().getTables(null,null,"%",new String[]{"TABLE"});
        while(resultSet.next()) {
            if (resultSet.getString("TABLE_NAME").trim().equalsIgnoreCase(name)) {return true;}
        }
        return false;
    }
}

