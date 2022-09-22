package com.example.Forum.Service;
import com.example.Forum.Model.Theme;

import java.sql.SQLException;

public interface ThemeService {
    public boolean createTheme(String ThemeName) throws SQLException;

    public boolean deleteTheme(Theme theme) throws SQLException;

}
