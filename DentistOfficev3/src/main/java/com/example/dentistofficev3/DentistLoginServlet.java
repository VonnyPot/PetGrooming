package com.example.dentistofficev3;


import com.example.dentistofficev3.DentistBO.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.RequestDispatcher;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "DentistLoginServlet", urlPatterns = {"/DentistLoginServlet"})
public class DentistLoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");
        String password = request.getParameter("password");
        String dbuid = "";
        String dbpwd = "";

        System.out.println("Input ID: " + id);
        System.out.println("Input Password: " + password);

        String url = "jdbc:ucanaccess://C:/Users/vonny/Downloads/DentistOfficeMDB.mdb";
        String query = "SELECT id, passwd FROM Dentists WHERE id = ?";

        try {
            // Load driver
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

            try (Connection connection = DriverManager.getConnection(url);
                 PreparedStatement stmt = connection.prepareStatement(query)) {

                stmt.setString(1, id);

                System.out.println("Executing query: " + query);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        dbuid = rs.getString("id");
                        dbpwd = rs.getString("passwd");

                        System.out.println("DB Dentist Id: " + dbuid);
                        System.out.println("DB Password: " + dbpwd);
                    }
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        //Use the requestDispatcher
        HttpSession ses1 = request.getSession();
        RequestDispatcher rd;

        //Checking password to Account
        if (id.equals(dbuid) && password.equals(dbpwd)) {
            Dentist dent = new Dentist();
            System.out.println("Getting all Dentists");
            dent.selectAllDB();
            DentistsList list = dent.getDentists();
            ses1.setAttribute("dentlist", list);

            System.out.println("Getting all procedures");
            Procedures pro = new Procedures();
            pro.selectAllDB();
            ProceduresList proList = pro.getProcList();
            ses1.setAttribute("proclist", proList);

            try {
                dent.selectDB(id);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            DentistAppointment da1 = dent.dList.dentList[0];
            ses1.setAttribute("dentist", dent);

            rd = request.getRequestDispatcher("DenAccinfo.jsp");
            rd.forward(request, response);
        } else {
            Dentist dent = new Dentist();
            try {
                dent.selectDB(id);
                System.out.println("Getting all Dentists");
                Dentist allDents = new Dentist();
                allDents.selectAllDB();
                DentistsList list = new DentistsList();
                list = allDents.getDentists();
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
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            ses1.setAttribute("dentist", dent);

            rd = request.getRequestDispatcher("Error.jsp"); //create error page
            rd.forward(request, response);
        }


        //response.setContentType("text/html");
        //PrintWriter out = response.getWriter();


        //Lab 3 part 3
        //if (id.equals("admin") && password.equals("123")) {
        //out.println("<html><body><h1>Valid Login</h1></body></html>");
        // } else {
        //    out.println("<html><body><h1>Invalid Login</h1></body></html>");
        //} out.close();

        //Lab 3 part 2
        //out.println("<html><body><h1>login servlet running</h1></body></html>");
    }
}
