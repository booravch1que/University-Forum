package com.example.Forum.Service;

import com.example.Forum.DAO.DBWorker;
import com.example.Forum.Model.ROLE;
import com.example.Forum.Model.User;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class ForumUserServiceTest {
DBWorker dbWorker = new DBWorker();
Connection connection = dbWorker.getConnection();
    @Test
    void isExist() {
        User user = new User("name","name");
    }

    @Test
    void checkPassword() {
    }
}