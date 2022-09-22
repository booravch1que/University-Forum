<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title></title>
    <link rel="stylesheet" href="../css/header.css">
    <link rel="shortcut icon" href="../css/forumIcon.png">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Comfortaa:wght@500&family=Open+Sans:wght@300&display=swap"
          rel="stylesheet">
</head>

<body>
        <c:if test="${sessionScope.isLogged == true}">
            <p id="welcoming"> Welcome,</p>

            <h1 id="welcomeName">${sessionScope.user.getUsername()}
                <c:if test="${sessionScope.user.getRole().toString() == 'ADMIN'}">
                    (ADMIN)
            </c:if></h1>
        </c:if>
        <p id="forumEntrance">
            KPI-Forum
        </p>
<form action="search" method="get" id="themeSearch">
    <label>
        <input type="text" placeholder="search for themes..." name="themeName" size="100">
        <input type="submit" value="search">
    </label>
</form>
        <c:if test="${sessionScope.isLogged == false}">
<form action="login" method="POST" id="login">
    <label>
        <input type="text" placeholder="username" name="username">
        <input type="password" placeholder="password" name="password">
        <input type="submit" value="login" id="loginSubmit">
        <input type="submit" value="register" id="registerSubmit" formaction="register">
    </label>
</form>
        </c:if>
        <c:if test="${sessionScope.isLogged}">
            <a href="logout" id="logoutButton">logout..</a>
        </c:if>
        <hr>
        <c:if test="${sessionScope.isLogged}">
            <ul id="commandBlock">
                <li><a href="createTheme">Create theme..</a></li>
                <li><a href="myThemes">My themes</a></li>
                <li><a href="mainPage">Main Page</a></li>
            </ul>
        </c:if>

</body>
</html>