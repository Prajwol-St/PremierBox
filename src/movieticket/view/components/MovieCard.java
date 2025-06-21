package movieticket.view.components;

import javax.swing.*;
import java.awt.*;
import movieticket.model.MoviesData;

public class MovieCard extends JPanel {
    public MovieCard(MoviesData movie) {
        setPreferredSize(new Dimension(220, 340));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        setLayout(new BorderLayout(10, 10));

        // Poster Image
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(200, 180));
        if (movie.getPosterPath() != null) {
            ImageIcon icon = new ImageIcon(movie.getPosterPath());
            Image scaled = icon.getImage().getScaledInstance(200, 180, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaled));
        } else {
            imageLabel.setText("No Image");
            imageLabel.setForeground(Color.GRAY);
        }

        // Info Panel
        JLabel titleLabel = new JLabel(movie.getTitle());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel genreLabel = new JLabel("Genre: " + movie.getGenre());
        genreLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        genreLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel dateLabel = new JLabel("Date: " + movie.getDate());
        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        dateLabel.setHorizontalAlignment(SwingConstants.CENTER);


        JPanel infoPanel = new JPanel(new GridLayout(3, 1));
        infoPanel.setOpaque(false);
        infoPanel.add(titleLabel);
        infoPanel.add(genreLabel);
        infoPanel.add(dateLabel);

        // Button
        JButton bookButton = new JButton("Book Now");
        bookButton.setBackground(new Color(220, 53, 69)); // Bootstrap-like red
        bookButton.setForeground(Color.WHITE);
        bookButton.setFocusPainted(false);
        bookButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        bookButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bookButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        bookButton.addActionListener(e -> {
            // You can pass movie.getMovie_id() if needed
            JOptionPane.showMessageDialog(this, "Booking: " + movie.getTitle());
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(bookButton);

        // Layout arrangement
        add(imageLabel, BorderLayout.NORTH);
        add(infoPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
