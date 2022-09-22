package com.example.Forum.Model;

import java.util.Objects;

public class Post {
    String author;
    String message;

    public Post(String author, String message) {
        this.author = author;
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(author, post.author) && Objects.equals(message, post.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, message);
    }

    @Override
    public String toString() {
        return "Post{" +
                "author='" + author + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

