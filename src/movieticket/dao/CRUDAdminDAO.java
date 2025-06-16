/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package movieticket.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import movieticket.database.MySqlConnection;
import movieticket.model.MovieData;
import movieticket.model.MoviesData;

/**
 *
 * @author Hp
 */
public class CRUDAdminDAO {
    MySqlConnection mySql = new MySqlConnection();
    
     public boolean createMovieTable() {
        Connection conn = mySql.openConnection();
        String createMoviesTableSQL = "CREATE TABLE IF NOT EXISTS Movie ("
                + "movie_id INT AUTO_INCREMENT PRIMARY KEY, "               
                + "title VARCHAR(30) NOT NULL, "
                + "genre VARCHAR(50) NOT NULL, "
                + "duration VARCHAR(10) NOT NULL, "
                + "datee VARCHAR(20) NOT NULL,"
                
                + ")";
        
        try (PreparedStatement pstmt = conn.prepareStatement(createMoviesTableSQL)) {
            pstmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.err.println("Error creating movies table: " + ex.getMessage());
            return false;
        } finally {
            mySql.closeConnection(conn);
        }
    }
    
    public boolean insertMovies(MovieData movieData) throws SQLException{
         Connection conn = mySql.openConnection();
         createMovieTable();
         
          String query = "INSERT INTO Movie(title, genre, duration, datee) VALUES(?, ?, ?, ?)";
         
           try (PreparedStatement pstmt = conn.prepareStatement(query)) {
               System.out.println("here3 "+movieData.getTitle()+movieData.getGenre()+movieData.getDuration()+ movieData.getDate());
            pstmt.setString(1, movieData.getTitle());
            pstmt.setString(2, movieData.getGenre());
            pstmt.setString(3, movieData.getDuration());
            pstmt.setString(4, movieData.getDate());
//            pstmt.setBytes(5, moviesData.getPosterPath());
            
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException ex) {
            System.err.println("Error adding movie: " + ex.getMessage());
            throw ex;
        } finally {
            mySql.closeConnection(conn);
        }
         
         
    }
    
     public List<MoviesData> getAllMovies() throws SQLException {
        Connection conn = mySql.openConnection();
        List<MoviesData> movies = new ArrayList<>();
        String query = "SELECT movie_id, title, genre, duration, datee FROM Movie";
        
        try (PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                MoviesData movie = new MoviesData(
                    rs.getInt("movie_id"),
                    rs.getString("title"),
                    rs.getString("genre"),
                    rs.getString("duration"),
                    rs.getString("datee"),
                    null // We don't load the poster for the list view
                );
                movies.add(movie);
            }
        } catch (SQLException ex) {
            System.err.println("Error getting all movies: " + ex.getMessage());
            throw ex;
        } finally {
            mySql.closeConnection(conn);
        }
        return movies;
    }
     
     public MoviesData getMovieById(int movieId) throws SQLException {
        Connection conn = mySql.openConnection();
        String query = "SELECT * FROM Movie WHERE movie_id=?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, movieId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new MoviesData(
                            
                        rs.getInt("movie_id"),
                        rs.getString("title"),
                        rs.getString("genre"),
                        rs.getString("duration"),
                        rs.getString("datee"),
                        null
                       );
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error getting movie by ID: " + ex.getMessage());
            throw ex;
        } finally {
            mySql.closeConnection(conn);
        }
        return null;
    }
}
