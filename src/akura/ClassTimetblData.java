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
class ClassTimetblData {
    
    String Class_ID, Class_Name, Grade, Schedule_date, Sche_time,  subjectName, teacherName;

    public ClassTimetblData(String Class_ID, String Class_Name, String Grade, String Schedule_date, String Sche_time,  String subjectName, String teacherName) {
        this.Class_ID = Class_ID;
        this.Class_Name = Class_Name;
        this.Grade = Grade;
        this.Schedule_date = Schedule_date;
        this.Sche_time = Sche_time;
        this.subjectName = subjectName;
        this.teacherName = teacherName;
    }

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

    public void setSche_time(String Sche_time) {
        this.Sche_time = Sche_time;
    }

    

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

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

    public String getSche_time() {
        return Sche_time;
    }

    

    public String getSubjectName() {
        return subjectName;
    }

    public String getTeacherName() {
        return teacherName;
    }
    
    
    
}
