package com.example.Forum.Service;
import com.example.Forum.Model.User;

import java.sql.Connection;
import java.sql.SQLException;

public interface UserService {
    boolean register(User user) throws SQLException;
     boolean login (User user) throws SQLException;
    User getByLogin(String login) throws SQLException;

    boolean isExist(User user) throws SQLException;
    boolean checkPassword(User user) throws SQLException;
}
