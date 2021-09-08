/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package akura;

import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author Pasindu01
 */
class examData {
    String Exam_ID, Class, sname, cname, Grade;
    Date Exam_Date;
    Time Time;

    public examData(String Exam_ID, String Class, String sname, String cname, Date Exam_Date, Time Time, String Grade) {
        this.Exam_ID = Exam_ID;
        this.Class = Class;
        this.sname = sname;
        this.cname = cname;
        this.Exam_Date = Exam_Date;
        this.Time = Time;
        this.Grade = Grade;
    }

    public void setExam_ID(String Exam_ID) {
        this.Exam_ID = Exam_ID;
    }

    public void setClass(String Class) {
        this.Class = Class;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public void setExam_Date(Date Exam_Date) {
        this.Exam_Date = Exam_Date;
    }

    public void setTime(Time Time) {
        this.Time = Time;
    }

    public void setGrade(String Grade) {
        this.Grade = Grade;
    }
    

    public String getExam_ID() {
        return Exam_ID;
    }

    public String getcClass() {
        return Class;
    }

    public String getSname() {
        return sname;
    }

    public String getCname() {
        return cname;
    }

    public Date getExam_Date() {
        return Exam_Date;
    }

    public Time getTime() {
        return Time;
    }

    public String getGrade() {
        return Grade;
    }
    
    
}
