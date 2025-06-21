package movieticket.view.components;

import movieticket.model.MoviesData;
import movieticket.dao.UserDao;
import movieticket.view.BookSeatView;
import movieticket.controller.BookSeatController;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MovieCard extends JPanel {
    private UserDao userDao;
    private int userId;

    public MovieCard(MoviesData movie, JFrame parentFrame, UserDao userDao, int userId) {
        this.userDao = userDao;
        this.userId = userId;
        initCard(movie, parentFrame);
    }

    public MovieCard(MoviesData movie) {
        initCard(movie, null);
    }

    private void initCard(MoviesData movie, JFrame parentFrame) {
        setPreferredSize(new Dimension(230, 360));
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        setBackground(new Color(0xF4F6F9));

        // 1. Poster
        JLabel posterLabel = new JLabel();
        posterLabel.setHorizontalAlignment(SwingConstants.CENTER);
        posterLabel.setIcon(movie.getScaledPoster(200, 240));
        add(posterLabel, BorderLayout.NORTH);

        // 2. Meta-data panel
        JPanel info = new JPanel(new GridLayout(0, 1));
        info.setOpaque(false);
        info.add(bold(" " + movie.getTitle()));
        info.add(new JLabel("Genre: " + movie.getGenre()));
        info.add(new JLabel("Date: " + movie.getDate()));

        add(info, BorderLayout.CENTER);

        // 3. Book Seat button
        JButton bookSeatButton = new JButton("Book Seat");
        bookSeatButton.setBackground(new Color(0x113C94));
        bookSeatButton.setForeground(Color.WHITE);
        bookSeatButton.setFocusPainted(false);
        bookSeatButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        bookSeatButton.addActionListener(e -> {
            BookSeatView bookSeatView = new BookSeatView(userDao, movie.getMovie_id());
            new BookSeatController(bookSeatView, userDao, userId, movie.getMovie_id());
            bookSeatView.setVisible(true);
            if (parentFrame != null) parentFrame.setVisible(false);
        });
        add(bookSeatButton, BorderLayout.SOUTH);
    }

    private JLabel bold(String txt) {
        JLabel l = new JLabel(txt, SwingConstants.CENTER);
        l.setFont(new Font("Segoe UI", Font.BOLD, 15));
        return l;
    }
}
