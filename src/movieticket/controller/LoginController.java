/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package movieticket.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import movieticket.controller.mail.SMTPSMailSender;
import movieticket.dao.UserDao;
import movieticket.model.LoginRequest;
import movieticket.model.ResetPasswordRequest;
import movieticket.model.UserData;
import movieticket.view.AdminMovies;
import movieticket.view.DashboardView;
import movieticket.view.LoginView;
import movieticket.view.RegistrationView;
import movieticket.view.UserDashboardView;

/**
 *
 * @author Hp
 */
public class LoginController {
    private final UserDao userDao= new UserDao();
    private final LoginView loginView;
    private boolean isPasswordVisible = false;
    public LoginController( LoginView loginView){
        this.loginView=loginView;
        loginView.addLoginUserListener(new LoginUserListener());
        loginView.addRegisterListener(new RegistrationListener());
        loginView.showPasswordButtonListener(new ShowPasswordListener());
        ResetPassword resetPass = new ResetPassword();
        this.loginView.forgotPassword(resetPass);

    }
    public void open(){
        this.loginView.setVisible(true);
    }
    public void close(){
        this.loginView.dispose();
    }
    class ResetPassword implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            String email = JOptionPane.showInputDialog(loginView,"Enter your email");
            if(email.isEmpty()){
                JOptionPane.showMessageDialog(loginView, "Email cannot be empty");
            } else {
                UserDao userDao = new UserDao();
                boolean emailExists = userDao.checkEmail(email);
                if (!emailExists){
                    JOptionPane.showMessageDialog(loginView, "Email does not exists");
                }else{
                    String otp = "987586";
//                    SMTPSMailSender smtpSender = new SMTPSMailSender();
                    String title = "Reset Password Verification";
                    String body = " The otp to resetb your password is"+otp;
                    boolean mailSent = SMTPSMailSender.sendMail(email,title,body);
                    if (!mailSent){
                        JOptionPane.showMessageDialog(loginView,"Failed to send OTP. Try again later.");
                    }else{
                        String otpReceived = JOptionPane.showInputDialog(loginView,"Enter your otp code");
                        if(!otp.equals(otpReceived)){
                            JOptionPane.showMessageDialog(loginView,"Otp did not match.");
                        }else{
                            String password = JOptionPane.showInputDialog(loginView, "Enter your new password");
                            if(password.trim().isEmpty()){
                                JOptionPane.showMessageDialog(loginView,"Password cannot be empty");
                            }else{
                                ResetPasswordRequest resetReq = new ResetPasswordRequest(email,password);
                                boolean updateResult = userDao.resetPassword(resetReq);
                                if(!updateResult){
                                    JOptionPane.showMessageDialog(loginView, "Failed to reset password");
                                } else{
                                JOptionPane.showMessageDialog(loginView, "Password has been changed");
                            }
                                
                            }
                        }
                    }
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
        
    }
    class RegistrationListener implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            RegistrationView registrationView= new RegistrationView();
            RegistrationController registrationController= new RegistrationController(registrationView);
            registrationController.open();
            close();
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {
        
        }
        
    }
    class ShowPasswordListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        isPasswordVisible = !isPasswordVisible;
        loginView.togglePasswordField(isPasswordVisible);
    }
    }
    
    class LoginUserListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
             String email= loginView.getEmailTextField().getText();
            String password= String.valueOf(loginView.getPasswordField().getPassword());
            if(email.isEmpty()||password.isEmpty()){
                 
                JOptionPane.showMessageDialog(loginView, 
                        "Please fill in all the fields.", "Validation Error", 
                        JOptionPane.ERROR_MESSAGE);
                
            } else {
                LoginRequest loginRequest= new LoginRequest(email,password);
                UserData user=userDao.loginUser(loginRequest);
                System.out.println(user);
                if(user != null){
                    
                    JOptionPane.showMessageDialog(loginView, 
                        "Login Successfull");
                    if (user.isAdmin()) {
                        // Open Admin Panel
                        DashboardView dashboardView = new DashboardView();
                        DashboardController dashboardController = new DashboardController(dashboardView);
                        AddMoviesController addmoviescontroller= new  AddMoviesController(dashboardView);
                        dashboardController.open();
                       
                    } else {
                        // Open User Dashboard
                        
                       
                       UserDashboardView userdashboardView = new UserDashboardView(user.getId());
                        UserDashboardController userdashboardController = new UserDashboardController(userdashboardView);
                       
                        userdashboardController.open();
                    }
                    
                    close();
                } else{
                         JOptionPane.showMessageDialog(loginView, 
                        "Login Failed");
                         System.out.println("Insert Failed");
                     }
                     
                
            }
        }
        
    }
}
