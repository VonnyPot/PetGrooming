<%--
  Created by IntelliJ IDEA.
  User: vonny
  Date: 11/2/2025
  Time: 5:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.dentistofficev3.PatientBO.Patient" %>
<%@ page import="com.example.dentistofficev3.PatientBO.PatientAppointmentList" %>
<%@ page import="com.example.dentistofficev3.PatientBO.PatientAppointment" %>
<%@ page import="com.example.dentistofficev3.DentistBO.DentistsList" %>
<%@ page import="com.example.dentistofficev3.DentistBO.ProceduresList" %>
<%@ page import="com.example.dentistofficev3.DentistBO.Dentist" %>
<%@ page import="com.example.dentistofficev3.DentistBO.Procedures" %>
<!DOCTYPE html>
<html lang="en">
</html>
<head>
    <title>Patient Info</title>
    <style>
        table { border-collapse: collapse; width: 80%; margin-top: 20px; }
        th, td { border: 1px solid #ccc; padding: 8px; }
        th { background: #eee; }
    </style>
</head>
<body >
<%
    Patient p1;
    p1 = (Patient) session.getAttribute("p1");

    PatientAppointment a1 = (PatientAppointment) session.getAttribute("a1");
    if (a1 != null) {
        a1.display();
    }

    DentistsList dl;
    dl = (DentistsList) session.getAttribute("dentlist");

    ProceduresList pl;
    pl = (ProceduresList) session.getAttribute("proclist");



    //PatientAppointmentList pList;
    //pList = (PatientAppointmentList) session.getAttribute("alist");
    //p1.display();
%>

<%

    //if (pList==null || pList.count == 0){
%>



<%
  //  } else{
%>



<form action="PatientServlet" method="post">
    <table>
        <tr>PatientId:<input type="text" name="patid" value="<%= p1.getPadId()%>"><br></tr>
        <tr>Password: <input type="text" name="password" value="<%= p1.getPassWd()%>"><br></tr>
        <tr>First name: <input type="text" name="firstname" value="<%= p1.getFirstName()%>"><br></tr>
        <tr>Last name: <input type="text"name="lastname"  value="<%= p1.getLastName()%>"><br></tr>
        <tr>Address: <input type="text"name="address" value="<%= p1.getAddress()%>"><br></tr>
        <tr>Ins co: <input type="text" name="insCo"value="<%= p1.getInsCo()%>"><br></tr>
        <tr>Email: <input type="text"name="email" value="<%= p1.getEmail()%>"><br></tr>

    <div style=" ">
        <input type="submit" value=" Submit ">
        <input type="reset" value=" Reset ">
    </div>
    </table>
</form>

<%if(p1 != null){ %>
<form action="PatientAppointmentServlet" method="post">
    <h1> Upcoming Appointments </h1>

    <table border="2">
        <tr>
            <th>Date-Time</th>
            <th>Patient ID</th>
            <th>Dentist Id</th>
            <th>Procedure</th>
        </tr>

    <% System.out.println("appointmentsIf: " + p1.aList.count);
        for (int i=0;i<p1.aList.count;i++){ %>

    <tr>

    <td><input type="text" name="appDate" value="<%= p1.aList.appArr[i].getAppDateTime()%>"></td>>
    <td><input type="text" name="patId" value="<%= p1.aList.appArr[i].getPatId()%>"></td>

    <td>
        <select name="dentId">
            <%
                for (int y = 0; y < dl.count; y++) {
                    Dentist d = dl.dentList[y];
            %>
            <option value="<%= d.getId() %>">
                <%= d.getFirstname() + " " + d.getLastname() + " (Office " + d.getOffice() + ")" %>
            </option>
            <% } %>
        </select>
    </td>
    <td>
        <select name="proCode">
            <%
                for (int x = 0; x < pl.count; x++) {
                    Procedures p = pl.procList[x];
            %>
            <option value="<%= p.getproCode() %>">
                <%= p.getProName() + " - $" + p.getCost() %>
            </option>
            <% } %>
        </select>

    </td>
    <% } %>
    </tr>

    </table>

    <input type="submit" value="Change Appointment">
</form>
</table>
<%} else { %>
</form>
<form action="CreatePatientAppt" method="post">
    <h1> Create A New Appointment </h1>

    <table border="2">
        <tr>
            <th>Date-Time</th>
            <th>Patient ID</th>
            <th>Dentist Id</th>
            <th>Procedure</th>
        </tr>



        <tr>

            <td><input type="text" name="appDate"></td>>
            <td><input name="patId" value="<%= p1.getPadId()%>"></input></td>

            <td>
                <select name="dentId">
                    <%
                        for (int y = 0; y < dl.count; y++) {
                            Dentist d = dl.dentList[y];
                    %>
                    <option value="<%= d.getId() %>">
                        <%= d.getFirstname() + " " + d.getLastname() + " (Office " + d.getOffice() + ")" %>
                    </option>
                    <% } %>
                </select>
            </td>
            <td>
                <select name="proCode">
                    <%
                        for (int x = 0; x < pl.count; x++) {
                            Procedures p = pl.procList[x];
                    %>
                    <option value="<%= p.getproCode() %>">
                        <%= p.getProName() + " - $" + p.getCost() %>
                    </option>
                    <% } %>
                </select>

            </td>

        </tr>

    </table>

    <input type="submit" value="Create Appointment">
</form>
</table>
<% } %>

</body>

</html>
