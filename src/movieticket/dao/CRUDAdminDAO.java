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
    
      public boolean createMoviesTable() {
        Connection conn = mySql.openConnection();
        String createMoviesTableSQL = "CREATE TABLE IF NOT EXISTS Movies ("
                + "movie_id INT AUTO_INCREMENT PRIMARY KEY, "               
                + "title VARCHAR(30) NOT NULL, "
                + "genre VARCHAR(50) NOT NULL, "
                + "duration VARCHAR(10) NOT NULL, "
                + "datee VARCHAR(20) NOT NULL,"
                + "poster LONGBLOB"
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
    
    public boolean insertMovies(MoviesData moviesData) throws SQLException{
         Connection conn = mySql.openConnection();
         createMoviesTable();
         
          String query = "INSERT INTO Movies(title, genre, duration, datee, poster) VALUES(?, ?, ?, ?, ?)";
         
           try (PreparedStatement pstmt = conn.prepareStatement(query)) {
//               System.out.println("here3 "+movieData.getTitle()+movieData.getGenre()+movieData.getDuration()+ movieData.getDate());
            pstmt.setString(1, moviesData.getTitle());
            pstmt.setString(2, moviesData.getGenre());
            pstmt.setString(3, moviesData.getDuration());
            pstmt.setString(4, moviesData.getDate());
            pstmt.setBytes(5, moviesData.getPosterPath());
            
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException ex) {
            System.err.println("Error adding movie: " + ex.getMessage());
            throw ex;
        } finally {
            mySql.closeConnection(conn);
        }
         
         
    }
    
    
     public boolean updateMovie(MoviesData moviesData) throws SQLException {
        Connection conn = mySql.openConnection();
        
        String query;
        if (moviesData.getPosterPath() != null) {
            query = "UPDATE Movies SET title=?, genre=?, duration=?, datee=?, poster=? WHERE movie_id=?";
        } else {
            query = "UPDATE Movies SET title=?, genre=?, duration=?, datee=? WHERE movie_id=?";
        }
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, moviesData.getTitle());
            pstmt.setString(2, moviesData.getGenre());
            pstmt.setString(3, moviesData.getDuration());
            pstmt.setString(4, moviesData.getDate());
            
            if (moviesData.getPosterPath() != null) {
                pstmt.setBytes(5, moviesData.getPosterPath());
                pstmt.setInt(6, moviesData.getMovie_id());
            } else {
                pstmt.setInt(5, moviesData.getMovie_id());
            }
            
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException ex) {
            System.err.println("Error updating movie: " + ex.getMessage());
            throw ex;
        } finally {
            mySql.closeConnection(conn);
        }
    }
     
     
     public List<MoviesData> getAllMovies() throws SQLException {
        Connection conn = mySql.openConnection();
        List<MoviesData> movies = new ArrayList<>();
        String query = "SELECT movie_id, title, genre, duration, datee FROM Movies";
        
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
        String query = "SELECT * FROM Movies WHERE movie_id=?";
        
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
                        rs.getBytes("poster")
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
