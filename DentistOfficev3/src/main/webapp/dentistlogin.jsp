<%--
  Created by IntelliJ IDEA.
  User: Vonny Pototsky
  Date: 10/29/2025
  CIST 2372

--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Dentist Login Here</title>
</head>

<body>

<h2> Dentist Login</h2>
<form action="DentistLoginServlet" method="post">
    Dentist ID: <input type="text" name="id"><br><br>
    Password: <input type="password" name="password"><br><br>
    <input type="submit" value="Login">
    <input type="reset" value="Clear">
</form>


</body>



</html>






