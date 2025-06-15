package movieticket.view;

import javax.swing.*;
import movieticket.controller.BookSeatController;
import movieticket.dao.UserDao;

public class DashboardView extends javax.swing.JFrame {
    private javax.swing.JButton availableMovies;
    private javax.swing.JButton seatPlan;
    private javax.swing.JButton profileButton;
    private javax.swing.JButton paymentButton;
    private javax.swing.JButton bookSeatButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JTabbedPane jTabbedPane1;

    public DashboardView() {
        initComponents();
    }

    private void initComponents() {
        mainPanel = new javax.swing.JPanel();
        profileButton = new javax.swing.JButton();
        availableMovies = new javax.swing.JButton();
        seatPlan = new javax.swing.JButton();
        paymentButton = new javax.swing.JButton();
        bookSeatButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        mainPanel.setOpaque(false);

        profileButton.setBackground(new java.awt.Color(0, 0, 0));
        profileButton.setFont(new java.awt.Font("Helvetica Neue", 0, 16));
        profileButton.setForeground(new java.awt.Color(255, 255, 255));
        profileButton.setText("Profile");
        profileButton.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(25, 25, 112), 1, true));

        availableMovies.setBackground(new java.awt.Color(0, 0, 0));
        availableMovies.setFont(new java.awt.Font("Helvetica Neue", 0, 16));
        availableMovies.setForeground(new java.awt.Color(255, 255, 255));
        availableMovies.setText("Available Movies");
        availableMovies.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(25, 25, 112), 1, true));

        seatPlan.setBackground(new java.awt.Color(0, 0, 0));
        seatPlan.setFont(new java.awt.Font("Helvetica Neue", 0, 16));
        seatPlan.setForeground(new java.awt.Color(255, 255, 255));
        seatPlan.setText("Seat Plan");
        seatPlan.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(25, 25, 112), 1, true));

        paymentButton.setBackground(new java.awt.Color(0, 0, 0));
        paymentButton.setFont(new java.awt.Font("Helvetica Neue", 0, 16));
        paymentButton.setForeground(new java.awt.Color(255, 255, 255));
        paymentButton.setText("Payment");
        paymentButton.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(25, 25, 112), 1, true));
        paymentButton.addActionListener(this::paymentButtonActionPerformed);

        bookSeatButton.setBackground(new java.awt.Color(0, 0, 0));
        bookSeatButton.setFont(new java.awt.Font("Helvetica Neue", 0, 16));
        bookSeatButton.setForeground(new java.awt.Color(255, 255, 255));
        bookSeatButton.setText("Book Seat");
        bookSeatButton.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(25, 25, 112), 1, true));
        bookSeatButton.addActionListener(this::bookSeatButtonActionPerformed);

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(profileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(availableMovies, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seatPlan, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(paymentButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bookSeatButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(profileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(availableMovies)
                .addGap(18, 18, 18)
                .addComponent(seatPlan, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(paymentButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(bookSeatButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(113, Short.MAX_VALUE))
        );

        getContentPane().add(mainPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 170, 420));

        jLabel1.setText("Dashboard Background Placeholder");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 600));
        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 61, 500, 360));

        pack();
    }

    private void paymentButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String input = JOptionPane.showInputDialog(this, "Enter amount to pay:", "Payment Amount", JOptionPane.PLAIN_MESSAGE);
        if (input != null) {
            try {
                double amount = Double.parseDouble(input.trim());
                if (amount <= 0) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid amount.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int bookingId = 1; // TODO: Replace with actual booking ID
                movieticket.view.PaymentView paymentView = new movieticket.view.PaymentView(amount);
                movieticket.controller.PaymentController paymentController = new movieticket.controller.PaymentController(paymentView, bookingId);
                paymentController.open();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Amount must be a number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void bookSeatButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int userId = 1; // TODO: Replace with actual logged-in user ID
        int movieId = 1; // TODO: Replace with actual selected movie ID

        movieticket.view.BookSeatView seatView = new movieticket.view.BookSeatView();
        movieticket.controller.BookSeatController controller = new movieticket.controller.BookSeatController(
            seatView, new UserDao(), userId, movieId
        );
        seatView.setVisible(true);
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new DashboardView().setVisible(true));
    }
}
