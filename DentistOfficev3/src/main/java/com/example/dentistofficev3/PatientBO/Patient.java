package com.example.dentistofficev3.PatientBO;


import java.sql.*;
import java.util.concurrent.Callable;

public class Patient {
    private String patId;
    private String passWd;
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String insCo;

    public PatientAppointmentList aList =new PatientAppointmentList();


    private static final String URL = "jdbc:ucanaccess://C:/Users/vonny/Downloads/DentistofficeMDB.mdb";

    //constructor
    public Patient() {
        this.patId = " ";
        this.passWd = " ";
        this.firstName = " ";
        this.lastName = " ";
        this.address = " ";
        this.email = " ";
        this.insCo = " ";
    }

    public Patient(String padId, String passWd, String firstName, String lastName, String address, String email) {
        this.patId = padId;
        this.passWd = passWd;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.insCo = null;
    }

    public Patient(String patId, String passWd) {

    }

    //Getter And Setter
    public String getPadId() {
        return patId;
    }

    public void setPadId(String padId) {
        this.patId = padId;
    }

    public String getPassWd() {
        return passWd;
    }

    public void setPassWd(String passWd) {
        this.passWd = passWd;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInsCo() {
        return insCo;
    }

    public void setInsCo(String insCo) {
        this.insCo = insCo;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "padId='" + getPadId() + '\'' +
                ",passWd='" + getPassWd() + '\'' +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", address='" + getAddress() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", insCo='" + getInsCo() + '\'' +
                '}';
    }

    public void getAppointments(){
        String sql="select * from Appointments where patId= ?";

        String url = "jdbc:ucanaccess://C:/Users/vonny/Downloads/DentistOfficeMDB.mdb";
        System.out.println("PatId: "+getPadId());

        try{
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

            Connection conn= DriverManager.getConnection(url);

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, this.patId);
            System.out.println("GetAppt:"+ this.patId);
            ResultSet rs = pstmt.executeQuery();

            String an;
            PatientAppointment pa1;

            while(rs.next()){
            an = rs.getString(3);
            pa1 = new PatientAppointment();
            pa1.selectDB(an);
            aList.addAccount(pa1);
            }

            rs.close();
            pstmt.close();
            conn.close();
            }

        catch(Exception e){
            System.out.println("pp:" +e);

        }


    }

    // Select
    public void selectDB(String acctNumber) {

        String url = "jdbc:ucanaccess://C:/Users/vonny/Downloads/DentistOfficeMDB.mdb";
        try {
            //1. Load driver
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

            //2. Connect
            Connection conn = DriverManager.getConnection(URL);

            //3. SQL
            String sql = "SELECT * FROM Patients WHERE patId = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, acctNumber);

            System.out.println("Patient: "+  sql);
            //System.out.println("Executing Query: " + sql);

            //4. Execute
            ResultSet rs = stmt.executeQuery();
            //System.out.println("Results:" + rs);


            while (rs.next()) {
                setFirstName(rs.getString(1));
                setLastName(rs.getString(2));
                setAddress(rs.getString(3));
                setEmail(rs.getString(4));
                setInsCo(rs.getString(5));
                setPadId(rs.getString(6));
                setPassWd(rs.getString(7));


                System.out.println("Patient filled: " + this.patId + " " + this.passWd + " " + this.firstName + " " + this.lastName + " " + this.address + " " + this.email + " " + this.insCo);
            }

            //5. Close
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Error in selectDB: " + e.getMessage());
        }

        //Pulls aapointments
        //getAppointments();

    }

    //Insert
    public void insertDB(String patId, String passWd, String firstName, String lastName, String address) {
        String url = "jdbc:ucanaccess://C:/Users/vonny/Downloads/DentistOfficeMDB.mdb";
        try {
            Class.forName("net.ucanaccess.jdbc.Ucanaccess.Driver");
            Connection conn = DriverManager.getConnection(URL);

            String sql = "Insert into Patients (padId, passWd, firstname, lastName, address, email, insCo) values (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, patId);
            pstmt.setString(2, passWd);
            pstmt.setString(3, firstName);
            pstmt.setString(4, lastName);
            pstmt.setString(5, address);
            pstmt.setString(6, email);
            pstmt.setString(7, insCo);

            pstmt.executeUpdate();

            conn.close();
        } catch (Exception e) {
            System.out.println("Error in insertDB: " + e.getMessage());
        }

    }

    //Update
    public void updateDb(String patId, String passWd, String firstName, String lastName, String address, String email,String insCo) {
        String url = "jdbc:ucanaccess://C:/Users/vonny/Downloads/DentistOfficeMDB.mdb";
        try {
            //1. Load driver
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

            //2. Connect
            Connection conn = DriverManager.getConnection(url);

            //3. SQL
            String sql = "UPDATE Patients SET passWd=?, firstName=?, lastName=?, addr=?, email=?, insCo=? WHERE patId=?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, passWd);
            stmt.setString(2, firstName);
            stmt.setString(3, lastName);
            stmt.setString(4, address);
            stmt.setString(5, email);
            stmt.setString(6, insCo);
            stmt.setString(7, patId);

            stmt.executeUpdate();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("Error in updateDb: " + e.getMessage());
        }

    }

    //Delete
    public void deleteDB() {
        try {
            //1. Load driver
            Class.forName("net.ucanaccess.jdbc.Ucanaccess.Driver");

            //2. Connect
            Connection conn = DriverManager.getConnection(URL);


            String sql = "delete from Patients where acctNo ='" + patId + "'";
            System.out.println(sql);

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, patId);

            pstmt.executeUpdate(sql);

            conn.close();
        } catch (Exception e) {
            System.out.println("Error in deleteDB" + e.getMessage());
        }
    }
    public void display(){
        System.out.println("First Name: " + getFirstName());
        System.out.println("Last Name: " + getLastName());
        System.out.println("Address: " + getAddress());
        System.out.println("Email: " + getEmail());
        System.out.println("InsCo: " + getInsCo());
    }

    public static void main(String[] args) {
        Patient p1 =  new Patient();
        p1.selectDB("A900");
        p1.display();
    }
}







