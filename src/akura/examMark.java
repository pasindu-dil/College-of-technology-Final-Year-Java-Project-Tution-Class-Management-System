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
class examMark {
    String Reg_ID, Name_with_initials, Exam_ID, Subject_Name;
    int Marks, ID;

    public examMark(int ID, String Reg_ID, String Name_with_initials, String Exam_ID, String Subject_Name, int Marks) {
        this.ID = ID;
        this.Reg_ID = Reg_ID;
        this.Name_with_initials = Name_with_initials;
        this.Exam_ID = Exam_ID;
        this.Subject_Name = Subject_Name;
        this.Marks = Marks;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setReg_ID(String Reg_ID) {
        this.Reg_ID = Reg_ID;
    }

    public void setName_with_initials(String Name_with_initials) {
        this.Name_with_initials = Name_with_initials;
    }

    public void setExam_ID(String Exam_ID) {
        this.Exam_ID = Exam_ID;
    }

    public void setSubject_Name(String Subject_Name) {
        this.Subject_Name = Subject_Name;
    }

    public void setMarks(int Marks) {
        this.Marks = Marks;
    }

    public int getID() {
        return ID;
    }
    
    public String getReg_ID() {
        return Reg_ID;
    }

    public String getName_with_initials() {
        return Name_with_initials;
    }

    public String getExam_ID() {
        return Exam_ID;
    }

    public String getSubject_Name() {
        return Subject_Name;
    }

    public int getMarks() {
        return Marks;
    }
    
    
    
}
