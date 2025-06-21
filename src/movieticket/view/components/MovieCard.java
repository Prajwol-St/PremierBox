package movieticket.view.components;

import javax.swing.*;
import java.awt.*;
import movieticket.controller.BookSeatController;
import movieticket.dao.UserDao;
import movieticket.model.MoviesData;
import movieticket.view.BookSeatView;

public class MovieCard extends JPanel {
    private UserDao userDao;
    private int userId; // <-- add this field

    public MovieCard(MoviesData movie, JFrame parentFrame, UserDao userDao, int userId) {
        this.userDao = userDao;
        this.userId = userId;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        setPreferredSize(new Dimension(200, 300));

        JLabel titleLabel = new JLabel(movie.getTitle(), SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(titleLabel, BorderLayout.NORTH);

        JButton bookButton = new JButton("Book Now");
        add(bookButton, BorderLayout.SOUTH);

        bookButton.addActionListener(e -> {
            BookSeatView bookSeatView = new BookSeatView(userDao, movie.getMovie_id());
            new BookSeatController(bookSeatView, userDao, userId, movie.getMovie_id());
            bookSeatView.setVisible(true);
            // Optionally: parentFrame.setVisible(false);
        });
    }
}
