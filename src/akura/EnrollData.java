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
class EnrollData {
    String Enroll_ID, Class_ID, Subject_Name, Std_Reg_No, Name_with_initials, Class_Name;

//Constructor for tble data
    public EnrollData(String Enroll_ID, String Class, String Subject_Name, String Std_Reg_No, String Name_with_initials, String Class_Name) {
        this.Class_ID = Class;
        this.Subject_Name = Subject_Name;
        this.Std_Reg_No = Std_Reg_No;
        this.Name_with_initials = Name_with_initials;
        this.Class_Name = Class_Name;
        this.Enroll_ID = Enroll_ID;
    }

//    Setters
    public void setClassID(String Class) {
        this.Class_ID = Class;
    }

    public void setSubject_Name(String Subject_Name) {
        this.Subject_Name = Subject_Name;
    }

    public void setStd_Reg_No(String Std_Reg_No) {
        this.Std_Reg_No = Std_Reg_No;
    }

    public void setName_with_initials(String Name_with_initials) {
        this.Name_with_initials = Name_with_initials;
    }

    public void setClass_Name(String Class_Name) {
        this.Class_Name = Class_Name;
    }

    public void setEnroll_ID(String Enroll_ID) {
        this.Enroll_ID = Enroll_ID;
    }

    
//    Getters
    public String getClassID() {
        return Class_ID;
    }

    public String getSubject_Name() {
        return Subject_Name;
    }

    public String getStd_Reg_No() {
        return Std_Reg_No;
    }

    public String getName_with_initials() {
        return Name_with_initials;
    }

    public String getClass_Name() {
        return Class_Name;
    }

    public String getEnroll_ID() {
        return Enroll_ID;
    }

    
    
    
}
