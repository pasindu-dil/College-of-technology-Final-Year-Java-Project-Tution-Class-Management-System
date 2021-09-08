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
class subjectDate {
    
    String Subject_ID, Subject_Name, Stream;

    public subjectDate(String Subject_ID, String Subject_Name, String Stream) {
        this.Subject_ID = Subject_ID;
        this.Subject_Name = Subject_Name;
        this.Stream = Stream;
    }

    public void setSubject_ID(String Subject_ID) {
        this.Subject_ID = Subject_ID;
    }

    public void setSubject_Name(String Subject_Name) {
        this.Subject_Name = Subject_Name;
    }

    public void setStream(String Stream) {
        this.Stream = Stream;
    }

    public String getSubject_ID() {
        return Subject_ID;
    }

    public String getSubject_Name() {
        return Subject_Name;
    }

    public String getStream() {
        return Stream;
    }
    
    
    
}
