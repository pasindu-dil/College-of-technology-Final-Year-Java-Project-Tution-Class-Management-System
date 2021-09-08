/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package akura;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Pasindu01
 */
public class userAuth {
    
//    Students std = new Students();
    
    private String auth;
    private JPanel panel;
    
    
    public void setuserAuthenticate(String auth){
        this.auth = auth;
    }
    
    public void setComponent(JPanel btn){
        this.panel = btn;
    }
    
    
    public void limitAccess(){
        if(auth.equals("User")){
            panel.setVisible(false);
        }
    }
}


