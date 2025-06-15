package movieticket.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;
import movieticket.database.MySqlConnection;
import movieticket.model.Payment;

public class PaymentDao {
    private final MySqlConnection mySql = new MySqlConnection();
    
    public boolean createPaymentTable() {
        Connection conn = mySql.openConnection();
        String createPaymentTableSQL = "CREATE TABLE IF NOT EXISTS Payments ("
                + "payment_id INT AUTO_INCREMENT PRIMARY KEY, "
                + "booking_id INT NOT NULL, "
                + "amount DECIMAL(10,2) NOT NULL, "
                + "payment_method VARCHAR(50) NOT NULL, "
                + "card_number VARCHAR(20), "
                + "card_holder_name VARCHAR(100), "
                + "expiry_date VARCHAR(7), "
                + "payment_status VARCHAR(20) DEFAULT 'PENDING', "
                + "transaction_id VARCHAR(100), "
                + "payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
                + "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
                + "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP"
                + ")";
        
        try (PreparedStatement pstmt = conn.prepareStatement(createPaymentTableSQL)) {
            pstmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.err.println("Error creating payments table: " + ex.getMessage());
            return false;
        } finally {
            mySql.closeConnection(conn);
        }
    }
    
    public boolean processPayment(Payment payment) throws SQLException {
        Connection conn = mySql.openConnection();
        createPaymentTable(); // Ensure table exists
        
        String query = "INSERT INTO Payments(booking_id, amount, payment_method, card_number, " +
                      "card_holder_name, expiry_date, payment_status, transaction_id, payment_date) " +
                      "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try {
            // Start transaction
            conn.setAutoCommit(false);
            
            // Simulate payment processing
            String transactionId = generateTransactionId();
            String paymentStatus = simulatePaymentGateway(payment);
            
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, payment.getBookingId());
                pstmt.setDouble(2, payment.getAmount());
                pstmt.setString(3, payment.getPaymentMethod());
                pstmt.setString(4, payment.getCardNumber());
                pstmt.setString(5, payment.getCardHolderName());
                pstmt.setString(6, payment.getExpiryDate());
                pstmt.setString(7, paymentStatus);
                pstmt.setString(8, transactionId);
                pstmt.setTimestamp(9, Timestamp.valueOf(payment.getPaymentDate()));
                
                int result = pstmt.executeUpdate();
                
                if (result > 0 && "SUCCESS".equals(paymentStatus)) {
                    // Update payment object with transaction details
                    payment.setTransactionId(transactionId);
                    payment.setPaymentStatus(paymentStatus);
                    
                    conn.commit();
                    return true;
                } else {
                    conn.rollback();
                    return false;
                }
            }
        } catch (SQLException ex) {
            conn.rollback();
            System.err.println("Error processing payment: " + ex.getMessage());
            throw ex;
        } finally {
            conn.setAutoCommit(true);
            mySql.closeConnection(conn);
        }
    }
    
    public Payment getPaymentByBookingId(int bookingId) throws SQLException {
        Connection conn = mySql.openConnection();
        String query = "SELECT * FROM Payments WHERE booking_id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, bookingId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Payment(
                        rs.getInt("payment_id"),
                        rs.getInt("booking_id"),
                        rs.getDouble("amount"),
                        rs.getString("payment_method"),
                        rs.getString("card_number"),
                        rs.getString("card_holder_name"),
                        rs.getString("expiry_date"),
                        rs.getString("payment_status"),
                        rs.getString("transaction_id"),
                        rs.getTimestamp("payment_date").toLocalDateTime()
                    );
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error getting payment by booking ID: " + ex.getMessage());
            throw ex;
        } finally {
            mySql.closeConnection(conn);
        }
        return null;
    }
    
    public boolean updatePaymentStatus(String transactionId, String status) throws SQLException {
        Connection conn = mySql.openConnection();
        String query = "UPDATE Payments SET payment_status = ?, updated_at = CURRENT_TIMESTAMP WHERE transaction_id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, status);
            pstmt.setString(2, transactionId);
            
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException ex) {
            System.err.println("Error updating payment status: " + ex.getMessage());
            throw ex;
        } finally {
            mySql.closeConnection(conn);
        }
    }
    
    private String generateTransactionId() {
        return "TXN_" + UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
    }
    
    private String simulatePaymentGateway(Payment payment) {
        // Simulate payment processing with 95% success rate
        // In real implementation, this would call actual payment gateway
        
        // Basic validation
        if (payment.getCardNumber() == null || payment.getCardNumber().length() < 16) {
            return "FAILED";
        }
        
        if (payment.getCvv() == null || payment.getCvv().length() != 3) {
            return "FAILED";
        }
        
        if (payment.getAmount() <= 0) {
            return "FAILED";
        }
        
        // Simulate network delay
        try {
            Thread.sleep(2000); // 2 second delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // 95% success rate simulation
        return Math.random() < 0.95 ? "SUCCESS" : "FAILED";
    }
}