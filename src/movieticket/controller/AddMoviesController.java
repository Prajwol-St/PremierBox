package movieticket.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JOptionPane;
import movieticket.dao.CRUDAdminDAO;
import movieticket.model.MovieData;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import movieticket.model.MoviesData;

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
    private int selectedMovieId = -1;
    
    public AddMoviesController(DashboardView dashboardView){
        this.dashboardView = dashboardView;
        
        dashboardView.insertMoviesListener(new InsertMovieListener());
        
         // Load movies when controller initializes
        loadMoviesToTable();
        
        // Add table selection listener
        dashboardView.getMovieTable().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow =  dashboardView.getMovieTable().getSelectedRow();
                if (selectedRow >= 0) {
                    DefaultTableModel model = (DefaultTableModel)  dashboardView.getMovieTable().getModel();
                    selectedMovieId = (int) model.getValueAt(selectedRow, 0);
                    try {
                        MoviesData movie = adminDAO.getMovieById(selectedMovieId);
                        if (movie != null) {
                             dashboardView.getMovieTitle().setText(movie.getTitle());
                             dashboardView.getGenre().setText(movie.getGenre());
                             dashboardView.getDuration().setText(movie.getDuration());
                             dashboardView.getPublishedDate().setText(movie.getDate());
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(dashboardView, "Error loading movie details: " + ex.getMessage(), 
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
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
                    clearFields();
                    loadMoviesToTable();
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
    
//    Load datas to table in add movies section
       private void loadMoviesToTable() {
        try {
            List<MoviesData> movies = adminDAO.getAllMovies();
            DefaultTableModel model = (DefaultTableModel) dashboardView.getMovieTable().getModel();
            model.setRowCount(0); // Clear existing data
            
            for (MoviesData movie : movies) {
                model.addRow(new Object[]{
                    movie.getMovie_id(),
                    movie.getTitle(),
                    movie.getGenre(),
                    movie.getDuration(),
                    movie.getDate()
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(dashboardView, "Error loading movies: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
     private void clearFields() {
        dashboardView.getMovieTitle().setText("");
         dashboardView.getGenre().setText("");
         dashboardView.getDuration().setText("");
         dashboardView.getPublishedDate().setText("");
//         dashboardView.setSelectedFile(null);
    }
    
    
    
    
}
