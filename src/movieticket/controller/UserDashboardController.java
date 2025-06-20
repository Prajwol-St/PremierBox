/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package movieticket.controller;

import java.awt.CardLayout;
import javax.swing.JButton;
import movieticket.view.UserDashboardView;

/**
 *
 * @author Hp
 */
public class UserDashboardController {
     UserDashboardView userdashboardView;
      private final CardLayout      cards; 
     
     public UserDashboardController(UserDashboardView userdashboardView){
        this.userdashboardView= userdashboardView;
        this.cards = (CardLayout)userdashboardView.getCardPanel().getLayout();
        wire();
        
      
    }
     
      private void wire() {
        map(userdashboardView.getDashboard(),    "UserDashboard");
       
        map(userdashboardView.getAvailableMovies(),"AvailableMovies");
       
    }
     
       private void map(JButton b, String card) {
        b.addActionListener(e -> cards.show(userdashboardView.getCardPanel(), card)); // swap[5]
    }
    
     
      
    public void open(){
        this.userdashboardView.setVisible(true);
    }
    public void close(){
        this.userdashboardView.dispose();
    }
    
}
