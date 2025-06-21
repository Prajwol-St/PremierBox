package movieticket.view;

import movieticket.dao.UserDao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.stream.Collectors;

public class BookSeatView extends JFrame {

    private JPanel seatPanel;
    public JButton bookButton;
    private JLabel infoLabel;
    private Set<JButton> selectedSeats = new HashSet<>();
    private Map<JButton, String> seatMap = new HashMap<>();
    private UserDao userDao;
    private int movieId;

    public BookSeatView(UserDao userDao, int movieId) {
        this.userDao = userDao;
        this.movieId = movieId;

        setTitle("Book Your Seats");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(false);

        infoLabel = new JLabel("Select seats (colored by type) and click 'Book'");
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        seatPanel = new JPanel(new GridBagLayout());
        seatPanel.setBorder(BorderFactory.createTitledBorder("Seat Layout"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);

        // Couple Seats (left column, red)
        for (int i = 0; i < 7; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            String seatId = "C" + (i + 1);
            JButton seatBtn = createSeatButton(seatId, new Color(255, 120, 120));
            seatPanel.add(seatBtn, gbc);
        }

        // Family Seats (center grid, blue)
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 8; col++) {
                gbc.gridx = col + 1;
                gbc.gridy = row;
                String seatId = "F" + (row + 1) + (col + 1);
                JButton seatBtn = createSeatButton(seatId, new Color(120, 180, 255));
                seatPanel.add(seatBtn, gbc);
            }
        }

        // Mixed Seats (right column, green)
        for (int i = 0; i < 7; i++) {
            gbc.gridx = 9;
            gbc.gridy = i;
            String seatId = "M" + (i + 1);
            JButton seatBtn = createSeatButton(seatId, new Color(120, 255, 120));
            seatPanel.add(seatBtn, gbc);
        }

        bookButton = new JButton("Book Selected Seats");
        bookButton.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(bookButton);

        setLayout(new BorderLayout(10, 10));
        add(infoLabel, BorderLayout.NORTH);
        add(new JScrollPane(seatPanel), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JButton createSeatButton(String seatId, Color baseColor) {
        JButton btn = new JButton(seatId);
        btn.setBackground(baseColor);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(70, 48));
        btn.setFont(new Font("Arial", Font.BOLD, 18));
        if (userDao.isSeatBooked(movieId, seatId)) {
            btn.setEnabled(false);
            btn.setBackground(Color.GRAY);
        } else {
            btn.addActionListener(e -> toggleSeat(btn, baseColor));
        }
        seatMap.put(btn, seatId);
        return btn;
    }

    private void toggleSeat(JButton btn, Color baseColor) {
        if (selectedSeats.contains(btn)) {
            btn.setBackground(baseColor);
            selectedSeats.remove(btn);
        } else {
            btn.setBackground(new Color(0, 200, 0));
            selectedSeats.add(btn);
        }
    }

    public void addBookListener(ActionListener listener) {
        bookButton.addActionListener(listener);
    }

    public String getSelectedSeats() {
        return selectedSeats.stream().map(seatMap::get).collect(Collectors.joining(","));
    }

    // --- This is the getter you need for your controller ---
    public int getMovieId() {
        return this.movieId;
    }
}
