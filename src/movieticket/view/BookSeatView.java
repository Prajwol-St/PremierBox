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

    public BookSeatView(UserDao dao, int movieId) {
        this.userDao = dao;
        this.movieId = movieId;

        setTitle("Book Your Seats");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setBackground(new Color(21,24,30));
        setLayout(new BorderLayout(10,10));

        // Header
        infoLabel = new JLabel("Select seats and click “Book”");
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        infoLabel.setForeground(Color.WHITE);
        infoLabel.setBorder(new EmptyBorder(15,0,15,0));
        add(infoLabel, BorderLayout.NORTH);

        // Seat grid
        seatPanel = new JPanel(new GridBagLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D)g.create();
                g2.setPaint(new GradientPaint(0,0,new Color(34,34,40), 0,getHeight(),new Color(26,26,32)));
                g2.fillRect(0,0,getWidth(),getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        seatPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6,6,6,6);

        // 7 rows, 10 columns (a-j), each seat: a1, a2, ..., g10
        int numRows = 7;
        int numCols = 10;
        for(int row = 0; row < numRows; row++) {
            char rowChar = (char)('a' + row); // 'a', 'b', ...
            for(int col = 0; col < numCols; col++) {
                gbc.gridx = col;
                gbc.gridy = row;
                String sid = rowChar + String.valueOf(col + 1); // a1, a2, ..., b1, ...
                Color seatColor;
                if(col == 0) {
                    seatColor = new Color(255,120,120); // Leftmost: red (couple)
                } else if(col == numCols - 1) {
                    seatColor = new Color(120,255,120); // Rightmost: green (mixed)
                } else {
                    seatColor = new Color(120,180,255); // Center: blue (family)
                }
                seatPanel.add(createSeatButton(sid, seatColor), gbc);
            }
        }

        JScrollPane scroll = new JScrollPane(seatPanel);
        scroll.setBorder(null);
        add(scroll, BorderLayout.CENTER);

        // Footer
        bookButton = new JButton("BOOK SELECTED SEATS");
        stylePrimaryButton(bookButton);
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER,0,20));
        bottom.setOpaque(false);
        bottom.add(bookButton);
        add(bottom, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    /* --------------- helpers ----------------------------------- */

    private JButton createSeatButton(String id, Color base) {
        JButton b = new JButton(id);
        b.setPreferredSize(new Dimension(72,48));
        b.setFont(new Font("Segoe UI",Font.BOLD,16));
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
                        : !b.isEnabled() ? Color.GRAY
                        : base;
                g2.setColor(fill);
                g2.fillRoundRect(0,0,c.getWidth(),c.getHeight(),16,16);
                super.paint(g,c);
                g2.dispose();
            }
        });

        if(userDao.isSeatBooked(movieId, id)) {
            b.setEnabled(false); // booked = grey
        } else {
            b.addActionListener(e -> toggleSeat(b, base));
        }

        seatMap.put(b, id);
        return b;
    }

    private void toggleSeat(JButton btn, Color base) {
        if(selectedSeats.contains(btn)) {
            selectedSeats.remove(btn);
        } else {
            selectedSeats.add(btn);
        }
        btn.repaint();
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
        b.setBorder(BorderFactory.createEmptyBorder(12,28,12,28));
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
