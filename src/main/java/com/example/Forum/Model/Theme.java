package com.example.Forum.Model;

import java.util.ArrayList;
import java.util.Objects;

public class Theme {
    String name;
    ArrayList<Post> posts;
    String author;

    public Theme(String name,ArrayList<Post> posts) {
        this.name = name;
        this.posts = posts;
        this.author = posts.get(0).getAuthor();
    }
    public Theme(String name,String author){
        this.name = name;
        this.posts = new ArrayList<Post>();
        this.author = author;
    }

    public Theme(String table_name) {
        this.name = table_name;
        this.posts = new ArrayList<Post>();
        this.author = "admin";
    }

    @Override
    public String toString() {
        return "Theme{" +
                "name='" + name + '\'' +
                ", posts=" + posts +
                ", author='" + author + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Theme theme = (Theme) o;
        return Objects.equals(name, theme.name) && Objects.equals(posts, theme.posts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, posts);
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
