package movieticket.controller;

import movieticket.dao.UserDao;
import movieticket.view.BookSeatView;
import movieticket.view.PaymentView;

import javax.swing.*;

public class BookSeatController {

    private final BookSeatView view;
    private final UserDao      userDao;
    private final int          userId;

    public BookSeatController(BookSeatView v, UserDao dao, int uid) {
        this.view    = v;
        this.userDao = dao;
        this.userId  = uid;

        view.bookButton.addActionListener(e -> proceedToPayment());   // ─── NEW ───
    }                                                                 // ─── NEW ───

    /* ---------------------------------------------------------------- */
    private void proceedToPayment() {                                  // ─── NEW ───

        String seatNumbers = view.getSelectedSeats();
        if (seatNumbers == null || seatNumbers.isBlank()) {
            JOptionPane.showMessageDialog(view,
                    "Please select at least one seat.",
                    "No Seats Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int movieId = view.getMovieId();                               // ask the view

        // open payment window, pass movieId + seats + amount if you have it
        PaymentView pay = new PaymentView(userId, userDao, movieId, seatNumbers);// NEW
        new PaymentController(pay, userDao, userId, movieId, seatNumbers);       // NEW
        pay.setVisible(true);
        view.dispose();
    }
}
