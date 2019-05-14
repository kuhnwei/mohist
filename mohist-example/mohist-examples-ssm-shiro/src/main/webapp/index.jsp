<%--
  Created by IntelliJ IDEA.
  User: kuhnwei
  Date: 2018/2/27
  Time: 15:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>SSM-SHIRO</title>
</head>
<body>
<h1>Welcome to ssm-shiro.</h1>
<shiro:hasPermission name="admin:add">1. admin:add</shiro:hasPermission>
<br>
<shiro:hasPermission name="admin:edit">2. admin:edit</shiro:hasPermission>
<br>
<shiro:hasPermission name="admin:remove">3. admin:remove</shiro:hasPermission>
<br>
<shiro:hasPermission name="dept:add">4. dept:add</shiro:hasPermission>
<br>
<shiro:hasPermission name="dept:edit">5. dept:edit</shiro:hasPermission>
<br>
<shiro:hasPermission name="dept:list">6. dept:list</shiro:hasPermission>
<br>
<shiro:hasPermission name="user:list">7. user:list</shiro:hasPermission>
<br>
<a href="logout.do">退出</a>
</body>
</html>
