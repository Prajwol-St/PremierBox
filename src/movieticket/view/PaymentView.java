package movieticket.view;
import movieticket.view.PaymentView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class PaymentView extends JFrame {
    
    // Payment form components
    private JLabel titleLabel;
    private JLabel amountLabel;
    private JLabel paymentMethodLabel;
    private JRadioButton creditCardRadio;
    private JRadioButton debitCardRadio;
    private JRadioButton paypalRadio;
    private ButtonGroup paymentMethodGroup;
    
    // Card details components
    private JLabel cardNumberLabel;
    private JTextField cardNumberField;
    private JLabel cardHolderLabel;
    private JTextField cardHolderField;
    private JLabel expiryLabel;
    private JComboBox<String> expiryMonthCombo;
    private JComboBox<String> expiryYearCombo;
    private JLabel cvvLabel;
    private JPasswordField cvvField;
    private javax.swing.JButton paymentButton;

    // Action buttons
    private JButton payNowButton;
    private JButton cancelButton;
    
    // Amount display
    private double totalAmount;
    private JLabel totalAmountLabel;
    
    public PaymentView(double amount) {
        this.totalAmount = amount;
        initComponents();
        setupLayout();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Secure Payment - Movie Ticket Booking");
        setSize(500, 600);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    private void paymentButtonActionPerformed(java.awt.event.ActionEvent evt) {
        double amount = 0.0; // Replace with the actual amount you want to pass
        PaymentView paymentView = new PaymentView(amount);
        paymentView.setVisible(true);
}


    
    private void initComponents() {
          paymentButton = new javax.swing.JButton();
        paymentButton.setBackground(new java.awt.Color(0, 0, 0));
        paymentButton.setFont(new java.awt.Font("Helvetica Neue", 0, 16));
        paymentButton.setForeground(new java.awt.Color(255, 255, 255));
        paymentButton.setText("Payment");
        paymentButton.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(25, 25, 112), 1, true));
        paymentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paymentButtonActionPerformed(evt);
            }
        });
        // Title
        titleLabel = new JLabel("Secure Payment", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(25, 25, 112));
        
        // Amount display
        DecimalFormat df = new DecimalFormat("#,##0.00");
        totalAmountLabel = new JLabel("Total Amount: $" + df.format(totalAmount), SwingConstants.CENTER);
        totalAmountLabel.setFont(new Font("Arial", Font.BOLD, 18));
        totalAmountLabel.setForeground(new Color(0, 128, 0));
        
        // Payment method selection
        paymentMethodLabel = new JLabel("Payment Method:");
        paymentMethodLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        creditCardRadio = new JRadioButton("Credit Card", true);
        debitCardRadio = new JRadioButton("Debit Card");
        paypalRadio = new JRadioButton("PayPal");
        
        paymentMethodGroup = new ButtonGroup();
        paymentMethodGroup.add(creditCardRadio);
        paymentMethodGroup.add(debitCardRadio);
        paymentMethodGroup.add(paypalRadio);
        
        // Card details
        cardNumberLabel = new JLabel("Card Number:");
        cardNumberField = new JTextField(20);
        cardNumberField.setToolTipText("Enter 16-digit card number");
        
        cardHolderLabel = new JLabel("Card Holder Name:");
        cardHolderField = new JTextField(20);
        
        expiryLabel = new JLabel("Expiry Date:");
        
        // Month combo box
        String[] months = {"01", "02", "03", "04", "05", "06", 
                          "07", "08", "09", "10", "11", "12"};
        expiryMonthCombo = new JComboBox<>(months);
        
        // Year combo box
        String[] years = new String[10];
        int currentYear = java.time.Year.now().getValue();
        for (int i = 0; i < 10; i++) {
            years[i] = String.valueOf(currentYear + i);
        }
        expiryYearCombo = new JComboBox<>(years);
        
        cvvLabel = new JLabel("CVV:");
        cvvField = new JPasswordField(4);
        cvvField.setToolTipText("3-digit security code");
        
        // Buttons
        payNowButton = new JButton("Pay Now");
        payNowButton.setBackground(new Color(0, 128, 0));
        payNowButton.setForeground(Color.WHITE);
        payNowButton.setFont(new Font("Arial", Font.BOLD, 16));
        payNowButton.setPreferredSize(new Dimension(120, 40));
        
        cancelButton = new JButton("Cancel");
        cancelButton.setBackground(new Color(220, 20, 60));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFont(new Font("Arial", Font.BOLD, 16));
        cancelButton.setPreferredSize(new Dimension(120, 40));
        
        // Add input validation listeners
        addInputValidation();
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Main panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Title
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(titleLabel, gbc);
        
        // Amount
        gbc.gridy = 1;
        mainPanel.add(totalAmountLabel, gbc);
        
        // Payment method section
        gbc.gridy = 2; gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(paymentMethodLabel, gbc);
        
        gbc.gridy = 3;
        mainPanel.add(creditCardRadio, gbc);
        gbc.gridx = 1;
        mainPanel.add(debitCardRadio, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4;
        mainPanel.add(paypalRadio, gbc);
        
        // Card details section
        JPanel cardPanel = createCardDetailsPanel();
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(cardPanel, gbc);
        
        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(payNowButton);
        buttonPanel.add(cancelButton);
        
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(buttonPanel, gbc);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Security notice
        JLabel securityLabel = new JLabel("🔒 Your payment information is secure and encrypted", SwingConstants.CENTER);
        securityLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        securityLabel.setForeground(new Color(100, 100, 100));
        securityLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(securityLabel, BorderLayout.SOUTH);
    }
    
    private JPanel createCardDetailsPanel() {
        JPanel cardPanel = new JPanel(new GridBagLayout());
        cardPanel.setBackground(new Color(248, 248, 248));
        cardPanel.setBorder(BorderFactory.createTitledBorder("Card Details"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Card number
        gbc.gridx = 0; gbc.gridy = 0;
        cardPanel.add(cardNumberLabel, gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        cardPanel.add(cardNumberField, gbc);
        
        // Card holder
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE;
        cardPanel.add(cardHolderLabel, gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        cardPanel.add(cardHolderField, gbc);
        
        // Expiry date
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE;
        cardPanel.add(expiryLabel, gbc);
        
        JPanel expiryPanel = new JPanel();
        expiryPanel.setBackground(new Color(248, 248, 248));
        expiryPanel.add(expiryMonthCombo);
        expiryPanel.add(new JLabel("/"));
        expiryPanel.add(expiryYearCombo);
        
        gbc.gridx = 1;
        cardPanel.add(expiryPanel, gbc);
        
        // CVV
        gbc.gridx = 0; gbc.gridy = 3;
        cardPanel.add(cvvLabel, gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.NONE;
        cardPanel.add(cvvField, gbc);
        
        return cardPanel;
    }
    
    private void addInputValidation() {
        // Card number formatting (add spaces every 4 digits)
        cardNumberField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                String text = cardNumberField.getText().replaceAll("\\s", "");
                if (text.length() > 16) {
                    text = text.substring(0, 16);
                }
                
                StringBuilder formatted = new StringBuilder();
                for (int i = 0; i < text.length(); i++) {
                    if (i > 0 && i % 4 == 0) {
                        formatted.append(" ");
                    }
                    formatted.append(text.charAt(i));
                }
                
                cardNumberField.setText(formatted.toString());
                cardNumberField.setCaretPosition(formatted.length());
            }
        });
        
        // CVV validation (only numbers, max 3 digits)
        cvvField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                if (!Character.isDigit(c) || cvvField.getPassword().length >= 3) {
                    evt.consume();
                }
            }
        });
    }
    
    // Getters for form data
    public String getSelectedPaymentMethod() {
        if (creditCardRadio.isSelected()) return "Credit Card";
        if (debitCardRadio.isSelected()) return "Debit Card";
        if (paypalRadio.isSelected()) return "PayPal";
        return "Credit Card";
    }
    
    public String getCardNumber() {
        return cardNumberField.getText().replaceAll("\\s", "");
    }
    
    public String getCardHolderName() {
        return cardHolderField.getText().trim();
    }
    
    public String getExpiryDate() {
        return expiryMonthCombo.getSelectedItem() + "/" + 
               ((String)expiryYearCombo.getSelectedItem()).substring(2);
    }
    
    public String getCvv() {
        return new String(cvvField.getPassword());
    }
    
    public double getTotalAmount() {
        return totalAmount;
    }
    
    // Event listeners
    public void addPayNowListener(ActionListener listener) {
        payNowButton.addActionListener(listener);
    }
    
    public void addCancelListener(ActionListener listener) {
        cancelButton.addActionListener(listener);
    }
    
    // Validation methods
    public boolean validatePaymentForm() {
        if (getCardNumber().length() != 16) {
            showError("Please enter a valid 16-digit card number");
            return false;
        }
        
        if (getCardHolderName().isEmpty()) {
            showError("Please enter card holder name");
            return false;
        }
        
        if (getCvv().length() != 3) {
            showError("Please enter a valid 3-digit CVV");
            return false;
        }
        
        return true;
    }
    
    private void showError(String message) {
        javax.swing.JOptionPane.showMessageDialog(this, message, "Validation Error", 
                javax.swing.JOptionPane.ERROR_MESSAGE);
    }
    
    public void showPaymentProcessing() {
        payNowButton.setText("Processing...");
        payNowButton.setEnabled(false);
        cancelButton.setEnabled(false);
    }
    
    public void resetPaymentButton() {
        payNowButton.setText("Pay Now");
        payNowButton.setEnabled(true);
        cancelButton.setEnabled(true);
    }
}