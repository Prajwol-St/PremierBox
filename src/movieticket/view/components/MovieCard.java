package movieticket.view.components;

import movieticket.model.MoviesData;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MovieCard extends JPanel {

    
    public MovieCard(MoviesData movie) {

        setPreferredSize(new Dimension(230, 360));
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        setBackground(new Color(0xF4F6F9));

        /* 1. Poster ---------------------------------------------------- */
        JLabel posterLabel = new JLabel();
        posterLabel.setHorizontalAlignment(SwingConstants.CENTER);
        posterLabel.setIcon(movie.getScaledPoster(200, 240)); // your helper
        add(posterLabel, BorderLayout.NORTH);

        /* 2. Meta-data panel ------------------------------------------ */
        JPanel info = new JPanel(new GridLayout(0, 1));
        info.setOpaque(false);
        info.add(bold(" " + movie.getTitle()));
        info.add(new JLabel("Genre:  " + movie.getGenre()));
        info.add(new JLabel("Date:   " + movie.getDate()));

        /* 3. BUTTON BAR FOR TIMES  (replaces old JLabel) --------------- */
        JPanel timeBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 4));
        timeBar.setOpaque(false);
        
        java.time.format.DateTimeFormatter HHMM =
        java.time.format.DateTimeFormatter.ofPattern("HH:mm");

         java.util.List<String> prettyTimes = java.util.Optional
        .ofNullable(movie.getShowTimes())          // could be null
        .orElse(java.util.Collections.emptyList()) // never null now
        .stream()
        .map(t -> {                                // trim “:00”
            try { return java.time.LocalTime.parse(t).format(HHMM); }
            catch (Exception ex) { return t; }
        })
        .toList();
         

        for (String t : /* REPLACE this variable */ prettyTimes) {      // ← CHANGE

    JButton b = new JButton(t.trim());
    b.setFocusPainted(false);
    b.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    b.setBackground(new Color(0x113C94));
    b.setForeground(Color.WHITE);
    b.setBorder(BorderFactory.createEmptyBorder(4, 12, 4, 12));

    /* confirmation pop-up on click ------------------------ */
    b.addActionListener(e -> {
        int choice = JOptionPane.showConfirmDialog(
                MovieCard.this,                                 // ← CHANGE “this”
                "Book a seat for " + movie.getTitle()
              + " (" + t + ")?",
                "Confirm booking",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (choice == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(
                    MovieCard.this,
                    "Seat booking coming soon!",
                    "Booked", JOptionPane.INFORMATION_MESSAGE);
            // later: open seat-selection window here
        }
    });

    timeBar.add(b);                          // stay inside the loop
}                                            // ← loop ends here

/* ────────────────── INSERT BLOCK ②  add timeBar row ───────── */
info.add(timeBar);                           // ONE call, after loop finishes
/* ───────────────────────────────────────────────────────────── */

add(info, BorderLayout.CENTER);

        /* 4. Optional “Book Now” footer ------------------------------- */
//        JButton bookNow = new JButton("Book Now");
//        bookNow.setEnabled(false);   // will enable once seat screen exists
//        add(bookNow, BorderLayout.SOUTH);
    }

    /* helper for bold label */
    private JLabel bold(String txt) {
        JLabel l = new JLabel(txt, SwingConstants.CENTER);
        l.setFont(new Font("Segoe UI", Font.BOLD, 15));
        return l;
    }
}
