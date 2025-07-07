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
    public  final JButton bookButton;
    private final JLabel  infoLabel;

    private final Set<JButton> selectedSeats = new HashSet<>();
    private final Map<JButton,String> seatMap = new HashMap<>();

    private final UserDao userDao;
<<<<<<< HEAD
    private final int movieId;
    // --- ADDED: Total price label ---
    private JLabel totalPriceLabel;
=======
    private final int     movieId;
>>>>>>> parent of 1d26aa3 (seat changed to a1,a2)

    public BookSeatView(UserDao dao, int movieId) {
        this.userDao = dao;
        this.movieId = movieId;

        /* ------------------- FRAME décor ------------------------ */
        setTitle("Book Your Seats");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
<<<<<<< HEAD
        getContentPane().setBackground(new Color(34,34,40));
        setLayout(new BorderLayout(0,0));
=======
        getContentPane().setBackground(new Color(21,24,30));             // ─── NEW ───
        setLayout(new BorderLayout(10,10));
>>>>>>> parent of 1d26aa3 (seat changed to a1,a2)

        /* Header ------------------------------------------------- */
        infoLabel = new JLabel("Select seats and click “Book”");
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
<<<<<<< HEAD
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
=======
        infoLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));          // ─── NEW ───
        infoLabel.setForeground(Color.WHITE);                            // ─── NEW ───
        infoLabel.setBorder(new EmptyBorder(15,0,15,0));                 // ─── NEW ───
        add(infoLabel, BorderLayout.NORTH);

        /* Seat grid ---------------------------------------------- */
        seatPanel = new JPanel(new GridBagLayout()) {
            @Override protected void paintComponent(Graphics g) {        // ─── NEW ───
                Graphics2D g2=(Graphics2D)g.create();                    // ─── NEW ───
                g2.setPaint(new GradientPaint(0,0,new Color(34,34,40),   // ─── NEW ───
                                              0,getHeight(),new Color(26,26,32))); // ─── NEW ───
                g2.fillRect(0,0,getWidth(),getHeight());                 // ─── NEW ───
                g2.dispose();                                            // ─── NEW ───
                super.paintComponent(g);                                 // ─── NEW ───
            }
        };
        seatPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));// ─── NEW ───

>>>>>>> parent of 1d26aa3 (seat changed to a1,a2)
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8,8,8,8);

<<<<<<< HEAD
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
=======
        /* Couple seats (left) */
        for(int i=0;i<7;i++){
            gbc.gridx=0; gbc.gridy=i;
            String sid="C"+(i+1);
            seatPanel.add(createSeatButton(sid,new Color(255,120,120)),gbc);
        }
        /* Family seats (centre) */
        for(int r=0;r<6;r++){
            for(int c=0;c<8;c++){
                gbc.gridx=c+1; gbc.gridy=r;
                String sid="F"+(r+1)+(c+1);
                seatPanel.add(createSeatButton(sid,new Color(120,180,255)),gbc);
>>>>>>> parent of 1d26aa3 (seat changed to a1,a2)
            }
        }
        /* Mixed seats (right) */
        for(int i=0;i<7;i++){
            gbc.gridx=9; gbc.gridy=i;
            String sid="M"+(i+1);
            seatPanel.add(createSeatButton(sid,new Color(120,255,120)),gbc);
        }

<<<<<<< HEAD
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
=======
        JScrollPane scroll = new JScrollPane(seatPanel);
        scroll.setBorder(null);
        add(scroll,BorderLayout.CENTER);
>>>>>>> parent of 1d26aa3 (seat changed to a1,a2)

        /* Footer -------------------------------------------------- */
        bookButton = new JButton("BOOK SELECTED SEATS");
<<<<<<< HEAD
        stylePrimaryButton(bookButton);
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER,0,28));
        bottom.setBackground(new Color(34,34,40));
=======
        stylePrimaryButton(bookButton);                                   // ─── NEW ───
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER,0,20));
        bottom.setOpaque(false);                                          // ─── NEW ───
