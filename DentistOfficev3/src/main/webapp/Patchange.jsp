<%--
  Created by IntelliJ IDEA.
  User: vonny
  Date: 11/2/2025
  Time: 5:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String patId = request.getParameter("patId");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Add Appointment</title>
</head>
<body>

<h2>Add Appointment for patient: <%= patId %></h2>

<form action="AppointmentServlet" method="post">

    <input type="hidden" name="patId" value="<%= patId %>

    Date/Time= <input type="text" name="appDateTime" placeholder="YYYY-MM-DD HH:MM>
    Dentist ID: <input type="text" name="dentId" placeholder=" ">
    Procedure Code: <input type="text" name="proCode" placeholder=" ">

    <input type="submit" name="action" value="Add">
</form>

<a href="PatAccinfo.jsp">Back to Patient Info</a>
</body>
</html>
