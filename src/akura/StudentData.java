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
class StudentData {
    private String Reg_ID, Full_Name, Name_with_initials, DoB, City, Gender, Address1, Address2, Address3,
            Mobile_No, NIC, ParentsName, ParentsMobile, Grade, Category, Stream, Exam_Year, School_Name;

    public StudentData(String Reg_ID, String Name_with_initials, String City, String Gender, String Address1, String Address2, String Mobile_No, String NIC, String Grade) {
        this.Reg_ID = Reg_ID;
        this.Name_with_initials = Name_with_initials;
        this.City = City;
        this.Gender = Gender;
        this.Address1 = Address1;
        this.Address2 = Address2;
        this.Mobile_No = Mobile_No;
        this.NIC = NIC;
        this.Grade = Grade;
    }

    public String getReg_ID() {
        return Reg_ID;
    }

    public String getName_with_initials() {
        return Name_with_initials;
    }

    public String getCity() {
        return City;
    }

    public String getGender() {
        return Gender;
    }

    public String getAddress1() {
        return Address1;
    }

    public String getAddress2() {
        return Address2;
    }

    public String getMobile_No() {
        return Mobile_No;
    }

    public String getNIC() {
        return NIC;
    }

    public String getGrade() {
        return Grade;
    }

    public void setReg_ID(String Reg_ID) {
        this.Reg_ID = Reg_ID;
    }

    public void setName_with_initials(String Name_with_initials) {
        this.Name_with_initials = Name_with_initials;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    public void setAddress1(String Address1) {
        this.Address1 = Address1;
    }

    public void setAddress2(String Address2) {
        this.Address2 = Address2;
    }

    public void setMobile_No(String Mobile_No) {
        this.Mobile_No = Mobile_No;
    }

    public void setNIC(String NIC) {
        this.NIC = NIC;
    }

    public void setGrade(String Grade) {
        this.Grade = Grade;
    }
    
    
}
