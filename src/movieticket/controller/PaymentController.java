package movieticket.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import movieticket.dao.PaymentDao;
import movieticket.model.Payment;
import movieticket.view.PaymentView;

public class PaymentController {
    private final PaymentView paymentView;
    private final PaymentDao paymentDao;
    private final int bookingId;
    private Payment currentPayment;

    public PaymentController(PaymentView paymentView, int bookingId) {
        this.paymentView = paymentView;
        this.paymentDao = new PaymentDao();
        this.bookingId = bookingId;
        initListeners();
    }

    private void initListeners() {
        paymentView.addPayNowListener(new PayNowListener());
        paymentView.addCancelListener(new CancelListener());
    }

    public void open() {
        paymentView.setLocationRelativeTo(null); // Center on screen
        paymentView.setVisible(true);
    }

    public void close() {
        paymentView.dispose();
    }

    private class PayNowListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Pay Now clicked"); // Debug print

            if (!paymentView.validatePaymentForm()) {
                JOptionPane.showMessageDialog(
                    paymentView,
                    "Please check your payment details and try again.",
                    "Invalid Payment Details",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            paymentView.showPaymentProcessing();
            currentPayment = new Payment(
                bookingId,
                paymentView.getTotalAmount(),
                paymentView.getSelectedPaymentMethod(),
                paymentView.getCardNumber().replaceAll("\\s+", ""), // Sanitize input
                paymentView.getCardHolderName().trim(),
                paymentView.getExpiryDate(),
                paymentView.getCvv()
            );
            new PaymentWorker().execute();
        }
    }

    private class CancelListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int confirm = JOptionPane.showConfirmDialog(
                paymentView,
                "Are you sure you want to cancel the payment?",
                "Cancel Payment",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
            if (confirm == JOptionPane.YES_OPTION) {
                close();
            }
        }
    }

    private class PaymentWorker extends SwingWorker<Boolean, Void> {
        private String errorMessage = "";

        @Override
        protected Boolean doInBackground() {
            try {
                return paymentDao.processPayment(currentPayment);
            } catch (Exception ex) {
                errorMessage = "Payment error: " + ex.getMessage();
                return false;
            }
        }

        @Override
        protected void done() {
            paymentView.resetPaymentButton();
            try {
                if (get()) {
                    JOptionPane.showMessageDialog(
                        paymentView,
                        "Payment processed successfully!\nTransaction ID: " + currentPayment.getTransactionId(),
                        "Payment Successful",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    close();
                } else {
                    String message = !errorMessage.isEmpty() ? errorMessage :
                        "Payment processing failed. Please check your details and try again.";
                    JOptionPane.showMessageDialog(
                        paymentView,
                        message,
                        "Payment Failed",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                    paymentView,
                    "System error: " + ex.getLocalizedMessage(),
                    "Technical Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    // Additional improvements
    public boolean isPaymentVerified() {
        try {
            return paymentDao.getPaymentByBookingId(bookingId) != null;
        } catch (Exception ex) {
            System.err.println("Verification error: " + ex.getMessage());
            return false;
        }
    }
}
