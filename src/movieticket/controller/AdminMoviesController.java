package movieticket.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import movieticket.dao.AdminDao;
import movieticket.model.MoviesData;
import movieticket.model.UserData;
import movieticket.view.AdminMovies;

public class AdminMoviesController {
    private final AdminDao adminDao = new AdminDao();
    AdminMovies adminMovies;
    UserData userData;
    private int selectedMovieId = -1;
    
    public AdminMoviesController(AdminMovies adminMovies) {
        this.adminMovies = adminMovies;
        this.userData = userData;
        
        adminMovies.importMovieListener(new ImportMovieListener());
        adminMovies.addMoviesListener(new AddMoviesListener());
        adminMovies.updateMoviesListener(new UpdateMoviesListener());
        adminMovies.deleteMovieListener(new DeleteMovieListener());
        
        // Load movies when controller initializes
        loadMoviesToTable();
        
        // Add table selection listener
        adminMovies.getMovieTable().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = adminMovies.getMovieTable().getSelectedRow();
                if (selectedRow >= 0) {
                    DefaultTableModel model = (DefaultTableModel) adminMovies.getMovieTable().getModel();
                    selectedMovieId = (int) model.getValueAt(selectedRow, 0);
                    try {
                        MoviesData movie = adminDao.getMovieById(selectedMovieId);
                        if (movie != null) {
                            adminMovies.getMovieTitleField().setText(movie.getTitle());
                            adminMovies.getGenreField().setText(movie.getGenre());
                            adminMovies.getDurationField().setText(movie.getDuration());
                            adminMovies.getDateField().setText(movie.getDate());
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(adminMovies, "Error loading movie details: " + ex.getMessage(), 
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }
    
    public void open() {
        this.adminMovies.setVisible(true);
    }
    
    public void close() {
        this.adminMovies.dispose();
    }
    
    private void loadMoviesToTable() {
        try {
            List<MoviesData> movies = adminDao.getAllMovies();
            DefaultTableModel model = (DefaultTableModel) adminMovies.getMovieTable().getModel();
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
            JOptionPane.showMessageDialog(adminMovies, "Error loading movies: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private class ImportMovieListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(adminMovies);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                if (file.exists() && file.isFile()) {
                    adminMovies.setSelectedFile(file);
                    JOptionPane.showMessageDialog(adminMovies, 
                            "Image selected successfully: " + file.getName(),
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(adminMovies,
                        "Invalid file selected.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    private class AddMoviesListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String title = adminMovies.getMovieTitleField().getText();            
            String genre = adminMovies.getGenreField().getText();           
            String duration = adminMovies.getDurationField().getText();           
            String date = adminMovies.getDateField().getText();
            File image = adminMovies.getSelectedFile();
            
            if (title.isEmpty() || genre.isEmpty() || duration.isEmpty() || date.isEmpty()) {
                JOptionPane.showMessageDialog(adminMovies, 
                        "Please fill in all the fields.", "Validation Error", 
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (image == null || !image.exists() || !image.isFile()) {
                JOptionPane.showMessageDialog(adminMovies, 
                        "Please select a valid image file.", "Validation Error", 
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                byte[] imageData = Files.readAllBytes(image.toPath());
                MoviesData moviesData = new MoviesData(title, genre, duration, date, imageData);
                boolean result = adminDao.addMovie(moviesData);
                
                if (result) {
                    JOptionPane.showMessageDialog(adminMovies, 
                            "Movie added successfully!", "Success", 
                            JOptionPane.INFORMATION_MESSAGE);
                    clearFields();
                    loadMoviesToTable();
                } else {
                    JOptionPane.showMessageDialog(adminMovies, 
                            "Failed to add movie.", "Error", 
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException | SQLException ex) {
                JOptionPane.showMessageDialog(adminMovies, 
                        "Error: " + ex.getMessage(), "Error", 
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private class UpdateMoviesListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (selectedMovieId == -1) {
                JOptionPane.showMessageDialog(adminMovies, 
                        "Please select a movie to update.", "Error", 
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String title = adminMovies.getMovieTitleField().getText();            
            String genre = adminMovies.getGenreField().getText();           
            String duration = adminMovies.getDurationField().getText();           
            String date = adminMovies.getDateField().getText();
            File image = adminMovies.getSelectedFile();
            
            if (title.isEmpty() || genre.isEmpty() || duration.isEmpty() || date.isEmpty()) {
                JOptionPane.showMessageDialog(adminMovies, 
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
                
                boolean result = adminDao.updateMovie(moviesData);
                
                if (result) {
                    JOptionPane.showMessageDialog(adminMovies, 
                            "Movie updated successfully!", "Success", 
                            JOptionPane.INFORMATION_MESSAGE);
                    clearFields();
                    loadMoviesToTable();
                    selectedMovieId = -1;
                } else {
                    JOptionPane.showMessageDialog(adminMovies, 
                            "Failed to update movie.", "Error", 
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException | SQLException ex) {
                JOptionPane.showMessageDialog(adminMovies, 
                        "Error: " + ex.getMessage(), "Error", 
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private class DeleteMovieListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (selectedMovieId == -1) {
                JOptionPane.showMessageDialog(adminMovies, 
                        "Please select a movie to delete.", "Error", 
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int confirm = JOptionPane.showConfirmDialog(adminMovies, 
                    "Are you sure you want to delete this movie?", "Confirm Delete", 
                    JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    boolean result = adminDao.deleteMovie(selectedMovieId);
                    
                    if (result) {
                        JOptionPane.showMessageDialog(adminMovies, 
                                "Movie deleted successfully!", "Success", 
                                JOptionPane.INFORMATION_MESSAGE);
                        clearFields();
                        loadMoviesToTable();
                        selectedMovieId = -1;
                    } else {
                        JOptionPane.showMessageDialog(adminMovies, 
                                "Failed to delete movie.", "Error", 
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(adminMovies, 
                            "Error: " + ex.getMessage(), "Error", 
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    private void clearFields() {
        adminMovies.getMovieTitleField().setText("");
        adminMovies.getGenreField().setText("");
        adminMovies.getDurationField().setText("");
        adminMovies.getDateField().setText("");
        adminMovies.setSelectedFile(null);
    }
}