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
public class TeacherData {
    String Teacher_ID, FullName, Name_with_initials, Address, NIC, Mobile_No, Other;
    
    public TeacherData(String Teacher_ID, String FullName, String Name_with_initials, String Address, String NIC, String Mobile_No, String Other) {
        this.Teacher_ID = Teacher_ID;
        this.FullName = FullName;
        this.Name_with_initials = Name_with_initials;
        this.Address = Address;
        this.NIC = NIC;
        this.Mobile_No = Mobile_No;
        this.Other = Other;
    }

    public void setTeacher_ID(String Teacher_ID) {
        this.Teacher_ID = Teacher_ID;
    }

    public void setFullName(String FullName) {
        this.FullName = FullName;
    }

    public void setName_with_initials(String Name_with_initials) {
        this.Name_with_initials = Name_with_initials;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public void setNIC(String NIC) {
        this.NIC = NIC;
    }

    public void setMobile_No(String Mobile_No) {
        this.Mobile_No = Mobile_No;
    }

    

    public void setOther(String Other) {
        this.Other = Other;
    }

    
    

    public String getTeacher_ID() {
        return Teacher_ID;
    }

    public String getFullName() {
        return FullName;
    }

    public String getName_with_initials() {
        return Name_with_initials;
    }

    public String getAddress() {
        return Address;
    }

    public String getNIC() {
        return NIC;
    }

    public String getMobile_No() {
        return Mobile_No;
    }

    

    public String getOther() {
        return Other;
    }

    
    
    
    
}
