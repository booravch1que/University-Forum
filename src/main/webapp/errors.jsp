<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
    <link rel="shortcut icon" href="../css/forumIcon.png">
</head>
<style>
    <%@include file="css/header.css"%>
</style>
<body>
<%@include file="header.jsp"%>
<h1>
    ERROR: ${requestScope.errorMessage}
</h1>
<h2>
    Return to the main page:
</h2>
<form action="mainPage">
    <input type="submit" value="Main Page">
</form>
</body>
</html>
