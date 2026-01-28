package com.example.dentistofficev3;

import com.example.dentistofficev3.DentistBO.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.*;

@WebServlet(name = "DentistAppointmentServlet", urlPatterns = {"/DentistAppointmentServlet"})
public class DentistAppointmentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String appDate = request.getParameter("appDate");
        String patId = request.getParameter("patId");
        String dentId = request.getParameter("dentId");
        String proCode = request.getParameter("proCode");

        //Insert Information
        DentistAppointment appt = new DentistAppointment();
        appt.createAppointment(appDate, patId, dentId, proCode);
        System.out.println("Dentist appointment created: ");


        //Use the requestDispatcher
        HttpSession ses1 = request.getSession();
        RequestDispatcher rd;

        // Reload the dentist with updated appointments
        Dentist d1 = (Dentist) ses1.getAttribute("dentist");
        if (d1 != null) {

            // Reset the appointment list before reloading to avoid duplicates
            d1.dList = new DentistAppointmentList();
            try {
                d1.loadAppointments();
                try {
                    System.out.println("Getting all Dentists");
                    Dentist dent = new Dentist();
                    dent.selectAllDB();
                    DentistsList list = new DentistsList();
                    list = dent.getDentists();
                    System.out.println(list);
                    System.out.println("-------------------- ");


                    System.out.println("Procedures list: ");
                    Procedures pro = new Procedures();
                    pro.selectAllDB();
                    ProceduresList proList = new ProceduresList();
                    proList = pro.getProcList();
                    System.out.println(proList);
                    System.out.println("-------------------- ");
                    ses1.setAttribute("dentlist", list);
                    ses1.setAttribute("proclist", proList);
                } catch (Exception e) {
                    System.out.println("Error loading lists: " + e.getMessage());
                }
            } catch (ClassNotFoundException | SQLException e) {
                throw new RuntimeException(e);
            }
            ses1.setAttribute("dentist", d1);
        }

        ses1.setAttribute("appt", appt);

        rd = request.getRequestDispatcher("DenAccinfo.jsp");
        rd.forward(request, response);
    }
}
