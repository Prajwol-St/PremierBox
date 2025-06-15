package movieticket.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.stream.Collectors;

public class BookSeatView extends JFrame {
    private JPanel seatPanel;
    private JButton bookButton;
    private JLabel infoLabel;
    private Set<JButton> selectedSeats = new HashSet<>();
    private Map<JButton, String> seatMap = new HashMap<>();

    public BookSeatView() {
        setTitle("Book Your Seats");
        setSize(500, 420);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        infoLabel = new JLabel("Select seats and click 'Book'");
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        seatPanel = new JPanel(new GridLayout(5, 8, 10, 10));
        seatPanel.setBorder(BorderFactory.createTitledBorder("Seat Layout"));

        // Create seat buttons (A1 to E8 for example)
        for (char row = 'A'; row <= 'E'; row++) {
            for (int col = 1; col <= 8; col++) {
                String seatId = "" + row + col;
                JButton seatBtn = new JButton(seatId);
                seatBtn.setBackground(Color.WHITE);
                seatBtn.setFocusPainted(false);
                seatBtn.addActionListener(e -> toggleSeat(seatBtn));
                seatPanel.add(seatBtn);
                seatMap.put(seatBtn, seatId);
            }
        }

        bookButton = new JButton("Book Selected Seats");
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(bookButton);

        setLayout(new BorderLayout(10, 10));
        add(infoLabel, BorderLayout.NORTH);
        add(seatPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void toggleSeat(JButton btn) {
        if (selectedSeats.contains(btn)) {
            btn.setBackground(Color.WHITE);
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
}
