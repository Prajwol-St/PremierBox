package movieticket.controller;

import movieticket.dao.UserDao;
import movieticket.model.Payment;
import movieticket.model.SeatBooking;
import movieticket.view.PaymentView;

import javax.swing.*;
import java.time.LocalDateTime;

public class PaymentController {

    private final PaymentView view;
    private final UserDao     userDao;
    private final int         userId;
    private final int         movieId;          // ─── NEW ───
    private final String      seatNumbers;      // ─── NEW ───

    public PaymentController(PaymentView v, UserDao dao,
                             int uid, int movieId, String seats) {     // ─── NEW ───
        this.view   = v;
        this.userDao= dao;
        this.userId = uid;
        this.movieId= movieId;                                         // ─── NEW ───
        this.seatNumbers = seats;                                      // ─── NEW ───

        view.payButton.addActionListener(e -> processPayment());
    }

    /* ---------------------------------------------------------------- */
    private void processPayment() {

        try {
            double amount     = Double.parseDouble(view.amountField.getText().trim());
            String cardNumber = view.cardNumberField.getText().trim();
            String holder     = view.cardHolderField.getText().trim();
            String expiry     = view.expiryField.getText().trim();
            String cvv        = new String(view.cvvField.getPassword()).trim();

            if (cardNumber.isEmpty() || holder.isEmpty()
                    || expiry.isEmpty() || cvv.isEmpty()) {
                JOptionPane.showMessageDialog(view,
                        "Please fill in all fields.",
                        "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            /* 1. try to book seats now --------------------------------*/
            SeatBooking booking = new SeatBooking(userId, movieId, seatNumbers);
            int bookingId = userDao.bookSeatsAndReturnId(booking);          // ─── NEW ───
            if (bookingId == -1) {                                          // ─── NEW ───
                JOptionPane.showMessageDialog(view,
                        "Seats just got booked by someone else.",
                        "Booking Lost", JOptionPane.ERROR_MESSAGE);
                return;
            }

            /* 2. record the payment ---------------------------------- */
            Payment pay = new Payment(bookingId, amount, "CARD",
                                       cardNumber, holder, expiry, cvv);
            pay.setPaymentStatus("SUCCESS");
            pay.setTransactionId("TXN" + System.currentTimeMillis());
            pay.setPaymentDate(LocalDateTime.now());

            boolean ok = userDao.makePayment(pay);
            if (ok) {
                JOptionPane.showMessageDialog(view,
                        "Payment & booking successful!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                userDao.addNotification(                       // ─── NEW
                userId,
                "Your tickets have been confirmed – enjoy the show!");
                view.dispose();
            } else {
                JOptionPane.showMessageDialog(view,
                        "Payment saved failed.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view,
                    "Invalid input: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
