package com.example.dentistofficev3.PatientBO;

import java.sql.*;


public class PatientAppointment {
    private String proCode;
    //private String proName;
    //private String proDesc;
    //private int Cost;
    private String dentId;
    private String patId;
    private String appDateTime;

    public PatientAppointment() {
        this.proCode = "";
        //this.proName = "";
        //this.proDesc = "";
        //this.Cost = 0;
        this.dentId = "";
        this.patId = "";
        this.appDateTime = "";
    }

    public PatientAppointment(String procCode, String dentistId, String patientId, String appDateTime) {
        this.proCode = procCode;
        this.dentId = dentistId;
        this.patId = patientId;
        this.appDateTime = appDateTime;
    }

    //Getter and setter
    public String getProCode() {
        return proCode;
    }
    public void setProCode(String proCode) {
        this.proCode = proCode;
    }

    public String getDentId() {
        return dentId;
    }
    public void setDentId(String dentId) {
        this.dentId = dentId;
    }
    public String getPatId() {
        return patId;
    }
    public void setPatId(String patId) {
        this.patId = patId;
    }
    public String getAppDateTime() {
        return appDateTime;
    }
    public void setAppDateTime(String appDateTime) {
        this.appDateTime = appDateTime;
    }

    // Select
    public void selectDB (String patId){
        this.patId= patId ;
        String url = "jdbc:ucanaccess://C:/Users/vonny/Downloads/DentistOfficeMDB.mdb";
        System.out.println("Patid (Sdb start): "+getPatId());
        try {
            //1. Load driver
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

            //2. Connect
            Connection conn = DriverManager.getConnection(url);

            //3. SQL
            String sql = "SELECT * FROM Appointments WHERE patId = ?";
            System.out.println(sql);
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, patId);


            System.out.println("Executing Query PAppt: " + sql);
            System.out.println("Patid (Appt): "+patId);
            //4. Execute
            ResultSet rs = stmt.executeQuery();

            //if

            while (rs.next()) {
                System.out.println(rs.getString(1) + rs.getString(2) + rs.getString(3) + rs.getString(4));
                setProCode(rs.getString(2));
                setDentId(rs.getString(4));
                setPatId(rs.getString(3));
                setAppDateTime(rs.getString(1));

                System.out.println("Appointment filled: ProcCode:" + this.proCode + " DentID: " + this.dentId + " PatID: " + this.patId+ " AppDate: " + this.appDateTime);

            }

            //5. Close
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
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
            Statement stmt = conn.createStatement();
            //String sql = "UPDATE Appointments SET apptDateTime=? WHERE patId=?";
            String sqlstate = "UPDATE Appointments Set apptDateTime= '"+appdate+"' where patId='"+patId+"'";
            //System.out.println(sql);
            stmt.executeUpdate(sqlstate);
            System.out.println("Executing Query: " + sqlstate);


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




public void display(){
        System.out.println("Date: " + appDateTime);
        System.out.println("Patient Id: " + patId);
        System.out.println("Dentist Name: " + dentId);
        System.out.println("ProcCode: " + proCode);


}
public static void main(String[] args) {
        PatientAppointment ap = new PatientAppointment();
        ap.selectDB("A900");
        ap.display();
}
    // automatically create appointments (pull from db)

    // manually make appointments (get/set) (for both patient & dentist




}