>>>>>>> parent of 1d26aa3 (seat changed to a1,a2)
        bottom.add(bookButton);
        add(bottom,BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    /* --------------- helpers ----------------------------------- */
<<<<<<< HEAD

    private JButton createSeatButton(String id, Color base) {
        JButton b = new JButton(id.toUpperCase());
        b.setPreferredSize(new Dimension(64,48));
        b.setFont(new Font("Segoe UI",Font.BOLD,18));
        b.setForeground(Color.WHITE);
=======
    private JButton createSeatButton(String id, Color base){
        JButton b = new JButton(id);
        b.setPreferredSize(new Dimension(72,48));
        b.setFont(new Font("Segoe UI",Font.BOLD,16));
        b.setForeground(Color.WHITE);                                     // ─── NEW ───
>>>>>>> parent of 1d26aa3 (seat changed to a1,a2)
        b.setBorder(BorderFactory.createEmptyBorder());
        b.setFocusPainted(false);
        b.setContentAreaFilled(false);                                    // ─── NEW ───

<<<<<<< HEAD
        // custom UI for rounded seat
        b.setUI(new BasicButtonUI() {
            @Override public void paint(Graphics g,JComponent c) {
                Graphics2D g2 = (Graphics2D)g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color fill = selectedSeats.contains(b) ? new Color(0,200,0)
                        : !b.isEnabled() ? new Color(120,120,120)
                        : base;
=======
        /* custom UI for rounded seat */
        b.setUI(new BasicButtonUI(){
            @Override public void paint(Graphics g,JComponent c){
                Graphics2D g2=(Graphics2D)g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
                Color fill = selectedSeats.contains(b)    ? new Color(0,200,0)
                            : !b.isEnabled()              ? Color.GRAY
                                                         : base;
>>>>>>> parent of 1d26aa3 (seat changed to a1,a2)
                g2.setColor(fill);
                g2.fillRoundRect(0,0,c.getWidth(),c.getHeight(),14,14);
                super.paint(g,c);
                g2.dispose();
            }
        });

        if(userDao.isSeatBooked(movieId,id)){
            b.setEnabled(false);                                          // booked = grey
        }else{
            b.addActionListener(e -> toggleSeat(b,base));
        }
<<<<<<< HEAD

        seatMap.put(b, id.toUpperCase());
=======
        seatMap.put(b,id);
>>>>>>> parent of 1d26aa3 (seat changed to a1,a2)
        return b;
    }

    private void toggleSeat(JButton btn,Color base){
        if(selectedSeats.contains(btn)){
            selectedSeats.remove(btn);
        }else{
            selectedSeats.add(btn);
        }
<<<<<<< HEAD
        btn.repaint();
        updateTotalPrice(); // update price when selection changes
    }

    // --- ADDED: Update total price label ---
    private void updateTotalPrice() {
        int total = selectedSeats.size() * 250;
        totalPriceLabel.setText("Total: " + total);
=======
        btn.repaint();                                                    // ─── NEW ───
>>>>>>> parent of 1d26aa3 (seat changed to a1,a2)
    }

    public void addBookListener(ActionListener l){ bookButton.addActionListener(l); }
    public String getSelectedSeats(){
        return selectedSeats.stream().map(seatMap::get).collect(Collectors.joining(","));
    }
    public int getMovieId(){ return movieId; }

<<<<<<< HEAD
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
=======
    private void stylePrimaryButton(JButton b){                           // ─── NEW ───
        b.setFont(new Font("Segoe UI",Font.BOLD,18));                     // ─── NEW ───
        b.setForeground(Color.white);                                     // ─── NEW ───
        b.setBackground(new Color(0x0E63C4));                             // ─── NEW ───
        b.setBorder(BorderFactory.createEmptyBorder(12,28,12,28));        // ─── NEW ───
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));      // ─── NEW ───
        b.setUI(new BasicButtonUI(){                                      // ─── NEW ───
            @Override public void paint(Graphics g,JComponent c){         // ─── NEW ───
                Graphics2D g2=(Graphics2D)g.create();                     // ─── NEW ───
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,      // ─── NEW ───
                                    RenderingHints.VALUE_ANTIALIAS_ON);   // ─── NEW ───
                Color fill = b.getModel().isArmed()?new Color(0x094A90)   // ─── NEW ───
                                              : new Color(0x0E63C4);      // ─── NEW ───
                g2.setColor(fill);                                        // ─── NEW ───
                g2.fillRoundRect(0,0,c.getWidth(),c.getHeight(),30,30);   // ─── NEW ───
                super.paint(g,c); g2.dispose();                           // ─── NEW ───
            }});                                                          // ─── NEW ───
>>>>>>> parent of 1d26aa3 (seat changed to a1,a2)
    }
}
