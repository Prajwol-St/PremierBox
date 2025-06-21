/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package movieticket.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import movieticket.database.MySqlConnection;
import movieticket.model.LoginRequest;
import movieticket.model.Payment;
import movieticket.model.ResetPasswordRequest;
import movieticket.model.SeatBooking;
import movieticket.model.UserData;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Hp
 */
public class UserDao {
    
     MySqlConnection mySql = new MySqlConnection();
    public boolean registerUser(UserData userData){
        Connection conn= mySql.openConnection();
         String createTableSQL = "CREATE TABLE IF NOT EXISTS demoUserss ("
            + "id INT AUTO_INCREMENT PRIMARY KEY, "               
            + "name VARCHAR(50) NOT NULL, "
            + "email VARCHAR(100) UNIQUE NOT NULL, "
            + "password VARCHAR(255) NOT NULL, "
            + "image BLOB NOT NULL, "
            + "isAdmin BOOLEAN DEFAULT FALSE"
            + ")";
        String query=  "INSERT INTO demoUserss (name, email, password,image, isAdmin) VALUES (?, ?, ?, ?, ?)";
         
        try {
            PreparedStatement createtbl= conn.prepareStatement(createTableSQL);
            createtbl.executeUpdate();
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(UserDao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            String hashedPassword = BCrypt.hashpw(userData.getPassword(), BCrypt.gensalt());
            
            
            pstmt.setString(1, userData.getName());
            pstmt.setString(2, userData.getEmail());
            pstmt.setString(3, hashedPassword);
            pstmt.setBytes(4, userData.getImage());
            pstmt.setBoolean(5, userData.isAdmin());
            
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException ex) {
            System.err.println(ex);

        } finally {
            mySql.closeConnection(conn);
        }
          return false;
    }
    
    public UserData loginUser(LoginRequest loginData){
        Connection conn = mySql.openConnection();
        String sql = "SELECT * FROM demoUserss where email = ?";
        
        
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, loginData.getEmail());
            ResultSet result = pstmt.executeQuery();
            
            if (result.next()) {
                String storedHashedPassword = result.getString("password");
                String enteredPassword = loginData.getPassword();
            
            if (BCrypt.checkpw(enteredPassword, storedHashedPassword)) {
                    // Password matches
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
    
    public boolean checkEmail(String email){
        String query = "SELECT * FROM demoUserss where email = ?";
        Connection conn = mySql.openConnection();
        try{
            PreparedStatement stmnt = conn.prepareStatement(query);
            stmnt.setString(1, email);
            ResultSet result = stmnt.executeQuery();
            if(result.next()){
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            return false;
        } finally{
            mySql.closeConnection(conn);
        }
    }
    
    public boolean resetPassword(ResetPasswordRequest resetReq){
//        step1 : write a query in string
        String query = "UPDATE demoUserss SET password = ? WHERE email=?";
//         open connection
        Connection conn = mySql.openConnection();
        try{
//           step 3: prepare statement
            PreparedStatement stmnt = conn.prepareStatement(query);
//            step 4: use setstring to prepare query if needed
            stmnt.setString(1,resetReq.getPassword());
            stmnt.setString(2,resetReq.getEmail());
//            step 5: executequery or executeupdate
            int result = stmnt.executeUpdate();
            System.out.println("RESULT::"+result);
            return result>0;
        }catch (Exception e){
            System.out.println("EXCEPTION::"+e);
            return false;
        } finally{
            mySql.closeConnection(conn);
        }
    }
    // Check if a seat is already booked for a given movie
public boolean isSeatBooked(int movieId, String seatNumber) {
    String query = "SELECT COUNT(*) FROM seat_bookings WHERE movie_id = ? AND FIND_IN_SET(?, seat_numbers)";
    Connection conn = mySql.openConnection();
    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setInt(1, movieId);
        pstmt.setString(2, seatNumber);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
    } catch (SQLException ex) {
        System.err.println(ex);
    } finally {
        mySql.closeConnection(conn);
    }
    return false;
}

// Book seats for a user
public boolean bookSeats(SeatBooking booking) {
    String query = "INSERT INTO seat_bookings (user_id, movie_id, seat_numbers) VALUES (?, ?, ?)";
    Connection conn = mySql.openConnection();
    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setInt(1, booking.getUserId());
        pstmt.setInt(2, booking.getMovieId());
        pstmt.setString(3, booking.getSeatNumbers());
        int result = pstmt.executeUpdate();
        return result > 0;
    } catch (SQLException ex) {
        System.err.println(ex);
    } finally {
        mySql.closeConnection(conn);
    }
    return false;
}
public boolean makePayment(Payment payment) {
    String query = "INSERT INTO payments (booking_id, amount, payment_method, card_number, card_holder_name, expiry_date, payment_status, transaction_id, payment_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    Connection conn = mySql.openConnection();
    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setInt(1, payment.getBookingId());
        pstmt.setDouble(2, payment.getAmount());
        pstmt.setString(3, payment.getPaymentMethod());
        pstmt.setString(4, payment.getCardNumber());
        pstmt.setString(5, payment.getCardHolderName());
        pstmt.setString(6, payment.getExpiryDate());
        pstmt.setString(7, payment.getPaymentStatus());
        pstmt.setString(8, payment.getTransactionId());
        pstmt.setObject(9, payment.getPaymentDate());
        int result = pstmt.executeUpdate();
        return result > 0;
    } catch (SQLException ex) {
        System.err.println(ex);
    } finally {
        mySql.closeConnection(conn);
    }
    return false;
}


}
