package com.example.Forum.Service;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SearchService<T>{
    public boolean search(String name) throws SQLException;
    public boolean exactSearch(String name) throws SQLException;
}
