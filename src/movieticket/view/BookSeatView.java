package movieticket.view;

import movieticket.dao.UserDao;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.stream.Collectors;

public class BookSeatView extends JFrame {

    private final JPanel seatPanel;
    public final JButton bookButton;
    private final JLabel infoLabel;
    private final Set<JButton> selectedSeats = new HashSet<>();
    private final Map<JButton, String> seatMap = new HashMap<>();
    private final UserDao userDao;
    private final int movieId;
    // --- ADDED: Total price label ---
    private JLabel totalPriceLabel;

    public BookSeatView(UserDao dao, int movieId) {
        this.userDao = dao;
        this.movieId = movieId;

        setTitle("Book Your Seats");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setBackground(new Color(34,34,40));
        setLayout(new BorderLayout(0,0));

        // Header
        infoLabel = new JLabel("Select seats and click “Book”");
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        infoLabel.setForeground(Color.WHITE);
        infoLabel.setBorder(new EmptyBorder(15,0,15,0));
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(34,34,40));
        headerPanel.add(infoLabel, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        // Main panel with screen and seats, light gray background
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(220,220,225));

        // SCREEN label centered above seats, black color
        JLabel screenLabel = new JLabel("SCREEN", SwingConstants.CENTER);
        screenLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        screenLabel.setForeground(Color.BLACK);
        screenLabel.setBorder(new EmptyBorder(32,0,24,0));
        mainPanel.add(screenLabel, BorderLayout.NORTH);

        // Seat grid
        seatPanel = new JPanel(new GridBagLayout());
        seatPanel.setBackground(new Color(220,220,225));
        seatPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8,8,8,8);

        int numRows = 7;
        int numCols = 10;

        for(int row = 0; row < numRows; row++) {
            char rowChar = (char)('A' + row);
            for(int col = 0; col < numCols; col++) {
                gbc.gridx = col;
                gbc.gridy = row;
                String sid = rowChar + String.valueOf(col + 1);
                Color seatColor;
                if(col == 0) {
                    seatColor = new Color(255,120,120); // Leftmost: red
                } else if(col == numCols - 1) {
                    seatColor = new Color(120,255,120); // Rightmost: green
                } else {
                    seatColor = new Color(120,180,255); // Center: blue
                }
                seatPanel.add(createSeatButton(sid, seatColor), gbc);
            }
        }

        // --- ADDED: Total price label below G row at right corner ---
        gbc.gridx = numCols - 1; // last column
        gbc.gridy = numRows;     // row after last seat row
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        totalPriceLabel = new JLabel("Total: 0");
        totalPriceLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        totalPriceLabel.setForeground(Color.BLACK);
        seatPanel.add(totalPriceLabel, gbc);

        mainPanel.add(seatPanel, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);

        // Footer
        bookButton = new JButton("BOOK SELECTED SEATS");
        stylePrimaryButton(bookButton);
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER,0,28));
        bottom.setBackground(new Color(34,34,40));
        bottom.add(bookButton);
        add(bottom, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    /* --------------- helpers ----------------------------------- */

    private JButton createSeatButton(String id, Color base) {
        JButton b = new JButton(id.toUpperCase());
        b.setPreferredSize(new Dimension(64,48));
        b.setFont(new Font("Segoe UI",Font.BOLD,18));
        b.setForeground(Color.WHITE);
        b.setBorder(BorderFactory.createEmptyBorder());
        b.setFocusPainted(false);
        b.setContentAreaFilled(false);

        // custom UI for rounded seat
        b.setUI(new BasicButtonUI() {
            @Override public void paint(Graphics g,JComponent c) {
                Graphics2D g2 = (Graphics2D)g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color fill = selectedSeats.contains(b) ? new Color(0,200,0)
                        : !b.isEnabled() ? new Color(120,120,120)
                        : base;
                g2.setColor(fill);
                g2.fillRoundRect(0,0,c.getWidth(),c.getHeight(),14,14);
                super.paint(g,c);
                g2.dispose();
            }
        });

        if(userDao.isSeatBooked(movieId, id)) {
            b.setEnabled(false); // booked = grey
        } else {
            b.addActionListener(e -> toggleSeat(b, base));
        }

        seatMap.put(b, id.toUpperCase());
        return b;
    }

    private void toggleSeat(JButton btn, Color base) {
        if(selectedSeats.contains(btn)) {
            selectedSeats.remove(btn);
        } else {
            selectedSeats.add(btn);
        }
        btn.repaint();
        updateTotalPrice(); // update price when selection changes
    }

    // --- ADDED: Update total price label ---
    private void updateTotalPrice() {
        int total = selectedSeats.size() * 250;
        totalPriceLabel.setText("Total: " + total);
    }

    public void addBookListener(ActionListener l) {
        bookButton.addActionListener(l);
    }

    public String getSelectedSeats() {
        return selectedSeats.stream().map(seatMap::get).collect(Collectors.joining(","));
    }

    public int getMovieId() {
        return movieId;
    }

    private void stylePrimaryButton(JButton b) {
        b.setFont(new Font("Segoe UI",Font.BOLD,18));
        b.setForeground(Color.white);
        b.setBackground(new Color(0x0E63C4));
        b.setBorder(BorderFactory.createEmptyBorder(14,36,14,36));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setUI(new BasicButtonUI() {
            @Override public void paint(Graphics g,JComponent c) {
                Graphics2D g2 = (Graphics2D)g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color fill = b.getModel().isArmed()?new Color(0x094A90)
                        : new Color(0x0E63C4);
                g2.setColor(fill);
                g2.fillRoundRect(0,0,c.getWidth(),c.getHeight(),30,30);
                super.paint(g,c); g2.dispose();
            }
        });
    }
}
