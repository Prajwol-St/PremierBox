package movieticket.view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.*;
import movieticket.controller.PaymentController;
import movieticket.dao.CRUDAdminDAO;
import movieticket.dao.UserDao;
import movieticket.model.MoviesData;
import movieticket.view.components.MovieCard;

public class UserDashboardView extends JFrame {
    private int userId;
    private UserDao userDao = new UserDao();
    private JPanel availableMoviesPanel;
    private JScrollPane availableMoviesScrollPane;
    private JPanel userDashboardPanel;
    private JPanel cardPanel;
    private JButton dashboardButton, availableMoviesButton, paymentButton, logoutButton;

    public UserDashboardView(int userId) {
        this.userId = userId;
        initComponents();
        setupAvailableMoviesPanel();
        // Show dashboard by default; do NOT call showAvailableMovies() here
    }

    private void setupAvailableMoviesPanel() {
        availableMoviesPanel = new JPanel(new GridBagLayout());
        availableMoviesScrollPane = new JScrollPane(availableMoviesPanel);
        availableMoviesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        availableMoviesScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        availableMoviesScrollPane.setBorder(null);

        cardPanel.add(userDashboardPanel, "Dashboard");
        cardPanel.add(availableMoviesScrollPane, "AvailableMovies");
    }

    private void displayAvailableMovies() {
        availableMoviesPanel.removeAll();
        availableMoviesPanel.setLayout(new GridBagLayout());
        CRUDAdminDAO dao = new CRUDAdminDAO();
        try {
            java.util.List<MoviesData> movies = dao.getAllMoviesWithImages();
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(20, 30, 20, 30);
            gbc.anchor = GridBagConstraints.NORTHWEST;
            gbc.fill = GridBagConstraints.NONE;
            int col = 0, row = 0;
            for (MoviesData movie : movies) {
                MovieCard card = new MovieCard(movie, this, userDao, userId);
                gbc.gridx = col;
                gbc.gridy = row;
                availableMoviesPanel.add(card, gbc);
                col++;
                if (col >= 2) { col = 0; row++; }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to load movies.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        availableMoviesPanel.revalidate();
        availableMoviesPanel.repaint();
    }

    private void openPaymentView() {
        PaymentView paymentView = new PaymentView(userId, userDao);
        new PaymentController(paymentView, userDao, userId);
        paymentView.setVisible(true);
    }

    private void initComponents() {
        setTitle("User Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        // Navigation panel
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        dashboardButton = new JButton("Dashboard");
        availableMoviesButton = new JButton("Available Movies");
        paymentButton = new JButton("Payment");
        logoutButton = new JButton("Log Out");
        navPanel.add(dashboardButton);
        navPanel.add(availableMoviesButton);
        navPanel.add(paymentButton);
        navPanel.add(logoutButton);

        // Card panel (center area)
        cardPanel = new JPanel(new CardLayout());

        // Dashboard panel (default)
        userDashboardPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("User Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        userDashboardPanel.add(titleLabel, BorderLayout.NORTH);

        // Add panels to frame
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(navPanel, BorderLayout.NORTH);
        getContentPane().add(cardPanel, BorderLayout.CENTER);

        // Button actions
        dashboardButton.addActionListener(evt -> showDashboard());
        availableMoviesButton.addActionListener(evt -> {
            showAvailableMovies();
            displayAvailableMovies();
        });
        paymentButton.addActionListener(evt -> openPaymentView());
        logoutButton.addActionListener(evt -> logout());

        // Add dashboard to card panel
        cardPanel.add(userDashboardPanel, "Dashboard");
    }

    private void showDashboard() {
        CardLayout cl = (CardLayout) cardPanel.getLayout();
        cl.show(cardPanel, "Dashboard");
    }

    private void showAvailableMovies() {
        CardLayout cl = (CardLayout) cardPanel.getLayout();
        cl.show(cardPanel, "AvailableMovies");
    }

    private void logout() {
        // Implement your logout logic (e.g., return to login screen)
        dispose();
    }

    // Public getters for controller use
    public JPanel getCardPanel() { return cardPanel; }
    public JButton getDashboardButton() { return dashboardButton; }
    public JButton getAvailableMoviesButton() { return availableMoviesButton; }
    public void logoutMovieListener(ActionListener listener) { logoutButton.addActionListener(listener); }

    // Optional: main method for testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UserDashboardView(1).setVisible(true));
    }
}
