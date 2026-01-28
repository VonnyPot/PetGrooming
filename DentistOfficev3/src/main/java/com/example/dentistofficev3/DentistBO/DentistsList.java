package com.example.dentistofficev3.DentistBO;

public class DentistsList {
    public int count = 0;
    public Dentist[] dentList = new Dentist[15];

    public void addDentist (Dentist d1) {
        dentList[count] = d1;
        count++;
    }
}
