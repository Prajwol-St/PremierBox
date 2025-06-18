/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package movieticket.controller;

import movieticket.view.UserDashboardView;

/**
 *
 * @author Hp
 */
public class UserDashboardController {
     UserDashboardView userdashboardView;
     
     public UserDashboardController(UserDashboardView userdashboardView){
        this.userdashboardView= userdashboardView;
      
    }
     
      
    public void open(){
        this.userdashboardView.setVisible(true);
    }
    public void close(){
        this.userdashboardView.dispose();
    }
    
}
