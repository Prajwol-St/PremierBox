package movieticket.model;

import java.time.LocalDateTime;

public class Payment {
    private int paymentId;
    private int bookingId;
    private double amount;
    private String paymentMethod;
    private String cardNumber;
    private String cardHolderName;
    private String expiryDate;
    private String cvv;
    private String paymentStatus;
    private String transactionId;
    private LocalDateTime paymentDate;
    
    // Constructor for new payment
    public Payment(int bookingId, double amount, String paymentMethod, 
                   String cardNumber, String cardHolderName, String expiryDate, String cvv) {
        this.bookingId = bookingId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.cardNumber = maskCardNumber(cardNumber);
        this.cardHolderName = cardHolderName;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.paymentStatus = "PENDING";
        this.paymentDate = LocalDateTime.now();
    }
    
    // Constructor for existing payment
    public Payment(int paymentId, int bookingId, double amount, String paymentMethod,
                   String cardNumber, String cardHolderName, String expiryDate,
                   String paymentStatus, String transactionId, LocalDateTime paymentDate) {
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expiryDate = expiryDate;
        this.paymentStatus = paymentStatus;
        this.transactionId = transactionId;
        this.paymentDate = paymentDate;
    }
    
    // Mask card number for security (show only last 4 digits)
    private String maskCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 4) {
            return "****";
        }
        return "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
    }
    
    // Getters and Setters
    public int getPaymentId() { return paymentId; }
    public void setPaymentId(int paymentId) { this.paymentId = paymentId; }
    
    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }
    
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    
    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }
    
    public String getCardHolderName() { return cardHolderName; }
    public void setCardHolderName(String cardHolderName) { this.cardHolderName = cardHolderName; }
    
    public String getExpiryDate() { return expiryDate; }
    public void setExpiryDate(String expiryDate) { this.expiryDate = expiryDate; }
    
    public String getCvv() { return cvv; }
    public void setCvv(String cvv) { this.cvv = cvv; }
    
    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
    
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    
    public LocalDateTime getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDateTime paymentDate) { this.paymentDate = paymentDate; }
}