/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package akura;

/**
 *
 * @author Pasindu01
 */
class ClassData {
    
    String Class_ID, Class_Name, Grade, Schedule_date, Name_with_initials, Subject_Name, Sche_time;
    Double Class_Fee;

//    Constructor Method
    public ClassData(String Class_ID, String Class_Name, String Grade, String Schedule_date, String Name_with_initials, String Subject_Name, String Sche_time, double Class_Fee) {
        this.Class_ID = Class_ID;
        this.Class_Name = Class_Name;
        this.Grade = Grade;
        this.Schedule_date = Schedule_date;
        this.Name_with_initials = Name_with_initials;
        this.Subject_Name = Subject_Name;
        this.Sche_time = Sche_time;
        this.Class_Fee = Class_Fee;
    }



//    Setters
    public void setClass_ID(String Class_ID) {
        this.Class_ID = Class_ID;
    }

    public void setClass_Name(String Class_Name) {
        this.Class_Name = Class_Name;
    }

    public void setGrade(String Grade) {
        this.Grade = Grade;
    }

    public void setSchedule_date(String Schedule_date) {
        this.Schedule_date = Schedule_date;
    }

    public void setName_with_initials(String Name_with_initials) {
        this.Name_with_initials = Name_with_initials;
    }

    public void setSubject_Name(String Subject_Name) {
        this.Subject_Name = Subject_Name;
    }

    public void setSche_time(String Sche_time) {
        this.Sche_time = Sche_time;
    }

    public void setClass_Fee(Double Class_Fee) {
        this.Class_Fee = Class_Fee;
    }
    

//    Getters
    public String getClass_ID() {
        return Class_ID;
    }

    public String getClass_Name() {
        return Class_Name;
    }

    public String getGrade() {
        return Grade;
    }

    public String getSchedule_date() {
        return Schedule_date;
    }

    public String getName_with_initials() {
        return Name_with_initials;
    }

    public String getSubject_Name() {
        return Subject_Name;
    }

    public String getSche_time() {
        return Sche_time;
    }

    public Double getClass_Fee() {
        return Class_Fee;
    }
    
    
    
    
}
