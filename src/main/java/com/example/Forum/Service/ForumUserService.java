package com.example.Forum.Service;

import com.example.Forum.DAO.DBWorker;
import com.example.Forum.Model.ROLE;
import com.example.Forum.Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class ForumUserService implements UserService{

    private final DBWorker worker = new DBWorker();
    private final Connection connection = worker.getConnection();
    ResultSet resultSet;
    String allUsersQuery = "select * from \"usersTable\"";
    String addUserQuery = "insert into \"usersTable\"(username, password, role) values (?,?,?)";


    @Override
    public boolean register(User user) throws SQLException {
        if(!isExist(user)){
            PreparedStatement prepStatement = connection.prepareStatement(addUserQuery);
            prepStatement.setString(1,user.getUsername());
            prepStatement.setString(2,user.getPassword());
            prepStatement.setString(3,user.getRole().toString());
            prepStatement.execute();
            return login(user);
        }
        return false;

    }

    @Override
    public boolean login(User user) throws SQLException {
        if(isExist(user)) {
            return checkPassword(user);
        }
        return false;
    }

    @Override
    public User getByLogin(String login) throws SQLException {
        resultSet = connection.createStatement().executeQuery(allUsersQuery);
        while(resultSet.next()){
            if(login.equals(resultSet.getString("username"))){
                return new User(login,resultSet.getString("password"),
                        getRoleByLogin(login));
            }
        }
        return null;
    }
    public ROLE getRoleByLogin(String login) throws SQLException {
        resultSet = connection.createStatement().executeQuery(allUsersQuery);
        while(resultSet.next()){
            if(login.equals(resultSet.getString("username"))){
                if(resultSet.getString("ROLE").equals("USER")) return ROLE.USER;
                if(resultSet.getString("ROLE").equals("ADMIN")) return ROLE.ADMIN;
            }
        }
        return ROLE.GUEST;
    }
    @Override
    public boolean isExist(User user) throws SQLException {
        if(isNull(user)) return false;
        resultSet = connection.createStatement().executeQuery(allUsersQuery);
        while (resultSet.next()){
            if(user.getUsername().equals(resultSet.getString("username"))){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkPassword(User user) throws SQLException {
        resultSet = connection.createStatement().executeQuery(allUsersQuery);
        while(resultSet.next()){
            if(user.getPassword().equals(resultSet.getString("password"))){
                return true;
            }
        }
        return false;

    }

    public void logout(User user) {

    }
}
