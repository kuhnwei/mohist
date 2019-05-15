<%--
  Created by IntelliJ IDEA.
  User: kuhnwei
  Date: 2018/2/27
  Time: 15:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form action="loginUrl.do" method="post">
    用户名：<input type="text" name="username" id="username"><br>
    密&nbsp;码：<input type="password" name="password" id="password"><br>
    <input type="checkbox" name="rememberMe" value="true">记住密码<br>
    <input type="submit" value="Login">
</form>
</body>
</html>
