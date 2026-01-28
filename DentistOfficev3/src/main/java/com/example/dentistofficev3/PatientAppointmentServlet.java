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
@WebServlet(name = "PatientAppointmentServlet", urlPatterns = {"/PatientAppointmentServlet"})

public class PatientAppointmentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

//         String insertedprocCode = request.getParameter("procCode");
//         String insertedproName = request.getParameter("proName");
//         String insertedproDesc = request.getParameter("proDesc");
//         String Cost = request.getParameter("Cost");
//         String inserteddentistId = request.getParameter("inserteddentistId");
//         String insertedpatientId = request.getParameter("insertedpatientId");
//
//         int insertedCost = Integer.parseInt(Cost);
//
//         PatientAppointment a1 = new PatientAppointment( insertedprocCode, insertedproName, insertedproDesc,  insertedCost,  inserteddentistId, insertedpatientId);
//         a1.selectDB(insertedpatientId);
//         a1.display();

        HttpSession ses1 = request.getSession();
        //Patient p = (Patient)ses1.getAttribute("p");
        String p = request.getParameter("patId");
        String appDate = request.getParameter("appDate");
        String dentId = request.getParameter("dentId");
        String proCode = request.getParameter("proCode");

        //Patient patient= new Patient();
        //Insert Information
        PatientAppointment appt = new PatientAppointment();
        appt.setPatId(p);
        appt.setDentId((dentId));
        appt.setProCode(proCode);
        appt.setAppDateTime(appDate);
        appt.updateAppointment(appDate, p, dentId, proCode);
        System.out.println("Patient appointment updated: ");
        System.out.println("Patient id apservlet: "+ p);
        PatientAppointment newAppt = new PatientAppointment();
        newAppt.selectDB(p);
        appt.selectDB(p);
        appt.display();
        System.out.println("-------------------- ");
        //log pulled parameteres
        //System.out.println("Input ID: " + id);
        //System.out.println("Input Password: " + password);

        //calling patient.insertdb(id, password, name)
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

        //PatientAppointmentList aList = new PatientAppointmentList();


        //Use the requestDispatcher
        ses1 = request.getSession();

        RequestDispatcher rd;
        //ses1.setAttribute("alist", aList);
        //ses1.setAttribute("patId", patId);
        Patient p1 = (Patient) ses1.getAttribute("p1");
        if (p1 != null) {
            p1.aList = new PatientAppointmentList();
            p1.getAppointments();
            ses1.setAttribute("p1", p1);
        }
        ses1.setAttribute("a1", newAppt);
        ses1.setAttribute("dentlist", list);
        ses1.setAttribute("proclist", proList);


        rd = request.getRequestDispatcher("PatAccinfo.jsp");
        rd.forward(request, response);

    }


    //if successful
        /*if () {
            Patient cust = new Patient();
            cust.selectDB(patId);
            ses1.setAttribute("pat", cust);

            rd = request.getRequestDispatcher("PatAccinfo.jsp");
            rd.forward(request, response);
        } else { //if not
            Patient cust = new Patient();
            cust.selectDB(patId);
            ses1.setAttribute("pat", cust);

            rd = request.getRequestDispatcher("error.jsp");
            rd.forward(request, response);
        }*/



}

