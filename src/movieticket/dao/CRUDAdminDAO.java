/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package movieticket.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import movieticket.database.MySqlConnection;
import movieticket.model.MovieData;

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
}
