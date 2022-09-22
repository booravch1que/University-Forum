package com.example.Forum.Service;

import com.example.Forum.DAO.DBWorker;
import com.example.Forum.Model.Post;
import com.example.Forum.Model.Theme;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ForumThemeService implements ThemeService {

    private final Connection connection = new DBWorker().getConnection();
    ResultSet resultSet;


    public ArrayList<Theme> mostPopularThemes() throws SQLException {
        ArrayList<Theme> result = new ArrayList<>();
        resultSet = connection.getMetaData().getTables(null, null, "%", new String[]{"TABLE"});
        HashMap<String, Integer> table = new HashMap<>();
        while (resultSet.next()) {
            ResultSet resultSet1 = connection.createStatement().executeQuery(
                    "SELECT count(*) from \"" + resultSet.getString("TABLE_NAME") + "\"");
            while (resultSet1.next()){
                table.put(resultSet.getString("TABLE_NAME"), resultSet1.getInt(1));
            }
        }
        table.remove("usersTable");
        int max = 0;
        HashMap<String,Integer> toRemove = new HashMap<>();
        for (Map.Entry<String,Integer> entry :
                table.entrySet()) {
            for(int i = 0;i < 5;i++){
                if(entry.getValue()>max){
                    max = entry.getValue();
                    toRemove.put(entry.getKey(),entry.getValue());
                    result.add(new Theme(entry.getKey()));
                }
                if(toRemove.size() >= table.size()){
                    break;
                }
            }
        }
        return result;
    }
    @Override
    public boolean createTheme(String ThemeName) throws SQLException {
        resultSet = connection.getMetaData().getTables(null, null, "%", new String[]{"TABLE"});
        while (resultSet.next()) {
            if (resultSet.getString("TABLE_NAME").equalsIgnoreCase(ThemeName)){
                return false;
            }
        }
        PreparedStatement preparedStatement = connection.prepareStatement(
                String.format("CREATE TABLE \"%s\" (author varchar(32),posts varchar(1024));",ThemeName));
        preparedStatement.execute();
        return true;
    }

    public boolean createTheme(Theme theme) throws SQLException {
        resultSet = connection.getMetaData().getTables(null, null, "%", new String[]{"TABLE"});
        while (resultSet.next()) {
            if (resultSet.getString("TABLE_NAME").equalsIgnoreCase(theme.getName().replaceAll(" ",""))) {
                return false;
            }
        }
        PreparedStatement preparedStatement = connection.prepareStatement(
                String.format("CREATE TABLE \"%s\" (author varchar(32),posts varchar(1024));",theme.getName()));
        preparedStatement.execute();
        return true;
    }

    public List<String> getThemePosts(String themeName) throws SQLException {
        List<String> result = new ArrayList<String>();
        resultSet = connection.getMetaData().getTables(null, null, "%", new String[]{"TABLE"});
        while (resultSet.next()) {
            if (resultSet.getString("TABLE_NAME").contains(themeName)) {
                ResultSet resultSet1 = connection.createStatement().executeQuery("select posts from " + themeName);
                while (resultSet1.next()) {
                    result.add(resultSet1.getString("posts"));
                }
                return result;
            }
        }
        return new ArrayList<>();
    }

    public ArrayList<Post> getThemePosts(Theme theme) throws SQLException {
        resultSet = connection.getMetaData().getTables(null, null, "%", new String[]{"TABLE"});
        while (resultSet.next()) {
            if (resultSet.getString("TABLE_NAME").contains(theme.getName())) {
                ResultSet resultSet1 = connection.createStatement().executeQuery("select posts from " + theme.getName());
                while (resultSet1.next()) {
                    theme.getPosts().add(new Post(resultSet1.getString("author"),
                            resultSet1.getString("posts")));
                }
                return theme.getPosts();
            }
        }
        return new ArrayList<Post>();
    }

    public ArrayList<Theme> getThemes(String name) throws SQLException {
        ArrayList<Theme> result = new ArrayList<Theme>();
        resultSet = connection.getMetaData().getTables(null, null, "%", new String[]{"TABLE"});
        while (resultSet.next()) {
            if (resultSet.getString("TABLE_NAME").toLowerCase().contains(name.toLowerCase())) {
                result.add(new Theme(resultSet.getString("TABLE_NAME")));
            }
        }
        result.removeIf(t -> t.getName().equals("usersTable"));
        return result;
    }

    @Override
    public boolean deleteTheme(Theme theme) throws SQLException {
        resultSet = connection.getMetaData().getTables(null, null, "%", new String[]{"TABLE"});
        while (resultSet.next()) {
            if (resultSet.getString("TABLE_NAME").equals(theme.getName())) {
                connection.createStatement().executeQuery("DROP TABLE " + theme.getName());
                return true;
            }
        }
        return false;
    }
    public boolean deleteTheme(String theme) throws SQLException {
        resultSet = connection.getMetaData().getTables(null, null, "%", new String[]{"TABLE"});
        while (resultSet.next()) {
            if (resultSet.getString("TABLE_NAME").equals(theme)) {
                PreparedStatement preparedStatement = connection.prepareStatement("DROP TABLE \"" + theme+ "\"");
                preparedStatement.execute();
                return true;
            }
        }
        return false;
    }
    public ArrayList<Theme> getThemesByUsername(String username) throws SQLException {
        ArrayList<Theme> result = new ArrayList<>();
        resultSet = connection.getMetaData().getTables(null, null, "%", new String[]{"TABLE"});
        while (resultSet.next()) {
            if(resultSet.getString("TABLE_NAME").equals("usersTable")){
                continue;
            }
            ResultSet resultSet1 = connection.createStatement().executeQuery("SELECT * from \"" +
                    resultSet.getString("TABLE_NAME") + "\" LIMIT 1");
            while (resultSet1.next()) {
                if (resultSet1.getString("author").equals(username)) {
                    result.add(new Theme(resultSet.getString("TABLE_NAME"), username));
                }
            }
        }
        return result;
    }
    public Theme getTheme(String themeID) throws SQLException {
        resultSet = connection.getMetaData().getTables(null, null, "%", new String[]{"TABLE"});
        while (resultSet.next()) {
            if (resultSet.getString("TABLE_NAME").equalsIgnoreCase(themeID)) {
                ArrayList<Post> posts = new ArrayList<>();
                ResultSet resultSet1 = connection.createStatement().executeQuery("select * from \""+themeID+"\"");
                while(resultSet1.next()){
                    posts.add(new Post(resultSet1.getString("author"),resultSet1.getString("posts")));
                }
                if(posts.size() == 0){
                    return new Theme(themeID);
                }
                return new Theme(themeID,posts);
            }
        }
        return null;
    }
    public void addPost(String post,String author,String themeName) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement("insert into "+ themeName +"(author, posts) VALUES " +
                "('"+author+"','"+post+"')");
        preparedStatement.execute();
    }
    public void addPost(Post post,String themeName) throws SQLException{
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO \"" + themeName + "\"(author, posts) VALUES " +
                    "('" + post.getAuthor().replaceAll("'","''") + "','" + post.getMessage().replaceAll("'","''") + "')");
            preparedStatement.execute();
    }

    public boolean deletePost(String theme, String post) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("delete from \""+theme+"\" " +
                "where posts="+"'"+post+"'");
        preparedStatement.execute();
        return true;
    }
    public boolean editPost(String theme,String post,String newPost) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("update \""+theme+"\" set " +
                "posts = '"+newPost+"' where posts="+"'"+post+"'");
        preparedStatement.execute();
        return true;
    }
}
