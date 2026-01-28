package com.example.dentistofficev3.DentistBO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Procedures {
    private String proCode;
    private String proName;
    private String proDesc;
    private int Cost;
    private ProceduresList procList;


    private static final String URL = "jdbc:ucanaccess://C:/Users/vonny/Downloads/DentistOfficeMDB.mdb";

    //Constructor
    public Procedures() {
        this.proCode = " ";
        this.proName = " ";
        this.proDesc = " ";
        this.Cost = 0;
        this.procList = new ProceduresList();
    }

    public Procedures(String proCode, String proName, String proDesc, int Cost) {
        this.proCode = proCode;
        this.proName = proName;
        this.proDesc = proDesc;
        this.Cost = Cost;

    }

    //Getter and Setter
    public String getproCode() {
        return proCode;
    }

    public void setProCode(String proCode) {
        this.proCode = proCode;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getProDesc() {
        return proDesc;
    }

    public void setProDesc(String proDesc) {
        this.proDesc = proDesc;
    }

    public int getCost() {
        return Cost;
    }

    public void setCost(int cost) {
        this.Cost = cost;
    }

    public ProceduresList getProcList() {
        return procList;
    }

    @Override
    public String toString() {
        return "Procedures{" +
                "proCode='" + getproCode() + '\'' +
                ",proName='" + proName + '\'' +
                ",proDesc='" + proDesc + '\'' +
                ", cost='" + Cost + '\'' +
                '}';

    }

    // Select
    public void selectAllDB() {

        String url = "jdbc:ucanaccess://C:/Users/vonny/Downloads/DentistOfficeMDB.mdb";
        try {
            //1. Load driver
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

            //2. Connect
            Connection conn = DriverManager.getConnection(url);

            //3. SQL
            String sql = "SELECT * FROM Procedures";
            System.out.println(sql);
            PreparedStatement stmt = conn.prepareStatement(sql);



            System.out.println("Executing Query: " + sql);

            //4. Execute
            ResultSet rs = stmt.executeQuery();

            //if

            while (rs.next()) {

                setProCode(rs.getString(1));
                setProName(rs.getString(2));
                setProDesc(rs.getString(3));
                setCost(rs.getInt(4));

                procList.addProcedure(new Procedures(getproCode(), getProName(), getProDesc(), getCost()));
                System.out.println("Appointment filled: " + this.proCode + " " + this.proName + " " + this.proDesc + " " + this.Cost);
            }

            //5. Close
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Error in selectDB: " + e.getMessage());


        }
    }
}

