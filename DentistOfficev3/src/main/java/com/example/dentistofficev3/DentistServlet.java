package com.example.dentistofficev3;


import com.example.dentistofficev3.DentistBO.Dentist;
import com.example.dentistofficev3.DentistBO.DentistAppointmentList;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "DentistServlet", urlPatterns = {"/DentistServlet"})
public class DentistServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("Id");
        String password = request.getParameter("password");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String email = request.getParameter("email");
        String office = request.getParameter("office");

        Dentist dentist = new Dentist();
        dentist.updateDb(id, password, firstname, lastname, office, email);
        System.out.println("Dentist updated.");

        try {
            dentist.selectDB(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }



        HttpSession ses1 = request.getSession();
        RequestDispatcher rd;

        ses1.setAttribute("dentist", dentist);


        rd = request.getRequestDispatcher("DenAccinfo.jsp");
        rd.forward(request, response);


    }
}
