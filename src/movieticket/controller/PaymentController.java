package movieticket.controller;

import movieticket.dao.UserDao;
import movieticket.model.Payment;
import movieticket.view.PaymentView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

public class PaymentController {
    private PaymentView view;
    private UserDao userDao;
    private int userId;

    public PaymentController(PaymentView view, UserDao userDao, int userId) {
        this.view = view;
        this.userDao = userDao;
        this.userId = userId;

        this.view.payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processPayment();
            }
        });
    }

    private void processPayment() {
        try {
            int bookingId = Integer.parseInt(view.bookingIdField.getText());
            double amount = Double.parseDouble(view.amountField.getText());
            String cardNumber = view.cardNumberField.getText();
            String cardHolder = view.cardHolderField.getText();
            String expiry = view.expiryField.getText();
            String cvv = view.cvvField.getText();

            Payment payment = new Payment(bookingId, amount, "CARD", cardNumber, cardHolder, expiry, cvv);
            payment.setPaymentStatus("SUCCESS");
            payment.setTransactionId("TXN" + System.currentTimeMillis());
            payment.setPaymentDate(LocalDateTime.now());

            boolean success = userDao.makePayment(payment);
            if (success) {
                JOptionPane.showMessageDialog(view, "Payment successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                view.dispose();
            } else {
                JOptionPane.showMessageDialog(view, "Payment failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Invalid input: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
