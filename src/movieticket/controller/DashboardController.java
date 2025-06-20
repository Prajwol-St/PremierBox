/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package movieticket.controller;

import java.awt.CardLayout;
import javax.swing.JButton;
import movieticket.view.DashboardView;

/**
 *
 * @author Hp
 */
public class DashboardController {
    DashboardView dashboardView;
      private final CardLayout      cards; 
      
    public DashboardController(DashboardView dashboardView){
        this.dashboardView=dashboardView;
        this.cards = (CardLayout)dashboardView.getCardPanel().getLayout();
        wire();
        
        


    }
    
     private void wire() {
        map(dashboardView.getDashboard(),    "dashboard");
        map(dashboardView.getAddMovies() , "addMovies");
        map(dashboardView.getAvailableMovies(),"availableMovies");
        map(dashboardView.getEditScreening(),"edit");
        map(dashboardView.getCustomer(),"customer");
    }
     
       private void map(JButton b, String card) {
        b.addActionListener(e -> cards.show(dashboardView.getCardPanel(), card)); // swap[5]
    }
    
    
    
    
    public void open(){
        this.dashboardView.setVisible(true);
    }
    public void close(){
        this.dashboardView.dispose();
    }
    
}
