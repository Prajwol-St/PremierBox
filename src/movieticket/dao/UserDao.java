/*  UserDao.java – full file with null-connection guards added            */
/*  NEW lines are marked  // ─── NEW ───                                   */
package movieticket.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import movieticket.database.MySqlConnection;
import movieticket.model.*;
import org.mindrot.jbcrypt.BCrypt;

public class UserDao {

    private final MySqlConnection mySql = new MySqlConnection();

    /* ------------------------------------------------ register -------- */
    public boolean registerUser(UserData userData) {

        Connection conn = mySql.openConnection();
        if (conn == null) return false;                             // ─── NEW ───

        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS demoUserss(
              id INT AUTO_INCREMENT PRIMARY KEY,
              name VARCHAR(50) NOT NULL,
              email VARCHAR(100) UNIQUE NOT NULL,
              password VARCHAR(255) NOT NULL,
              image BLOB NOT NULL,
              isAdmin BOOLEAN DEFAULT FALSE)""";

        String insertSQL = """
            INSERT INTO demoUserss(name,email,password,image,isAdmin)
            VALUES (?,?,?,?,?)""";

        try (Statement st = conn.createStatement();
             PreparedStatement ps = conn.prepareStatement(insertSQL)) {

            st.executeUpdate(createTableSQL);

            String hashed = BCrypt.hashpw(userData.getPassword(), BCrypt.gensalt());

            ps.setString(1, userData.getName());
            ps.setString(2, userData.getEmail());
            ps.setString(3, hashed);
            ps.setBytes (4, userData.getImage());
            ps.setBoolean(5, userData.isAdmin());

            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.err.println(ex);
            return false;
        } finally { mySql.closeConnection(conn); }
    }

    /* ------------------------------------------------ login ----------- */
    public UserData loginUser(LoginRequest req) {

        Connection conn = mySql.openConnection();
        if (conn == null) return null;                               // ─── NEW ───

        String sql = "SELECT * FROM demoUserss WHERE email=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, req.getEmail());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next() &&
                    BCrypt.checkpw(req.getPassword(), rs.getString("password"))) {

                    UserData u = new UserData(
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getBytes ("image"));
                    u.setId     (rs.getInt   ("id"));
                    u.setIsAdmin(rs.getBoolean("isAdmin"));
                    return u;
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally { mySql.closeConnection(conn); }
        return null;
    }

    /* ------------------------------------------------ check email ----- */
    public boolean checkEmail(String email) {

        Connection conn = mySql.openConnection();
        if (conn == null) return false;                              // ─── NEW ───

        String q = "SELECT 1 FROM demoUserss WHERE email=?";
        try (PreparedStatement ps = conn.prepareStatement(q)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {
            return false;
        } finally { mySql.closeConnection(conn); }
    }

    /* ------------------------------------------------ reset password -- */
    public boolean resetPassword(ResetPasswordRequest r) {

        Connection conn = mySql.openConnection();
        if (conn == null) return false;                              // ─── NEW ───

        String q = "UPDATE demoUserss SET password=? WHERE email=?";
        try (PreparedStatement ps = conn.prepareStatement(q)) {
            ps.setString(1, BCrypt.hashpw(r.getPassword(), BCrypt.gensalt()));
            ps.setString(2, r.getEmail());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            return false;
        } finally { mySql.closeConnection(conn); }
    }

    /* ------------------------------------------------ seat booked? ---- */
    public boolean isSeatBooked(int movieId, String seatNo) {

        Connection conn = mySql.openConnection();
        if (conn == null) return false;                              // ─── NEW ───

        String q = """
            SELECT COUNT(*) FROM seat_bookings
             WHERE movie_id=? AND FIND_IN_SET(?, seat_numbers)""";
        try (PreparedStatement ps = conn.prepareStatement(q)) {
            ps.setInt   (1, movieId);
            ps.setString(2, seatNo);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (SQLException ex) {
            System.err.println(ex);
            return false;
        } finally { mySql.closeConnection(conn); }
    }

    /* ------------------------------------------------ book seats ------ */
   /* ------------------------------------------------------------------ */
/*  Book seats – now returns generated id (-1 if failed or duplicate) */
/* ------------------------------------------------------------------ */
public int bookSeatsAndReturnId(SeatBooking booking) {               // ─── NEW ───

    String sql = "INSERT INTO seat_bookings(user_id,movie_id,seat_numbers) "
               + "VALUES (?,?,?)";
    try (Connection conn = mySql.openConnection();
         PreparedStatement ps = conn.prepareStatement(
                 sql, Statement.RETURN_GENERATED_KEYS)) {

        ps.setInt   (1, booking.getUserId());
        ps.setInt   (2, booking.getMovieId());
        ps.setString(3, booking.getSeatNumbers());
        ps.executeUpdate();

        try (ResultSet rs = ps.getGeneratedKeys()) {
            return rs.next() ? rs.getInt(1) : -1;
        }
    } catch (SQLException ex) {
        System.err.println(ex);
        return -1;
    }
}

/* wrapper kept so old calls compile */
public boolean bookSeats(SeatBooking b) {
    return bookSeatsAndReturnId(b) != -1;                            // ─── NEW ───
}


    /* ------------------------------------------------ payment --------- */
    public boolean makePayment(Payment p) {

        Connection conn = mySql.openConnection();
        if (conn == null) return false;                              // ─── NEW ───

        String q = """
            INSERT INTO payments(booking_id,amount,payment_method,
                                 card_number,card_holder_name,expiry_date,
                                 payment_status,transaction_id,payment_date)
            VALUES (?,?,?,?,?,?,?,?,?)""";
        try (PreparedStatement ps = conn.prepareStatement(q)) {
            ps.setInt   (1, p.getBookingId());
            ps.setDouble(2, p.getAmount());
            ps.setString(3, p.getPaymentMethod());
            ps.setString(4, p.getCardNumber());
            ps.setString(5, p.getCardHolderName());
            ps.setString(6, p.getExpiryDate());
            ps.setString(7, p.getPaymentStatus());
            ps.setString(8, p.getTransactionId());
            ps.setObject(9, p.getPaymentDate());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println(ex);
            return false;
        } finally { mySql.closeConnection(conn); }
    }
    
        /* ------------------------------------------------------------------ */
    /*  fetch unread notifications for dashboard                          */
    /* ------------------------------------------------------------------ */
    public List<String> getUnreadNotifications(int uid){                   // ─── NEW ───
        List<String> out = new ArrayList<>();
        String sql = "SELECT message FROM notifications "
                   + "WHERE user_id=? AND is_read=0 ORDER BY created DESC";
        try(Connection c = mySql.openConnection();
            PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1, uid);
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()) out.add(rs.getString(1));
            }
        }catch(SQLException ex){ System.err.println(ex); }
        return out;
    }

    
        // one-liner to drop a message in the DB
    public void insertNotification(int uid, String msg, String type){
        String q = "INSERT INTO notifications(user_id,message,type) VALUES (?,?,?)";
        try(Connection c = mySql.openConnection();
            PreparedStatement ps = c.prepareStatement(q)){
            ps.setInt   (1, uid);
            ps.setString(2, msg);
            ps.setString(3, type);
            ps.executeUpdate();
        }catch(SQLException ex){ System.err.println(ex); }
    }
    
    // UserDao.java  (add anywhere in the class)

    // ─── NEW : one-liner you can call from any controller
    public void addNotification(int uid, String msg) {
        String q = "INSERT INTO notifications(user_id,message) VALUES (?,?)";
        try (Connection c = mySql.openConnection();
             PreparedStatement ps = c.prepareStatement(q)) {
            ps.setInt(1, uid);
            ps.setString(2, msg);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("addNotification: " + ex.getMessage());
        }
    }
    
    // ─── NEW : mark one message as read ---------------------------------
        public void markNotificationRead(int uid, String msg) {
            String sql = "UPDATE notifications               "
                       + "SET    is_read = 1                "
                       + "WHERE  user_id = ? AND message = ?";

            try (Connection c = mySql.openConnection();
                 PreparedStatement ps = c.prepareStatement(sql)) {

                ps.setInt   (1, uid);
                ps.setString(2, msg);
                ps.executeUpdate();                               // no result needed
            } catch (SQLException ex) {
                System.err.println("markNotificationRead: " + ex.getMessage());
            }
        }
        
        
         public boolean updateUser(int userId, String name, String password) {
        String query;
        boolean updatePassword = password != null && !password.isEmpty();

        if (updatePassword) {
            query = "UPDATE demoUserss SET name = ?, password = ? WHERE id = ?";
        } else {
            query = "UPDATE demoUserss SET name = ? WHERE id = ?";
        }

        Connection conn = mySql.openConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            if (updatePassword) {
                String hashedPassword = org.mindrot.jbcrypt.BCrypt.hashpw(password, org.mindrot.jbcrypt.BCrypt.gensalt());
                pstmt.setString(2, hashedPassword);
                pstmt.setInt(3, userId);
            } else {
                pstmt.setInt(2, userId);
            }

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Update Exception: " + e);
            return false;
        } finally {
            mySql.closeConnection(conn);
        }
    }




}
