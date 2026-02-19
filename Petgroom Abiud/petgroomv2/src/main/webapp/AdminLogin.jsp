<%--
  Created by IntelliJ IDEA.
  User: vonny
  Date: 2/3/2026
  Time: 12:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%-- Change Made By: Abiud Emanuel Ramos Ruiz. 02/18/2026
 Title of the JSP Changed from AdminLogæ
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>

    <title>AdminLogin Here</title>
</head>
<body>
<h2>AdminLogin</h2>

<%--Change Made By: Abiud Emanuel Ramos Ruiz. 02/18/2026
 Updated form action and password field name so the AdminLogin POST matches AdminServlet parameters and login works. --%>
<form action="<%= request.getContextPath() %>/admin" method="post">
    Admin ID: <input type="text" name="id"><br>
    Password: <input type="password" name="password"><br>
    <input type="submit" value="Login">
    <input type="reset" value="clear">
</form>
</body>
</html>
