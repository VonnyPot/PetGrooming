package com.example.dentistofficev3;

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
@WebServlet(name = "PatientServlet", urlPatterns = {"/PatientServlet"})

public class PatientServlet extends HttpServlet {
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




         String id = request.getParameter("patid");
         String password = request.getParameter("password");
         String firstname = request.getParameter("firstname");
         String lastname = request.getParameter("lastname");
         String address = request.getParameter("address");
         String email = request.getParameter("email");
         String insCo = request.getParameter("insCo");

         Patient patient = new Patient();
         patient.updateDb(id,password,firstname,lastname,address,email,insCo);
         System.out.println("Patient updated: ");
         patient.selectDB(id);
         patient.display();


         PatientAppointmentList aList = new PatientAppointmentList();
         //aList.selectDB(id);


         //log pulled parameteres
         //System.out.println("Input ID: " + id);
         //System.out.println("Input Password: " + password);

         //calling patient.insertdb(id, password, name)


         //Use the requestDispatcher
         HttpSession ses1 = request.getSession();
         RequestDispatcher rd;

         ses1.setAttribute("p1", patient);
         ses1.setAttribute("alist", aList);

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

