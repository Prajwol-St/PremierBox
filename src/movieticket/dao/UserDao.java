package movieticket.dao;

import movieticket.model.Movie;
import movieticket.model.LoginRequest;
import movieticket.model.ResetPasswordRequest;
import movieticket.model.UserData;
import movieticket.model.SeatBooking;
import movieticket.database.MySqlConnection;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserDao {

    MySqlConnection mySql = new MySqlConnection();

    // --- User-related methods (existing code) ---

    public boolean registerUser(UserData userData) {
        Connection conn = mySql.openConnection();
        String createTableSQL = "CREATE TABLE IF NOT EXISTS demoUserss ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "name VARCHAR(50) NOT NULL, "
                + "email VARCHAR(100) UNIQUE NOT NULL, "
                + "password VARCHAR(255) NOT NULL, "
                + "image BLOB NOT NULL, "
                + "isAdmin BOOLEAN DEFAULT FALSE"
                + ")";
        String query = "INSERT INTO demoUserss (name, email, password, image, isAdmin) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement createtbl = conn.prepareStatement(createTableSQL);
            createtbl.executeUpdate();
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                String hashedPassword = BCrypt.hashpw(userData.getPassword(), BCrypt.gensalt());
                pstmt.setString(1, userData.getName());
                pstmt.setString(2, userData.getEmail());
                pstmt.setString(3, hashedPassword);
                pstmt.setBytes(4, userData.getImage());
                pstmt.setBoolean(5, userData.isAdmin());
                int result = pstmt.executeUpdate();
                return result > 0;
            }
        } catch (SQLException ex) {
            System.err.println(ex);
            return false;
        } finally {
            mySql.closeConnection(conn);
        }
    }

    public UserData loginUser(LoginRequest loginData) {
        Connection conn = mySql.openConnection();
        String sql = "SELECT * FROM demoUserss where email = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, loginData.getEmail());
            ResultSet result = pstmt.executeQuery();
            if (result.next()) {
                String storedHashedPassword = result.getString("password");
                String enteredPassword = loginData.getPassword();
                if (BCrypt.checkpw(enteredPassword, storedHashedPassword)) {
                    UserData user = new UserData(
                            result.getString("name"),
                            result.getString("email"),
                            storedHashedPassword,
                            result.getBytes("image")
                    );
                    user.setId(result.getInt("id"));
                    user.setIsAdmin(result.getBoolean("isAdmin"));
                    return user;
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            mySql.closeConnection(conn);
        }
        return null;
    }

    public boolean checkEmail(String email) {
        String query = "SELECT * FROM demoUserss where email = ?";
        Connection conn = mySql.openConnection();
        try {
            PreparedStatement stmnt = conn.prepareStatement(query);
            stmnt.setString(1, email);
            ResultSet result = stmnt.executeQuery();
            return result.next();
        } catch (Exception e) {
            return false;
        } finally {
            mySql.closeConnection(conn);
        }
    }

    public boolean resetPassword(ResetPasswordRequest resetReq) {
        String query = "UPDATE demoUserss SET password = ? WHERE email=?";
        Connection conn = mySql.openConnection();
        try {
            PreparedStatement stmnt = conn.prepareStatement(query);
            String hashedPassword = BCrypt.hashpw(resetReq.getPassword(), BCrypt.gensalt());
            stmnt.setString(1, hashedPassword);
            stmnt.setString(2, resetReq.getEmail());
            int result = stmnt.executeUpdate();
            System.out.println("RESULT::" + result);
            return result > 0;
        } catch (Exception e) {
            System.out.println("EXCEPTION::" + e);
            return false;
        } finally {
            mySql.closeConnection(conn);
        }
    }

    public boolean bookSeats(SeatBooking booking) {
        Connection conn = mySql.openConnection();
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Bookings ("
                + "booking_id INT AUTO_INCREMENT PRIMARY KEY, "
                + "user_id INT NOT NULL, "
                + "movie_id INT NOT NULL, "
                + "seat_numbers VARCHAR(100) NOT NULL, "
                + "booking_time DATETIME DEFAULT CURRENT_TIMESTAMP"
                + ")";
        String query = "INSERT INTO Bookings (user_id, movie_id, seat_numbers) VALUES (?, ?, ?)";
        try {
            PreparedStatement createtbl = conn.prepareStatement(createTableSQL);
            createtbl.executeUpdate();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, booking.getUserId());
            pstmt.setInt(2, booking.getMovieId());
            pstmt.setString(3, booking.getSeatNumbers());
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException ex) {
            System.err.println("Error booking seats: " + ex.getMessage());
            return false;
        } finally {
            mySql.closeConnection(conn);
        }
    }

    public boolean isSeatBooked(int movieId, String seat) {
        Connection conn = mySql.openConnection();
        boolean booked = false;
        try {
            String query = "SELECT * FROM Bookings WHERE movie_id = ? AND FIND_IN_SET(?, seat_numbers)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, movieId);
            ps.setString(2, seat);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                booked = true;
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            mySql.closeConnection(conn);
        }
        return booked;
    }

   public List<Movie> getAllMovies() {
    List<Movie> movies = new ArrayList<>();
    Connection conn = mySql.openConnection();
    String query = "SELECT movie_id, title, genre, duration, datee, poster FROM Movies";
    try {
        PreparedStatement pstmt = conn.prepareStatement(query);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Movie movie = new Movie(
                rs.getInt("movie_id"),
                rs.getString("title"),
                rs.getString("genre"),
                rs.getString("duration"),
                rs.getString("datee"),
                rs.getBytes("poster")
            );
            movies.add(movie);
        }
    } catch (SQLException ex) {
        System.err.println("Error loading movies: " + ex.getMessage());
    } finally {
        mySql.closeConnection(conn);
    }
    return movies;
}
}


   