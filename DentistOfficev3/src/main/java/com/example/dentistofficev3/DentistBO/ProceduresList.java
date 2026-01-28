package com.example.dentistofficev3.DentistBO;

import java.sql.*;
import java.util.ArrayList;

public class ProceduresList {
    public int count;
    public Procedures[] procList = new Procedures[15];

    public void ProceduresList() {
        this.count = 0;
        this.procList = new Procedures[15];
    }

    public void addProcedure(Procedures p1){
        procList[count] = p1;
        count++;
    }
}
