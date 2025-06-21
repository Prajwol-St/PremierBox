/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package movieticket.dao;

import java.sql.Statement;   
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import movieticket.database.MySqlConnection;
 

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
         
           try (PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
//               System.out.println("here3 "+movieData.getTitle()+movieData.getGenre()+movieData.getDuration()+ movieData.getDate());
            pstmt.setString(1, moviesData.getTitle());
            pstmt.setString(2, moviesData.getGenre());
            pstmt.setString(3, moviesData.getDuration());
            pstmt.setString(4, moviesData.getDate());
            pstmt.setBytes(5, moviesData.getPosterPath());
            
            pstmt.executeUpdate();                      // run INSERT

          ResultSet rs = pstmt.getGeneratedKeys();    // fetch AUTO_INCREMENT
          if (rs.next()) {
              int movieId = rs.getInt(1);             // ← PK value

              insertTimesBatch(conn, movieId,         // ← HOOK #1
                         moviesData.getShowTimes());

          return true;                            // everything OK
          }
          return false;                               // safety net
         
         
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
            /* ────────── show-time refresh ───────────────────────────── */
         try (PreparedStatement del =
         conn.prepareStatement("DELETE FROM ShowTime WHERE movie_id=?")) {
            del.setInt(1, moviesData.getMovie_id());
         del.executeUpdate();                       // remove old times
}

insertTimesBatch(conn,                         // re-insert new list
                 moviesData.getMovie_id(),
                 moviesData.getShowTimes());
/* ───────────────────────────────────────────────────────────*/

            return result > 0;
        } catch (SQLException ex) {
            System.err.println("Error updating movie: " + ex.getMessage());
            throw ex;
        } finally {
            mySql.closeConnection(conn);
        }
    }
     
    public boolean deleteMovie(int movieId) throws SQLException {
        Connection conn = mySql.openConnection();
        String query = "DELETE FROM Movies WHERE movie_id=?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, movieId);
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException ex) {
            System.err.println("Error deleting movie: " + ex.getMessage());
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
     
     
      //to fetch data in the User MOvie as card interface
    public List<MoviesData> getAllMoviesWithImages() throws SQLException {
    Connection conn = mySql.openConnection();
    List<MoviesData> movies = new ArrayList<>();

    String query = "SELECT * FROM Movies";

    try (PreparedStatement pstmt = conn.prepareStatement(query);
         ResultSet rs = pstmt.executeQuery()) {

        while (rs.next()) {
            MoviesData movie = new MoviesData(
                rs.getInt("movie_id"),
                rs.getString("title"),
                rs.getString("genre"),
                rs.getString("duration"),
                rs.getString("datee"),
                rs.getBytes("poster")  // Include poster for image
            );
            
             /* ---------- add this single line ---------- */
            movie.setShowTimes(getTimes(movie.getMovie_id()));
            movies.add(movie);
        }
    } catch (SQLException ex) {
        System.err.println("Error getting all movies with images: " + ex.getMessage());
        throw ex;
    } finally {
        mySql.closeConnection(conn);
    }

    return movies;
}
    
    /* ------------------------------------------------------------------ */
/*  SHOW-TIME SUPPORT –– ADD THESE THREE MEMBERS                      */
/* ------------------------------------------------------------------ */

/* create table once (idempotent) */
private void createShowTimeTable() throws SQLException {          // ADD
    String sql = """
        CREATE TABLE IF NOT EXISTS ShowTime(
          show_id   INT AUTO_INCREMENT PRIMARY KEY,
          movie_id  INT NOT NULL,
          show_time TIME NOT NULL,
          FOREIGN KEY (movie_id) REFERENCES Movies(movie_id)
                   ON DELETE CASCADE
        )""";
    try (Connection c = mySql.openConnection();
         PreparedStatement ps = c.prepareStatement(sql)) {
        ps.executeUpdate();
    }
}

/* batch insert after movie insert/update */
private void insertTimesBatch(Connection c,
                              int movieId,
                              java.util.List<String> times)        // ADD
        throws SQLException {

    if (times == null || times.isEmpty()) return;
    createShowTimeTable();                             // ensure table

    String sql = "INSERT INTO ShowTime(movie_id, show_time) VALUES (?,?)";
    try (PreparedStatement ps = c.prepareStatement(sql)) {
        for (String t : times) {
            ps.setInt(1, movieId);
            ps.setString(2, t.trim());
            ps.addBatch();
        }
        ps.executeBatch();
    }
}

/* fetch list when you need to show it */
public java.util.List<String> getTimes(int movieId) throws SQLException { // ADD
    java.util.List<String> out = new java.util.ArrayList<>();
    String sql = "SELECT show_time FROM ShowTime WHERE movie_id=?";
    try (Connection c = mySql.openConnection();
         PreparedStatement ps = c.prepareStatement(sql)) {
        ps.setInt(1, movieId);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) out.add(rs.getString(1));
        }
    }
    return out;
}

}
