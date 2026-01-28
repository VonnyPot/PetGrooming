package com.example.dentistofficev3.DentistBO;

public class DentistAppointmentList {
    public int count;
    public DentistAppointment dentList[] = new DentistAppointment[15];

    public DentistAppointmentList(){
        this.count = 0;
        this.dentList = new DentistAppointment[15];
    }

    public void addAccount (DentistAppointment d1) {
        dentList[count] = d1;
        count++;
    }
}
