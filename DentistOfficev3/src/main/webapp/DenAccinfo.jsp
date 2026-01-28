<%--
  Created by IntelliJ IDEA.
  User: vonny
  Date: 11/2/2025
  Time: 5:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.dentistofficev3.DentistBO.*" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Dentist Info</title>
    <style>
        table { border-collapse: collapse; width: 80%; margin-top: 20px; }
        th, td { border: 1px solid #ccc; padding: 8px; }
        th { background: #eee; }
    </style>
</head>
<body>
<%
    Dentist d1 = (Dentist) session.getAttribute("dentist");

    // Get other session attributes
    DentistsList dl = (DentistsList) session.getAttribute("dentlist");
    ProceduresList pl = (ProceduresList) session.getAttribute("proclist");

    // Add null check for d1
    if (d1 == null) {
        System.out.println("<h2>Error: No dentist session found. Please log in again.</h2>");
        return;
    }
%>

<h1>Dentist Information</h1>

<form action="DentistServlet" method="post">
    <table>
        <tr><td>Id:</td><td><input type="text" name="Id" value="<%= d1.getId()%>"></td></tr>
        <tr><td>Password:</td><td><input type="text" name="password" value="<%= d1.getPassword()%>"></td></tr>
        <tr><td>First name:</td><td><input type="text" name="firstname" value="<%= d1.getFirstname()%>"></td></tr>
        <tr><td>Last name:</td><td><input type="text" name="lastname" value="<%= d1.getLastname()%>"></td></tr>
        <tr><td>Email:</td><td><input type="text" name="email" value="<%= d1.getEmail()%>"></td></tr>
        <tr><td>Office:</td><td><input type="text" name="office" value="<%= d1.getOffice()%>"></td></tr>
    </table>
    <div style="margin-top: 10px;">
        <input type="submit" value="Submit">
        <input type="reset" value="Reset">
    </div>
</form>

<h2>Upcoming Appointments</h2>

<%
    if (d1.dList != null && d1.dList.count > 0) {
%>
<form action="DentistAppointmentServlet" method="post">
    <table border="2">
        <tr>
            <th>Date-Time</th>
            <th>Patient ID</th>
            <th>Dentist Id</th>
            <th>Procedure</th>
        </tr>

        <%
            System.out.println("Number of appointments: " + d1.dList.count);
            for (int i = 0; i < d1.dList.count; i++) {
                DentistAppointment appt = d1.dList.dentList[i];
        %>
        <tr>
            <td><input type="text" name="appDate" value="<%= appt.getappDateTime()%>"></td>
            <td><input type="text" name="patId" value="<%= appt.getPatId()%>"></td>
            <td>
                <% if (dl != null && dl.count > 0) { %>
                <select name="dentId">
                    <% for (int y = 0; y < dl.count; y++) {
                        Dentist dentist = dl.dentList[y];
                    %>
                    <option value="<%= dentist.getId() %>" <%= dentist.getId().equals(appt.getDentId()) ? "selected" : "" %>>
                        <%= dentist.getFirstname() + " " + dentist.getLastname() + " (Office " + dentist.getOffice() + ")" %>
                    </option>
                    <% } %>
                </select>
                <% } else { %>
                <input type="text" name="dentId" value="<%= appt.getDentId()%>">
                <% } %>
            </td>
            <td>
                <% if (pl != null && pl.count > 0) { %>
                <select name="proCode">
                    <% for (int x = 0; x < pl.count; x++) {
                        Procedures proc = pl.procList[x];
                    %>
                    <option value="<%= proc.getproCode() %>" <%= proc.getproCode().equals(appt.getProCode()) ? "selected" : "" %>>
                        <%= proc.getProName() + " - $" + proc.getCost() %>
                    </option>
                    <% } %>
                </select>
                <% } else { %>
                <input type="text" name="proCode" value="<%= appt.getProCode()%>">
                <% } %>
            </td>
        </tr>
        <% } %>
    </table>
    <input type="submit" value="Update Appointment">
</form>

<% } else { %>
<p>No appointments found.</p>
<% } %>

<h2>Create New Appointment</h2>
<form action="DentistAppointmentServlet" method="post">
    <table border="2">
        <tr>
            <th>Date-Time</th>
            <th>Patient ID</th>
            <th>Dentist Id</th>
            <th>Procedure</th>
        </tr>
        <tr>
            <td><input type="text" name="appDate" placeholder="YYYY-MM-DD HH:MM"></td>
            <td><input type="text" name="patId" placeholder="Patient ID"></td>
            <td>
                <% if (dl != null && dl.count > 0) { %>
                <select name="dentId">
                    <% for (int y = 0; y < dl.count; y++) {
                        Dentist dentist = dl.dentList[y];
                    %>
                    <option value="<%= dentist.getId() %>">
                        <%= dentist.getFirstname() + " " + dentist.getLastname() + " (Office " + dentist.getOffice() + ")" %>
                    </option>
                    <% } %>
                </select>
                <% } else { %>
                <input type="text" name="dentId" value="<%= d1.getId()%>">
                <% } %>
            </td>
            <td>
                <% if (pl != null && pl.count > 0) { %>
                <select name="proCode">
                    <% for (int x = 0; x < pl.count; x++) {
                        Procedures proc = pl.procList[x];
                    %>
                    <option value="<%= proc.getproCode() %>">
                        <%= proc.getProName() + " - $" + proc.getCost() %>
                    </option>
                    <% } %>
                </select>
                <% } else { %>
                <input type="text" name="proCode" placeholder="Procedure Code">
                <% } %>
            </td>
        </tr>
    </table>
    <input type="submit" value="Create Appointment">
</form>

</body>
</html>