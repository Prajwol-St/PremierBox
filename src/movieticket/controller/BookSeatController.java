package movieticket.controller;

import movieticket.dao.UserDao;
import movieticket.model.SeatBooking;
import movieticket.view.BookSeatView;
import movieticket.view.PaymentView;

import javax.swing.*;

public class BookSeatController {

    private final BookSeatView view;
    private final UserDao userDao;
    private final int userId;
    private final int movieId;

    public BookSeatController(BookSeatView view, UserDao userDao, int userId, int movieId) {
        this.view = view;
        this.userDao = userDao;
        this.userId = userId;
        this.movieId = movieId;
        this.view.addBookListener(e -> bookSeats());
    }

    private void bookSeats() {
        String seats = view.getSelectedSeats();
        if (seats.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please select at least one seat.", "No Seats Selected", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if any selected seat is already booked
        for (String seat : seats.split(",")) {
            if (userDao.isSeatBooked(movieId, seat.trim())) {
                JOptionPane.showMessageDialog(view, "Seat " + seat + " is already booked.", "Booking Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // Book seats
        SeatBooking booking = new SeatBooking(userId, movieId, seats);
        boolean success = userDao.bookSeats(booking);
        if (success) {
            JOptionPane.showMessageDialog(view, "Seats booked successfully! Proceed to payment.", "Success", JOptionPane.INFORMATION_MESSAGE);
            // Open PaymentView after successful booking
            PaymentView paymentView = new PaymentView(userId, userDao);
            paymentView.setVisible(true);
            view.dispose();
        } else {
            JOptionPane.showMessageDialog(view, "Booking failed. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
