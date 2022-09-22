package com.example.Forum.Model;

import java.util.Objects;

public class User {
    private String password;
    private int id;
    private String username;
    private ROLE role;

    public User(String username, String password,ROLE role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = ROLE.USER;
    }
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", role=" + role +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && username.equals(user.username) &&
                password.equals(user.password) && role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, role);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ROLE getRole() {
        return role;
    }

    public void setRole(ROLE role) {
        this.role = role;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
