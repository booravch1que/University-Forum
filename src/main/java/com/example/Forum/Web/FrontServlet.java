package com.example.Forum.Web;

import com.example.Forum.Model.Post;
import com.example.Forum.Model.Theme;
import com.example.Forum.Model.User;
import com.example.Forum.Service.ForumSearchService;
import com.example.Forum.Service.ForumThemeService;
import com.example.Forum.Service.ForumUserService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@WebServlet(name = "FrontServlet", urlPatterns = {"/controller/*"})
public class FrontServlet extends HttpServlet {
    String URL;
    PrintWriter out;
    ForumUserService fus;
    ForumSearchService fss;
    ForumThemeService fts;
    @Override
    public void init(ServletConfig config) throws ServletException {
        fus = (ForumUserService) config.getServletContext().getAttribute("fus");
        fss = (ForumSearchService) config.getServletContext().getAttribute("fss");
        fts = (ForumThemeService) config.getServletContext().getAttribute("fts");
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ServletException {
        out = response.getWriter();
        URL = request.getPathInfo();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if(URL == null){
            URL = "/";
        }
        
       switch (URL) {
           case "/login" :
               login(request, response, new User(username, password));
           break;
           case "/register":
               register(request, response, new User(username, password));
           break;
           case "/logout":
               logout(request, response, new User(username, password));
           break;
           case "/search":
                search(request,response,request.getParameter("themeName"));
           break;
           case "/theme":
               theme(request,response,request.getParameter("themeID"));
           break;
           case "/createThemeSubmit":
               createThemeSubmit(request,response,request.getParameter("newThemeName"));
           break;
           case "/newPost":
               newPost(request,response,request.getParameter("newPost"),request.getParameter("themeID"));
           break;
           case "/myThemes":
               myThemes(request,response);
           break;
           case"/deletePost":
               deletePost(request,response,request.getParameter("post"),request.getParameter("themeID"));
           break;
           case"/editPost":
               editPostRequest(request,response,request.getParameter("themeID"));
           break;
           case"/editPostSubmit":
                editPost(request,response,request.getParameter("themeID"),request.getParameter("post")
                        ,request.getParameter("newPost"));
           break;
           case"/deleteTheme":
                deleteTheme(request,response,request.getParameter("themeID"));
           break;
           case "/createTheme":
               createThemeRequest(request,response);
           case"/main.jsp":
           case"/mainPage":
           case"/":
               request.getRequestDispatcher("../main.jsp").forward(request,response);
           break;
           default:
                errors(request,response,"Wrong URL :(");
           break;
           }




    }

    private void editPostRequest(HttpServletRequest request, HttpServletResponse response,String theme) throws ServletException, IOException, SQLException {
        request.setAttribute("editPost",true);
        request.setAttribute("themeID", fts.getTheme(theme));
        request.getRequestDispatcher("../theme.jsp").forward(request,response);
    }
    private void editPost(HttpServletRequest request, HttpServletResponse response,String theme,String post,String newPost) throws SQLException, ServletException, IOException {
        boolean isChanged = fts.editPost(theme,post,newPost);
        request.setAttribute("editPost",false);
        if(isChanged){
            request.setAttribute("themeID", fts.getTheme(theme));
            request.getRequestDispatcher("../theme.jsp").forward(request,response);
        }
        else{
            errors(request,response,"Something went wrong!");
        }
    }

    private void deleteTheme(HttpServletRequest request, HttpServletResponse response,String theme) throws SQLException, ServletException, IOException {
        boolean isDeleted = fts.deleteTheme(theme);
        if(isDeleted){
            request.getRequestDispatcher("../main.jsp").forward(request,response);
        }
        else{
            errors(request,response,"Something went wrong!");
        }
    }
    private void deletePost(HttpServletRequest request, HttpServletResponse response ,String post ,String theme) throws SQLException, ServletException, IOException {
        boolean isDeleted = fts.deletePost(theme,post);
        if(isDeleted){
            request.setAttribute("themeID", fts.getTheme(theme));
            request.getRequestDispatcher("../theme.jsp").forward(request, response);
        }
        else{
            errors(request,response,"Something went wrong!");
        }
    }

    private void errors(HttpServletRequest request, HttpServletResponse response,String message) throws ServletException, IOException {
        request.setAttribute("errorMessage",message);
        request.getRequestDispatcher("../errors.jsp").forward(request,response);
    }

    private void myThemes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        User user = (User)request.getSession().getAttribute("user");
        ArrayList<Theme> myThemes = fts.getThemesByUsername(user.getUsername());
        if(nonNull(myThemes)) {
            request.setAttribute("myThemes",myThemes);

        }
        request.getRequestDispatcher("main.jsp").forward(request,response);
    }


