package com.example.Forum.Web;

import com.example.Forum.DAO.DBWorker;
import com.example.Forum.Model.Theme;
import com.example.Forum.Service.*;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;

@WebListener
public class ApplicationContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        UserService fus = new ForumUserService();
        SearchService<Theme> fss = new ForumSearchService();
        ThemeService fts = new ForumThemeService();
        DBWorker worker = new DBWorker();
        Connection connection = worker.getConnection();
        sce.getServletContext().setAttribute("fts",fts);
        sce.getServletContext().setAttribute("fss",fss);
        sce.getServletContext().setAttribute("fus",fus);
        sce.getServletContext().setAttribute("connection",connection);

    }


}

