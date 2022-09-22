
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="../css/theme.css">
    <link rel="shortcut icon" href="../css/forumIcon.png">
    <title>Theme: ${requestScope.themeID.getName()}</title>
    <style>
    </style>
</head>
<body>
    <%@include file="header.jsp"%>
    <c:if test="${sessionScope.isLogged}">
        <hr>
    </c:if>
    <c:if test="${requestScope.themeID != null && requestScope.themeID.getPosts().size()>0}">
        <ul id="ThemeID">
        <li>Theme: ${requestScope.themeID.getName()}</li>
        <li> Author: ${requestScope.themeID.getPosts().get(0).getAuthor()}</li>
        <c:if test="${sessionScope.user.getRole().toString() == 'ADMIN' ||
        requestScope.themeID.getPosts().get(0).getAuthor() == sessionScope.user.getUsername()}">
            <div id="deleteTheme">
                <p>
                    <a href="deleteTheme?themeID=${requestScope.themeID.getName()}"
                       style="text-decoration: none;
                                        color: black;">X</a>
                </p>
            </div>
        </c:if>
        </ul>
        <br>
        <c:forEach items="${requestScope.themeID.getPosts()}" var="post">
            <div id="ThemePost">
                <div id="ThemeAuthor">
                    <p>${post.getAuthor()}</p>
                </div>
                <c:if test="${sessionScope.user.getRole().toString() == 'ADMIN' ||
                 post.getAuthor() == sessionScope.user.getUsername()}">
                <div id="adminPanel">
                    <div id="deleteButton">
                        <p>
                            <a href="deletePost?themeID=${requestScope.themeID.getName()}&post=${post.getMessage()}"
                               style="text-decoration: none;
                                        color: black;">X</a>
                        </p>
                    </div>
                    <div id="editButton">
                        <p>
                            <a href="editPost?themeID=${requestScope.themeID.getName()}&post=${post.getMessage()}"
                               style="text-decoration: none;
                                color: black;"><span> &#9998; </span></a>
                        </p>
                    </div>
                </div>
                </c:if>
            <p>${post.getMessage()}</p>
                <c:if test="${requestScope.editPost}">
                    <form action="editPostSubmit">
                        <input type="hidden" name="themeID" value="${requestScope.themeID.getName()}">
                        <input type="hidden" name="post" value="${post.getMessage()}">
                    <label>
                        <textarea maxlength="512" name="newPost" rows="10" cols="60" >${post.getMessage()}</textarea>
                    </label>
                        <input type="submit" value="submit">
                    </form>
                </c:if>
            </div>
            <br>
        </c:forEach>
    </c:if>
    <c:if test="${sessionScope.isLogged}">
        <form action="newPost" id="newPostArea">
            <label>
                <input type="hidden" name="themeID" value="${requestScope.themeID.getName()}">
                <textarea name="newPost" rows="15" cols="60" placeholder="post.." maxlength="500"></textarea>
            </label>
            <input type="submit" style="float: right" value="submit">
        </form>
    </c:if>
</body>
</html>
