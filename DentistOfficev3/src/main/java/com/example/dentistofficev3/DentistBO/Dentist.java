package com.example.dentistofficev3.DentistBO;

import java.sql.*;

public class Dentist {
    private String id;
    private String passwd;
    private String firstname;
    private String lastname;
    private String email;
    private String office;
    public DentistAppointmentList dList;
    public DentistsList dentists;

    private static final String URL = "jdbc:ucanaccess://C:/Users/vonny/Downloads/DentistOfficeMDB.mdb";

    //Constructor
    public Dentist() {
        this.id = "";
        this.passwd = "";
        this.firstname = "";
        this.lastname = "";
        this.email = "";
        this.office = "";
        this.dentists = new DentistsList();
        this.dList = new DentistAppointmentList();
    }

    public Dentist(String id, String password, String firstname, String lastname, String email, String office) {
        this.id = id;
        this.passwd = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.office = office;
        this.dentists = new DentistsList();
        this.dList = new DentistAppointmentList();
    }

    //Getter and Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getPassword() {
        return passwd;
    }

    public void setPassword(String passwd) {
        this.passwd = passwd;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;

    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public DentistsList getDentists() {
        return dentists;
    }

    @Override
    public String toString() {
        return "Dentist{" +
                "id='" + getId() + '\'' +
                "password='" + getPassword() + '\'' +
                "firstName='" + getFirstname() + '\'' +
                "lastName= '" + getLastname() + '\'' +
                "email= '" + getEmail() + '\'' +
                "office= '" + getOffice() + '\'' +
                '}';

    }

    public void selectAllDB() {

        String url = "jdbc:ucanaccess://C:/Users/vonny/Downloads/DentistOfficeMDB.mdb";
        try {
            //1. Load driver
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

            //2. Connect
            Connection conn = DriverManager.getConnection(url);

            //3. SQL
            String sql = "SELECT * FROM Dentists";
            System.out.println(sql);
            PreparedStatement stmt = conn.prepareStatement(sql);


            System.out.println("Executing Query: " + sql);

            //4. Execute
            ResultSet rs = stmt.executeQuery();

            //if

            while (rs.next()) {

                setFirstname(rs.getString(1));
                setLastname(rs.getString(2));
                setEmail(rs.getString(3));
                setId(rs.getString(4));
                setPassword(rs.getString(5));
                setOffice(rs.getString(6));

                System.out.println("ADDING: " + getId() + " " + getFirstname());

                dentists.addDentist(new Dentist(getId(), getPassword(), getFirstname(), getLastname(), getEmail(), getOffice()));
                System.out.println("Dentist filled: " + this.id + " " + this.passwd + " " + this.firstname + " " + this.lastname + " " + this.email + " " + this.office);
            }

            //5. Close
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Error in selectDB: " + e.getMessage());
        }
    }

    // Select
    public void selectDB(String denID) throws ClassNotFoundException {
        String url = "jdbc:ucanaccess://C:/Users/vonny/Downloads/DentistOfficeMDB.mdb";
        //1. Load driver
        Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

        try {
            //2. Connect
            Connection conn = DriverManager.getConnection(url);

            //3. SQL
            String sql = "SELECT * FROM Dentists WHERE id = ?";
            System.out.println(sql);
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, denID);

            System.out.println("Executing Query: " + sql);

            //4. Execute
            ResultSet rs = stmt.executeQuery();

            //if

            while (rs.next()) {
                setId(rs.getString("id"));
                setPassword(rs.getString("passwd"));
                setFirstname(rs.getString("firstName"));
                setLastname(rs.getString("lastName"));
                setEmail(rs.getString("email"));
                setOffice(rs.getString("office"));



                System.out.println("Account filled: " + this.id + " " + this.passwd + " " + this.firstname + " " + this.lastname + " " + this.email + " " + this.office);
            }

            //5. Close
            rs.close();
            stmt.close();
            conn.close();

            loadAppointments();
        } catch (Exception e) {
            System.out.println("Error in selectDB: " + e.getMessage());
        }
    }

    //Update
    public void updateDb(String id, String passWd, String firstName, String lastName, String office, String email) {
        String url = "jdbc:ucanaccess://C:/Users/vonny/Downloads/DentistOfficeMDB.mdb";
        try {
            //1. Load driver
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

            //2. Connect
            Connection conn = DriverManager.getConnection(url);

            //3. SQL
            String sql = "UPDATE Dentists SET passwd=?, firstName=?, lastName=?, office=?, email=? WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, passWd);
            stmt.setString(2, firstName);
            stmt.setString(3, lastName);
            stmt.setString(4, office);
            stmt.setString(5, email);
            stmt.setString(6, id);

            stmt.executeUpdate();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("Error in updateDb: " + e.getMessage());
        }

    }

    public void loadAppointments() throws ClassNotFoundException, SQLException {
        String url = "jdbc:ucanaccess://C:/Users/vonny/Downloads/DentistOfficeMDB.mdb";

        Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
        Connection conn = DriverManager.getConnection(URL);
        String sql = "SELECT * FROM Appointments WHERE dentId = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, this.id);

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            DentistAppointment da = new DentistAppointment();
            da.setAppDateTime(rs.getString("apptDateTime"));
            da.setPatId(rs.getString("patId"));
            da.setDentId(rs.getString("dentId"));
            da.setProCode(rs.getString("procCode"));

            dList.addAccount(da);
        }

        rs.close();
        stmt.close();
        conn.close();
    }

}



