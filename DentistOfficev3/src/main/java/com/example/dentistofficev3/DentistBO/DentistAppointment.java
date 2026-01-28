package com.example.dentistofficev3.DentistBO;

import java.sql.*;

public class DentistAppointment {
    private String appDateTime;
    private String patId;
    private String dentId;
    private String proCode;

    private static final String URL = "jdbc:ucanaccess://C:/Users/vonny/Downloads/DentistOfficeMDB.mdb";

    //Constructor
    public DentistAppointment() {
        this.appDateTime = " ";
        this.patId = " ";
        this.dentId = " ";
        this.proCode = " ";


    }

    public DentistAppointment(String appDateTime, String patId, String DentId, String proCode) {
        this.appDateTime = appDateTime;
        this.patId = patId;
        this.dentId = dentId;
        this.proCode = proCode;

    }

    //Getter and Setter
    public String getappDateTime() {
        return appDateTime;
    }

    public void setAppDateTime(String appDateTime) {
        this.appDateTime = appDateTime;
    }

    public String getPatId() {
        return patId;
    }

    public void setPatId(String patId) {
        this.patId = patId;
    }

    public String getDentId() {
        return dentId;
    }

    public void setDentId(String dentId) {
        this.dentId = dentId;
    }

    public String getProCode() {
        return proCode;
    }

    public void setProCode(String proCode) {
        this.proCode = proCode;
    }

    // Select
    public void selectDB(String dentId, DentistAppointmentList apptList) {

        String url = "jdbc:ucanaccess://C:/Users/vonny/Downloads/DentistOfficeMDB.mdb";

        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection conn = DriverManager.getConnection(url);

            String sql = "SELECT * FROM Appointments WHERE dentId = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, dentId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                DentistAppointment appt = new DentistAppointment(
                        rs.getString("apptDateTime"),
                        rs.getString("patId"),
                        rs.getString("dentId"),
                        rs.getString("procCode")
                );

                apptList.addAccount(appt);
            }

            rs.close();
            stmt.close();
            conn.close();
        }
        catch (Exception e) {
            System.out.println("Error in selectDB: " + e.getMessage());
        }
    }


    //create(insert)
    public void createAppointment(String appdate, String patId, String dentId, String proCode ){
        String url = "jdbc:ucanaccess://C:/Users/vonny/Downloads/DentistOfficeMDB.mdb";
        try {


            //1. Load driver
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

            //2. Connect
            Connection conn = DriverManager.getConnection(url);

            //3. SQL
            String sql = "INSERT INTO Appointments (apptDateTime, patId, dentId, procCode) VALUES (?,?,?,?)";
            System.out.println(sql);
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, appdate);
            stmt.setString(2, patId);
            stmt.setString(3, dentId);
            stmt.setString(4, proCode);

            stmt.executeUpdate();

            System.out.println("Executing Query: " + sql);

            //4. Execute
            //ResultSet rs = stmt.executeQuery();

            //if

            //while (rs.next()) {
            stmt.setString(1, appdate);
            stmt.setString(2, patId);
            stmt.setString(3, dentId);
            stmt.setString(4, proCode);


            System.out.println("Appointment filled: " + proCode + " " + appdate  + " for patient: " + patId + " for this dentist: " + dentId);
            // }

            //5. Close
            //rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Error in creating appointment: " + e.getMessage());
        }

    }

    // update
    public void updateAppointment(String appdate, String patId, String dentId, String proCode){
        String url = "jdbc:ucanaccess://C:/Users/vonny/Downloads/DentistOfficeMDB.mdb";

        System.out.println("Appdate:"+appdate +" PatId:"+patId);
        try {
            //1. Load driver
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

            //2. Connect
            Connection conn = DriverManager.getConnection(url);

            //3. SQL

            //String sql = "UPDATE Appointments SET apptDateTime=? WHERE patId=?";
            String sql = "UPDATE Appointments SET apptDateTime=? WHERE patId=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, appdate);
            stmt.setString(2, patId);
            stmt.executeUpdate();

            //PreparedStatement  stmt = conn.prepareStatement(sql);
            //stmt.setString(2, patId);


            //System.out.println("Executing Query: " + sql);

            //4. Execute
            //ResultSet rs = stmt.executeQuery();

            //if

            /*while (rs.next()) {
                stmt.setString(1, appdate);
                stmt.setString(2, patId);
                stmt.setString(3, dentId);
                stmt.setString(4, proCode);


                System.out.println("Appointment filled: " + proCode + " " + appdate  + " for patient: " + patId + " for this dentist: " + dentId);
            }

            //5. Close
            rs.close();*/
            //stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Error in creating appointment: " + e.getMessage());
        }

    }



    //Delete
    public void deleteAppointment(String appdate, String patId, String dentId, String proCode){
        String url = "jdbc:ucanaccess://C:/Users/vonny/Downloads/DentistOfficeMDB.mdb";
        try {
            //1. Load driver
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

            //2. Connect
            Connection conn = DriverManager.getConnection(url);

            //3. SQL
            String sql = "DELETE FROM Appointments WHERE appdatetime = ?";
            System.out.println(sql);
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, appDateTime);


            System.out.println("Executing Query: " + sql);

            //4. Execute
            ResultSet rs = stmt.executeQuery();

            //if

            while (rs.next()) {
                stmt.setString(1, appdate);
                stmt.setString(2, patId);
                stmt.setString(3, dentId);
                stmt.setString(4, proCode);


                System.out.println("Appointment filled: " + proCode + " " + appdate  + " for patient: " + patId + " for this dentist: " + dentId);
            }

            //5. Close
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Error in creating appointment: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "DentistAppointment{" +
                "appDateTime='" + getappDateTime() + '\'' +
                ",patId='" + patId + '\'' +
                ", dentId='" + dentId + '\'' +
                ", proCode='" + proCode + '\'' +
                '}';

    }

    }




