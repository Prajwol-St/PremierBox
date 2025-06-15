package movieticket.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class PaymentValidator {
    
    // Credit card number validation using Luhn algorithm
    public static boolean isValidCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.isEmpty()) {
            return false;
        }
        
        // Remove spaces and non-digits
        cardNumber = cardNumber.replaceAll("\\D", "");
        
        // Check length (13-19 digits for most cards)
        if (cardNumber.length() < 13 || cardNumber.length() > 19) {
            return false;
        }
        
        // Luhn algorithm
        return luhnCheck(cardNumber);
    }
    
    private static boolean luhnCheck(String cardNumber) {
        int sum = 0;
        boolean alternate = false;
        
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(cardNumber.charAt(i));
            
            if (alternate) {
                digit *= 2;
                if (digit > 9) {
                    digit = (digit % 10) + 1;
                }
            }
            
            sum += digit;
            alternate = !alternate;
        }
        
        return (sum % 10) == 0;
    }
    
    // CVV validation
    public static boolean isValidCVV(String cvv) {
        if (cvv == null || cvv.isEmpty()) {
            return false;
        }
        
        // CVV should be 3 or 4 digits
        return Pattern.matches("\\d{3,4}", cvv);
    }
    
    // Expiry date validation
    public static boolean isValidExpiryDate(String expiryDate) {
        if (expiryDate == null || expiryDate.isEmpty()) {
            return false;
        }
        
        try {
            // Expected format: MM/YY
            if (!Pattern.matches("\\d{2}/\\d{2}", expiryDate)) {
                return false;
            }
            
            String[] parts = expiryDate.split("/");
            int month = Integer.parseInt(parts[0]);
            int year = 2000 + Integer.parseInt(parts[1]);
            
            // Validate month
            if (month < 1 || month > 12) {
                return false;
            }
            
            // Check if card is not expired
            LocalDate cardExpiry = LocalDate.of(year, month, 1).plusMonths(1).minusDays(1);
            LocalDate today = LocalDate.now();
            
            return !cardExpiry.isBefore(today);
            
        } catch (Exception e) {
            return false;
        }
    }
    
    // Card holder name validation
    public static boolean isValidCardHolderName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        
        // Name should contain only letters, spaces, hyphens, and apostrophes
        // Length should be between 2 and 50 characters
        String trimmedName = name.trim();
        return trimmedName.length() >= 2 && 
               trimmedName.length() <= 50 && 
               Pattern.matches("[a-zA-Z\\s\\-']+", trimmedName);
    }
    
    // Amount validation
    public static boolean isValidAmount(double amount) {
        return amount > 0 && amount <= 10000; // Max $10,000 per transaction
    }
    
    // Get card type from card number
    public static String getCardType(String cardNumber) {
        if (cardNumber == null || cardNumber.isEmpty()) {
            return "Unknown";
        }
        
        cardNumber = cardNumber.replaceAll("\\D", "");
        
        if (cardNumber.startsWith("4")) {
            return "Visa";
        } else if (cardNumber.startsWith("5") || 
                   (cardNumber.startsWith("2") && cardNumber.length() >= 4 && 
                    Integer.parseInt(cardNumber.substring(0, 4)) >= 2221 && 
                    Integer.parseInt(cardNumber.substring(0, 4)) <= 2720)) {
            return "Mastercard";
        } else if (cardNumber.startsWith("34") || cardNumber.startsWith("37")) {
            return "American Express";
        } else if (cardNumber.startsWith("6011") || cardNumber.startsWith("65") ||
                   (cardNumber.startsWith("644") || cardNumber.startsWith("645") ||
                    cardNumber.startsWith("646") || cardNumber.startsWith("647") ||
                    cardNumber.startsWith("648") || cardNumber.startsWith("649"))) {
            return "Discover";
        }
        
        return "Unknown";
    }
    
    // Comprehensive payment validation
    public static ValidationResult validatePayment(String cardNumber, String cardHolderName, 
                                                 String expiryDate, String cvv, double amount) {
        ValidationResult result = new ValidationResult();
        
        if (!isValidCardNumber(cardNumber)) {
            result.addError("Invalid card number");
        }
        
        if (!isValidCardHolderName(cardHolderName)) {
            result.addError("Invalid card holder name");
        }
        
        if (!isValidExpiryDate(expiryDate)) {
            result.addError("Invalid or expired card");
        }
        
        if (!isValidCVV(cvv)) {
            result.addError("Invalid CVV");
        }
        
        if (!isValidAmount(amount)) {
            result.addError("Invalid amount");
        }
        
        return result;
    }
    
    // Validation result class
    public static class ValidationResult {
        private StringBuilder errors = new StringBuilder();
        private boolean valid = true;
        
        public void addError(String error) {
            if (errors.length() > 0) {
                errors.append("\n");
            }
            errors.append("• ").append(error);
            valid = false;
        }
        
        public boolean isValid() {
            return valid;
        }
        
        public String getErrors() {
            return errors.toString();
        }
    }
}