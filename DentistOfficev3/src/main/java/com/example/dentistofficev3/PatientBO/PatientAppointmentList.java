package com.example.dentistofficev3.PatientBO;

import java.sql.*;
import java.util.ArrayList;

public class PatientAppointmentList {
  public int count;
  public PatientAppointment appArr[] = new PatientAppointment[10];

  public void PatientAppointmentList(){
      this.count = 0;
      this.appArr = new PatientAppointment[1];

  }

  public void PatientAppointmentList(int c, PatientAppointment a){
      this.count = c;
      this.appArr[count] = a;
  }

public int getCount() {
    return count;
}
public void setCount(int count) {
    this.count = count;
}

  public void addAccount(PatientAppointment p1){
    appArr[count] = p1;
    count++;
  }

    //Display
    public void displayList(){
        System.out.println("# of Patient Appt:" + count);
        for(int i = 0; i < count; i++){
            appArr[i].display();

        }
    }




    /*public String toString() {
      Stringbuilder sb = new StringBuilder();

      for (int i = 0; i < count; i++) {
          sb.append(AppArr[i].toString()).append("\n");
      }
      System.out.println("returned account strings: " +sb.toString());
      return sb.toString();
  }*/


  public static void main(String[] args)  {
      PatientAppointmentList aList = new PatientAppointmentList();
      PatientAppointment a = new PatientAppointment();
      PatientAppointment b = new PatientAppointment();

      //aList.addAppointment(a);
      //aList.addAppointment(b);
      //aList.displayList();
  }
}