    private void newPost(HttpServletRequest request, HttpServletResponse response, String newPost,String themeName) throws SQLException, ServletException, IOException {
        User user = (User)request.getSession().getAttribute("user");
        if(!newPost.isEmpty()) {
            fts.addPost(new Post(user.getUsername(), newPost), themeName);
            request.setAttribute("themeID", fts.getTheme(themeName));
            request.getRequestDispatcher("../theme.jsp").forward(request, response);
        }
        errors(request,response,"Post message can't be empty!");
    }

    private void createThemeRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("createThemeSubmit",true);
    }

    private void createThemeSubmit(HttpServletRequest request, HttpServletResponse response,String themeName) throws ServletException, IOException, SQLException {
        if (isNull(themeName) || themeName.isEmpty()) {
            request.setAttribute("errorMessage","Enter a theme name!");
            request.getRequestDispatcher("errors.jsp").forward(request,response);

        } else if (!request.getParameter("newThemePost").isEmpty() && nonNull(request.getParameter("newThemePost"))) {
            boolean isCreated = fts.createTheme(themeName);
            User user = (User) request.getSession().getAttribute("user");
            fts.addPost(new Post(user.getUsername(), request.getParameter("newThemePost")), themeName);

            if (isCreated) {
                request.getRequestDispatcher("../main.jsp").forward(request, response);
            } else {
                errors(request,response,"This theme name already exists!");
            }
        }
        else {
            errors(request,response,"Post message can't be empty!");
        }
    }

    private void theme(HttpServletRequest request, HttpServletResponse response, String themeID) throws SQLException, ServletException, IOException {
        boolean isFound = fss.exactSearch(themeID);
        if(isFound){
            request.setAttribute("themeID",fts.getTheme(themeID));
            request.getRequestDispatcher("../theme.jsp").forward(request,response);
        }
        errors(request,response,"No such theme!");

    }

    private void login(HttpServletRequest request, HttpServletResponse response,User user) throws SQLException, ServletException, IOException {
        if(user.getUsername().isEmpty() || user.getPassword().isEmpty()){
            errors(request,response,"Empty username or password");
        }
        else {
            boolean isLogged = fus.login(user);
            request.getSession().setAttribute("isLogged", isLogged);
            user.setRole(fus.getRoleByLogin(user.getUsername()));
            if (isLogged) {
                request.getSession().setAttribute("user", user);
                request.getSession().setAttribute("ROLE", user.getRole().toString());
                request.getRequestDispatcher("main.jsp").forward(request, response);
            } else {
                errors(request, response, "No such user or wrong username/password combination!");
            }
        }
    }
    private void register(HttpServletRequest request, HttpServletResponse response,User user) throws IOException, SQLException, ServletException {
        boolean isLogged = fus.register(user);
        request.getSession().setAttribute("isLogged",isLogged);
        if(isLogged) {
            request.getSession().setAttribute("user", user);
            request.getSession().setAttribute("ROLE",user.getRole().toString());
            request.getRequestDispatcher("main.jsp").forward(request,response);
        }
        errors(request,response,"User with this username already exists!");


    }
    private void logout(HttpServletRequest request, HttpServletResponse response,User user) throws IOException, SQLException, ServletException {
        fus.logout(user);
        request.getSession().setAttribute("isLogged",false);
        request.getSession().setAttribute("user",null);
        request.getRequestDispatcher("main.jsp").forward(request,response);
    }
    private void search(HttpServletRequest request, HttpServletResponse response,String themeName) throws IOException, SQLException, ServletException {
    ArrayList<Theme> foundedThemes = fts.getThemes(themeName);
   if(nonNull(foundedThemes)) {
       request.setAttribute("foundedThemes",foundedThemes);
   }
   request.getRequestDispatcher("main.jsp").forward(request,response);

    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request,response);
        } catch (SQLException e) {
            errors(request,response,"Something went wrong!");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request,response);
        } catch (SQLException e) {
            errors(request,response,"Something went wrong!");
        }
    }
}
