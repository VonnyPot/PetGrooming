package com.example.dentistofficev3;

import com.example.dentistofficev3.DentistBO.Dentist;
import com.example.dentistofficev3.DentistBO.DentistsList;
import com.example.dentistofficev3.DentistBO.Procedures;
import com.example.dentistofficev3.DentistBO.ProceduresList;
import com.example.dentistofficev3.PatientBO.Patient;
import com.example.dentistofficev3.PatientBO.PatientAppointment;
import com.example.dentistofficev3.PatientBO.PatientAppointmentList;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.*;

//copy imports from dentist
@WebServlet(name = "CreatePatientAppt", urlPatterns = {"/CreatePatientAppt"})

public class CreatePatientAppt extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        String appDate = request.getParameter("appDate");
        String patId= request.getParameter("patId");
        String dentId = request.getParameter("dentId");
        String proCode = request.getParameter("proCode");


        //Insert Information
        PatientAppointment appt = new PatientAppointment();
        appt.createAppointment(appDate, patId, dentId, proCode);
        System.out.println("Patient appointment created: ");
        appt.selectDB(patId);

        System.out.println("Getting all Dentists");
        Dentist dent = new Dentist();
        dent.selectAllDB();
        DentistsList list = new DentistsList();
        list = dent.getDentists();
        System.out.println(list);


        System.out.println("Procedures list: ");
        Procedures pro = new Procedures();
        pro.selectAllDB();
        ProceduresList proList = new ProceduresList();
        proList = pro.getProcList();
        System.out.println(proList);



        //Use the requestDispatcher
        HttpSession ses1 = request.getSession();

        RequestDispatcher rd;

        ses1.setAttribute("appt", appt);
        ses1.setAttribute("dentlist", list);
        ses1.setAttribute("proclist", proList);


        rd = request.getRequestDispatcher("PatAccinfo.jsp");
        rd.forward(request, response);


    }






}

