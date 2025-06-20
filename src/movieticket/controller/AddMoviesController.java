package movieticket.controller;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javax.swing.JOptionPane;
import movieticket.dao.CRUDAdminDAO;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import movieticket.model.MoviesData;
import movieticket.model.UserData;

import movieticket.view.DashboardView;
import movieticket.view.EntryView;

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
    UserData userData;
    private int selectedMovieId = -1;
    
    public AddMoviesController(DashboardView dashboardView){
        this.dashboardView = dashboardView;
        
        dashboardView.importMovieListener (new ImportMovieListener());
        dashboardView.insertMoviesListener(new InsertMovieListener());
        dashboardView.updateMoviesListener(new UpdateMoviesListener());
        dashboardView.deleteMovieListener(new DeleteMovieListener());
        dashboardView.logoutMovieListener(new LogoutMovieListener());
        
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
                             
                             showPoster(movie.getPosterPath()); 
                               
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(dashboardView, "Error loading movie details: " + ex.getMessage(), 
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
}
    
    
    
    public class ImportMovieListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
        
          JFileChooser fc = new JFileChooser();
        if (fc.showOpenDialog(dashboardView) != JFileChooser.APPROVE_OPTION) return;

        File file = fc.getSelectedFile();
        if (file == null || !file.isFile()) {
            JOptionPane.showMessageDialog(dashboardView,"Invalid file.","Error",
                                          JOptionPane.ERROR_MESSAGE);
            return;
        }

        dashboardView.setSelectedFile(file);          // your existing setter

        /* ------- display poster in jPanel8 --------------------- */
        try {
            BufferedImage img = ImageIO.read(file);                 // load pixels[3]
            JLabel lbl       = dashboardView.getPosterLabel();               // the JLabel
            int w = lbl.getWidth(), h = lbl.getHeight();
            Image scaled = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
            lbl.setIcon(new ImageIcon(scaled));                     // show it[4][6]
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(dashboardView, ex.getMessage(),
                     "Cannot read image", JOptionPane.ERROR_MESSAGE);
        }
 
        
        }
       
        
    }
    
  private void showPoster(byte[] bytes) {
        JLabel lbl = dashboardView.getPosterLabel();      // make sure this returns JLabel
        if (bytes == null || bytes.length == 0) {
            lbl.setIcon(null);
            lbl.setText("No image");
            return;
        }
        try (ByteArrayInputStream in = new ByteArrayInputStream(bytes)) {
            BufferedImage img = ImageIO.read(in);
            if (img == null) throw new IllegalArgumentException("Not an image");
            Image scaled = img.getScaledInstance(
                    lbl.getWidth(), lbl.getHeight(), Image.SCALE_SMOOTH);
            lbl.setText("");
            lbl.setIcon(new ImageIcon(scaled));
        } catch (Exception ex) {
            lbl.setIcon(null);
            lbl.setText("No image");
        }
    }

  
    
     


    
   
    
   public class InsertMovieListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String title = dashboardView.getMovieTitle().getText().trim();            
            String genre = dashboardView.getGenre().getText().trim();           
            String duration =  dashboardView.getDuration().getText().trim();           
            String Date =  dashboardView.getPublishedDate().getText().trim();
            File image =  dashboardView.getSelectedFile();




            
            if (title.isEmpty() || genre.isEmpty() || duration.isEmpty() || Date.isEmpty()) {
                JOptionPane.showMessageDialog(dashboardView, 
                        "Please fill in all the fields.", "Validation Error", 
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (image == null || !image.exists() || !image.isFile()) {
                JOptionPane.showMessageDialog(dashboardView, 
                        "Please select a valid image file.", "Validation Error", 
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                byte[] imageData = Files.readAllBytes(image.toPath());
             MoviesData moviesData = new MoviesData(title, genre, duration, Date, imageData);
                boolean result = adminDAO.insertMovies(moviesData);
                
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
   
   public class UpdateMoviesListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if (selectedMovieId == -1) {
                JOptionPane.showMessageDialog(dashboardView, 
                        "Please select a movie to update.", "Error", 
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String title = dashboardView.getMovieTitle().getText();            
            String genre = dashboardView.getGenre().getText();           
            String duration = dashboardView.getDuration().getText();           
            String date = dashboardView.getPublishedDate().getText();
            File image = dashboardView.getSelectedFile();
            
            if (title.isEmpty() || genre.isEmpty() || duration.isEmpty() || date.isEmpty()) {
                JOptionPane.showMessageDialog(dashboardView, 
                        "Please fill in all the fields.", "Validation Error", 
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                MoviesData moviesData;
                if (image != null && image.exists() && image.isFile()) {
                    byte[] imageData = Files.readAllBytes(image.toPath());
                    moviesData = new MoviesData(selectedMovieId, title, genre, duration, date, imageData);
                } else {
                    moviesData = new MoviesData(selectedMovieId, title, genre, duration, date, null);
                }
                
                boolean result = adminDAO.updateMovie(moviesData);
                
                if (result) {
                    JOptionPane.showMessageDialog(dashboardView, 
                            "Movie updated successfully!", "Success", 
                            JOptionPane.INFORMATION_MESSAGE);
                    clearFields();
                    loadMoviesToTable();
                    selectedMovieId = -1;
                } else {
                    JOptionPane.showMessageDialog(dashboardView, 
                            "Failed to update movie.", "Error", 
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException | SQLException ex) {
                JOptionPane.showMessageDialog(dashboardView, 
                        "Error: " + ex.getMessage(), "Error", 
                        JOptionPane.ERROR_MESSAGE);
            }
        }
       
   }
   
   public class DeleteMovieListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
         if (selectedMovieId == -1) {
                JOptionPane.showMessageDialog(dashboardView, 
                        "Please select a movie to delete.", "Error", 
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int confirm = JOptionPane.showConfirmDialog(dashboardView, 
                    "Are you sure you want to delete this movie?", "Confirm Delete", 
                    JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    boolean result = adminDAO.deleteMovie(selectedMovieId);
                    
                    if (result) {
                        JOptionPane.showMessageDialog(dashboardView, 
                                "Movie deleted successfully!", "Success", 
                                JOptionPane.INFORMATION_MESSAGE);
                        clearFields();
                        loadMoviesToTable();
                        selectedMovieId = -1;
                    } else {
                        JOptionPane.showMessageDialog(dashboardView, 
                                "Failed to delete movie.", "Error", 
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(dashboardView, 
                            "Error: " + ex.getMessage(), "Error", 
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        
        }
          
       
   }
   
//   Logout admin dashboardView
   public class LogoutMovieListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
        
         int choice = JOptionPane.showConfirmDialog(
                dashboardView,                       // parent component
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
         dashboardView.setSelectedFile(null);
    }
    
    
 public void open(){
        this.dashboardView.setVisible(true);
    }
    public void close(){
        this.dashboardView.dispose();
    }
    
    
}
