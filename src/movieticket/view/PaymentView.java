package movieticket.view;

import javax.swing.*;
import java.awt.*;
import movieticket.dao.UserDao;

public class PaymentView extends JFrame {
    // Add fields for payment info
    public JTextField bookingIdField, amountField, cardNumberField, cardHolderField, expiryField, cvvField;
    public JButton payButton;

    public PaymentView(int userId, UserDao userDao) {
        setTitle("Payment");
        setSize(400, 400);
        setLayout(new GridLayout(7, 2, 10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        add(new JLabel("Booking ID:"));
        bookingIdField = new JTextField();
        add(bookingIdField);

        add(new JLabel("Amount:"));
        amountField = new JTextField();
        add(amountField);

        add(new JLabel("Card Number:"));
        cardNumberField = new JTextField();
        add(cardNumberField);

        add(new JLabel("Card Holder:"));
        cardHolderField = new JTextField();
        add(cardHolderField);

        add(new JLabel("Expiry Date (MM/YY):"));
        expiryField = new JTextField();
        add(expiryField);

        add(new JLabel("CVV:"));
        cvvField = new JTextField();
        add(cvvField);

        payButton = new JButton("Pay");
        add(payButton);

        // Empty label for alignment
        add(new JLabel());
    }
}
