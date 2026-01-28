package com.example.dentistofficev3;

import com.example.dentistofficev3.DentistBO.Dentist;
import com.example.dentistofficev3.DentistBO.DentistsList;
import com.example.dentistofficev3.DentistBO.Procedures;
import com.example.dentistofficev3.DentistBO.ProceduresList;
import com.example.dentistofficev3.PatientBO.*;
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
@WebServlet(name = "PatientLoginServlet", urlPatterns = {"/PatientLoginServlet"})

public class PatientLoginServlet extends HttpServlet {
     @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");
        String password = request.getParameter("password");
        String dbuid = "";
        String dbpwd = "";

        System.out.println("Input ID: " + id);
        System.out.println("Input Password: " + password);

        //String url = "jdbc:ucanaccess://C:/Users/vonny/Downloads/DentistOfficeMDB.mdb";
        //String query = "SELECT patId, passwd FROM Patients WHERE patId = ?";

        Patient p1 = new Patient(); //Patient instead of customer
         p1.selectDB(id);
         p1.display();

         dbuid = p1.getPadId();
         dbpwd = p1.getPassWd();

        /*try {
            // Load driver
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

            try (Connection connection = DriverManager.getConnection(url);
                 PreparedStatement stmt = connection.prepareStatement(query)) {

                stmt.setString(1, id);

                System.out.println("Executing query: " + query);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {


                        System.out.println("DB ID: " + dbuid);
                        System.out.println("DB Password: " + dbpwd);
                    }
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }*/





         //Use the requestDispatcher
        HttpSession ses1 = request.getSession();
        RequestDispatcher rd;

         System.out.println("Getting all Dentists");
         Dentist dent = new Dentist();
         dent.selectAllDB();
         DentistsList list = dent.getDentists();
         ses1.setAttribute("dentlist", list);

         System.out.println("Getting all procedures");
         Procedures pro = new Procedures();
         pro.selectAllDB();
         ProceduresList proList = pro.getProcList();
         ses1.setAttribute("proclist", proList);

        //Checking password to Account
        if (id.equals(dbuid) && password.equals(dbpwd)) {
            p1.getAppointments();
            //System.out.println("appointmentsPLserv: " + p1.aList.appArr.length);
            ses1.setAttribute("p1", p1);



//            PatientAppointment pa1= p1.aList.appArr[0];
//            System.out.println("appointments: " + p1.aList.appArr.length);
//            ses1.setAttribute("pa1", pa1);

            rd = request.getRequestDispatcher("PatAccinfo.jsp"); //Where sending the data too
            rd.forward(request, response);
        }
         else {

            rd = request.getRequestDispatcher("error.jsp");
            rd.forward(request, response);
       }
    }
}
