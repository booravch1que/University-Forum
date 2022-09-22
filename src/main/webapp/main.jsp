
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<html>
<head>
    <title>KPI-Forum</title>
    <meta charset="UTF-8">
    <link rel="shortcut icon" href="../css/forumIcon.png">
    <link rel="stylesheet" href="../css/main.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Comfortaa:wght@500&family=Open+Sans:wght@300&display=swap"
          rel="stylesheet">
</head>
<body>
<jsp:include page="header.jsp"/>
<%--<c:if test="${requestScope.foundedThemes == null && requestScope.foundedThemes == null &&
requestScope.myThemes == null && !requestScope.createThemeSubmit}">
    <h1>
        Most Popular Themes:
    </h1>
        <c:forEach items="${requestScope.mostPopularThemes}" var="popularTheme">
            <div id = "themeBlock">
                <h1 style = "text-align: center;font-size: 125%">
                    <a href="theme?themeID=${popularTheme.getName()}">${popularTheme.getName()}</a>
                </h1>
            </div>
        </c:forEach>
</c:if>
    --%>
<c:if test="${requestScope.foundedThemes != null}">
    <h1>
        Themes Found:
    </h1>
<c:forEach items="${requestScope.foundedThemes}" var = "foundedTheme">
    <div id = "themeBlock">
        <h1 style = "text-align: center;font-size: 125%">
            <a href="theme?themeID=${foundedTheme.getName()}">${foundedTheme.getName()}</a>
        </h1>
    </div>

</c:forEach>
</c:if>
<c:if test="${requestScope.myThemes != null}">
    <h1>
        ${sessionScope.user.getUsername()} Themes Found:
    </h1>
    <c:forEach items="${requestScope.myThemes}" var = "myTheme">
        <div id = "themeBlock">
            <h1 style = "text-align: center;font-size: 125%">
                <a href="theme?themeID=${myTheme.getName()}">${myTheme.getName()}</a>
            </h1>
        </div>
    </c:forEach>
</c:if>
<c:if test="${sessionScope.isLogged && requestScope.createThemeSubmit}">
    <div id="ThemeCreationBlock">
    <form action="createThemeSubmit" id="ThemeCreation">
        <br>
        <label>
            Theme Name:<input type="text" name="newThemeName" maxlength="32">
        </label>
        <br><br>
        <label>
            First Post or Question(required):<textarea name="newThemePost" rows="15" cols="60"
                                                       placeholder="post.." maxlength="500"></textarea>
        </label>
        <input type="submit" value="submit" style="float: right">
    </form>
    </div>
</c:if>
<p>

</p>
</body>
</html>
