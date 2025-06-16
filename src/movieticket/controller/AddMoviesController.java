package movieticket.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JOptionPane;
import movieticket.dao.CRUDAdminDAO;
import movieticket.model.MovieData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import movieticket.view.DashboardView;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Hp
 */
public class AddMoviesController {
    
    DashboardView dashboardView;
    private final CRUDAdminDAO adminDAO = new CRUDAdminDAO();
    
    public AddMoviesController(DashboardView dashboardView){
        this.dashboardView = dashboardView;
        
        dashboardView.insertMoviesListener(new InsertMovieListener());
}
    
    
   public class InsertMovieListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String title = dashboardView.getMovieTitle().getText().trim();            
            String genre = dashboardView.getGenre().getText().trim();           
            String duration =  dashboardView.getDuration().getText().trim();           
            String Date =  dashboardView.getPublishedDate().getText().trim();
//            File image =  dashboardView.getSelectedFile();

System.out.println("here1: "+title+ genre+ duration+ Date);
            
            if (title.isEmpty() || genre.isEmpty() || duration.isEmpty() || Date.isEmpty()) {
                JOptionPane.showMessageDialog(dashboardView, 
                        "Please fill in all the fields.", "Validation Error", 
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            
//            if (image == null || !image.exists() || !image.isFile()) {
//                JOptionPane.showMessageDialog(dashboardView, 
//                        "Please select a valid image file.", "Validation Error", 
//                        JOptionPane.ERROR_MESSAGE);
//                return;
//            }
            
            try {
//                byte[] imageData = Files.readAllBytes(image.toPath());
               MovieData movieData = new MovieData(title, genre, duration, Date);
                boolean result = adminDAO.insertMovies(movieData);
                
                if (result) {
                    JOptionPane.showMessageDialog(dashboardView, 
                            "Movie added successfully!", "Success", 
                            JOptionPane.INFORMATION_MESSAGE);
//                    clearFields();
//                    loadMoviesToTable();
                } else {
                    JOptionPane.showMessageDialog(dashboardView, 
                            "Failed to add movie.", "Error", 
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(dashboardView, ex.getMessage(),
                        "DB error", JOptionPane.ERROR_MESSAGE);
            }
        }
        
    }
    
    
    
    
    
    
    
    
}
