/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package akura;

import java.sql.Date;
import org.exolab.castor.types.DateTime;

/**
 *
 * @author Pasindu01
 */
class PaymentData {
    String Reg_ID, Class, Month, Name_with_initials;
    int ID, Payment;
    Date Date;

    public PaymentData(String Reg_ID, String Class, String Month, int ID, int Payment, Date Date, String Name_with_initials) {
        this.Reg_ID = Reg_ID;
        this.Class = Class;
        this.Month = Month;
        this.ID = ID;
        this.Payment = Payment;
        this.Date = Date;
        this.Name_with_initials = Name_with_initials;
    }

    public void setReg_ID(String Reg_ID) {
        this.Reg_ID = Reg_ID;
    }

    public void setClassP(String Class) {
        this.Class = Class;
    }

    public void setMonth(String Month) {
        this.Month = Month;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setPayment(int Payment) {
        this.Payment = Payment;
    }

    public void setDate(Date Date) {
        this.Date = Date;
    }

    public void setName_with_initials(String Name_with_initials) {
        this.Name_with_initials = Name_with_initials;
    }
    

    public String getReg_ID() {
        return Reg_ID;
    }

    public String getClassP() {
        return Class;
    }

    public String getMonth() {
        return Month;
    }

    public int getID() {
        return ID;
    }

    public int getPayment() {
        return Payment;
    }

    public Date getDate() {
        return Date;
    }

    public String getName_with_initials() {
        return Name_with_initials;
    }
    
    
}
