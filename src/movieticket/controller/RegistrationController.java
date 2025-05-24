/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package movieticket.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import movieticket.dao.UserDao;
import movieticket.model.UserData;
import movieticket.view.LoginView;
import movieticket.view.RegistrationView;

/**
 *
 * @author Hp
 */
public class RegistrationController {
       private final UserDao userDao= new UserDao();
    private boolean isPasswordVisible = false;
    private boolean isConfirmPasswordVisible = false;
    private final RegistrationView registrationView;
    public RegistrationController(RegistrationView registrationView){
        this.registrationView= registrationView;
        registrationView.addRegisterUserListener(new RegisterUserListener());
        registrationView.addLoginListener(new LoginListener());
        registrationView.showPasswordButtonListener(new ShowPasswordListener());
        registrationView.showPasswordButton1Listener(new ShowConfirmPasswordListener());
        registrationView.uploadImageButtonListener(new UploadImageListener());
    }
     public void open(){
        this.registrationView.setVisible(true);
    }
    public void close(){
        this.registrationView.dispose();
    }
    
//    navigate using loginLabel
    class LoginListener implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
          LoginView loginView= new LoginView();
          LoginController loginController= new LoginController(loginView);
          loginController.open();
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
        registrationView.togglePasswordField(isPasswordVisible);
    }
    }
    
    class ShowConfirmPasswordListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        isConfirmPasswordVisible = !isConfirmPasswordVisible;
        registrationView.toggleConfirmPasswordField(isConfirmPasswordVisible);
    }
    }
    
    class UploadImageListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(registrationView);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (file.exists() && file.isFile()) {
                registrationView.setSelectedFile(file);
            } else {
                JOptionPane.showMessageDialog(registrationView,
                    "Invalid file selected.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    }
    
    
    class RegisterUserListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String name= registrationView.getNameTextField().getText();            
            String email= registrationView.getEmailTextField().getText();
            String password= String.valueOf(registrationView.getPasswordField().getPassword());
            String confirmPassword = String.valueOf(registrationView.getConfirmPasswordField().getPassword());
            File image= registrationView.getSelectedFile();
            System.out.println("Attempting to register user: " + email);
            
//            validation
            if (name.isEmpty()||email.isEmpty()||password.isEmpty()||confirmPassword.isEmpty()){
                
                JOptionPane.showMessageDialog(registrationView, 
                        "Please fill in all the fields.", "Validation Error", 
                        JOptionPane.ERROR_MESSAGE);
                
            } else if (!password.equals(confirmPassword)){
                
                JOptionPane.showMessageDialog(registrationView, 
                        "Passwords do not match", "Validation Error", 
                        JOptionPane.ERROR_MESSAGE);

            } else if (image == null || !image.exists() || !image.isFile()) {
                
                JOptionPane.showMessageDialog(registrationView, 
                        "Please select a valid image file.", "Validation Error", 
                        JOptionPane.ERROR_MESSAGE);
                
            } else{
//                when data is validated
                try {
                    byte []imageData = Files.readAllBytes(image.toPath());
                    UserData userData= new UserData(name,email,password,imageData);
                    boolean result= userDao.registerUser(userData);
                    if(result == true){
                            JOptionPane.showMessageDialog(registrationView, 
                                "Registration Successfull");
                            LoginView loginView= new LoginView();
                            LoginController loginController= new LoginController(loginView);
                            loginController.open();
                            close();
                    } else{
                            JOptionPane.showMessageDialog(registrationView, 
                            "Registration Failed");
                            System.out.println("Insert Failed");
                    }
                } catch (IOException ex) {
                        Logger.getLogger(RegistrationController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }           
            }
        }   
}
