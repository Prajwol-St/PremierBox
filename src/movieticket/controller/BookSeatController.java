package movieticket.controller;

import movieticket.dao.UserDao;
import movieticket.model.SeatBooking;
import movieticket.view.BookSeatView;
import movieticket.view.PaymentView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BookSeatController {

    private BookSeatView view;
    private UserDao userDao;
    private int userId;

    public BookSeatController(BookSeatView view, UserDao userDao, int userId) {
        this.view = view;
        this.userDao = userDao;
        this.userId = userId;

        // Attach listener to the "Book Selected Seats" button
        this.view.bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookSeats();
            }
        });
    }

    private void bookSeats() {
    // Get selected seats from the view
    String seatNumbers = view.getSelectedSeats();

    if (seatNumbers == null || seatNumbers.trim().isEmpty()) {
        JOptionPane.showMessageDialog(view, "Please select at least one seat.", "No Seats Selected", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Use the getter instead of accessing the field directly
    int movieId = view.getMovieId();

    // Book seats in the database
    SeatBooking booking = new SeatBooking(userId, movieId, seatNumbers);
    boolean bookingSuccess = userDao.bookSeats(booking);

    if (bookingSuccess) {
        JOptionPane.showMessageDialog(view, "Seats booked! Proceed to payment.");
        PaymentView paymentView = new PaymentView(userId, userDao);
        new PaymentController(paymentView, userDao, userId);
        paymentView.setVisible(true);
        view.dispose();
    } else {
        JOptionPane.showMessageDialog(view, "Booking failed.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

}
