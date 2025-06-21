package movieticket.view;

import movieticket.dao.UserDao;
import javax.swing.*;
import java.awt.*;

public class PaymentView extends JFrame {
    public JTextField bookingIdField, amountField, cardNumberField, cardHolderField, expiryField;
    public JPasswordField cvvField;
    public JButton payButton;

    // Optional: Store userId and userDao if needed for further logic
    private int userId;
    private UserDao userDao;

    // No-argument constructor (existing)
    public PaymentView() {
        initializeUI();
    }

    // New constructor to match your usage in BookSeatController
    public PaymentView(int userId, UserDao userDao) {
        this.userId = userId;
        this.userDao = userDao;
        initializeUI();
        // You can use userId and userDao as needed in your PaymentView logic
    }

    // UI initialization extracted to a method to avoid code duplication
    private void initializeUI() {
        setTitle("Payment");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(420, 380);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createTitledBorder("Enter Payment Details"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Booking ID
        mainPanel.add(new JLabel("Booking ID:"), gbc);
        gbc.gridx = 1;
        bookingIdField = new JTextField(16);
        mainPanel.add(bookingIdField, gbc);

        // Amount
        gbc.gridy++;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("Amount:"), gbc);
        gbc.gridx = 1;
        amountField = new JTextField(16);
        mainPanel.add(amountField, gbc);

        // Card Number
        gbc.gridy++;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("Card Number:"), gbc);
        gbc.gridx = 1;
        cardNumberField = new JTextField(19);
        cardNumberField.setToolTipText("Enter your 16-digit card number");
        mainPanel.add(cardNumberField, gbc);

        // Card Holder
        gbc.gridy++;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("Card Holder:"), gbc);
        gbc.gridx = 1;
        cardHolderField = new JTextField(16);
        mainPanel.add(cardHolderField, gbc);

        // Expiry Date
        gbc.gridy++;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("Expiry Date (MM/YY):"), gbc);
        gbc.gridx = 1;
        expiryField = new JTextField(5);
        expiryField.setToolTipText("Format: MM/YY");
        mainPanel.add(expiryField, gbc);

        // CVV
        gbc.gridy++;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("CVV:"), gbc);
        gbc.gridx = 1;
        cvvField = new JPasswordField(3);
        cvvField.setToolTipText("3-digit code at the back of your card");
        mainPanel.add(cvvField, gbc);

        // Pay Button
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        payButton = new JButton("Pay Now");
        mainPanel.add(payButton, gbc);

        add(mainPanel);
    }
}
