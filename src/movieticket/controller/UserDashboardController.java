/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package movieticket.controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import movieticket.view.EntryView;
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
        userdashboardView.logoutMovieListener(new LogoutMovieListener());
        wire();
        
      
    }
     
     //   Logout admin dashboardView
   public class LogoutMovieListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
        
         int choice = JOptionPane.showConfirmDialog(
                userdashboardView,                       // parent component
                "Are you sure you want to log out?",  // message
                "Confirm Logout",                // dialog title
                JOptionPane.YES_NO_OPTION,       // two buttons
                JOptionPane.QUESTION_MESSAGE);   // question-mark icon

        if (choice == JOptionPane.YES_OPTION) {  // user clicked “Yes”
            EntryView entryView = new EntryView();          // open login
            EntryController entryController = new EntryController(entryView);
            entryController.open();
            close();            // close the admin window
        }
        }
        
       
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
