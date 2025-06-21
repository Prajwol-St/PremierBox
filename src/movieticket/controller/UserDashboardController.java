package movieticket.controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import movieticket.view.EntryView;
import movieticket.view.UserDashboardView;

/**
 * @author Hp
 */
public class UserDashboardController {

    private final UserDashboardView userdashboardView;
    private final CardLayout cards;

    public UserDashboardController(UserDashboardView userdashboardView) {
        this.userdashboardView = userdashboardView;
        this.cards = (CardLayout) userdashboardView.getCardPanel().getLayout();
        userdashboardView.logoutMovieListener(new LogoutMovieListener());
        wire();
    }

    // Wire up navigation buttons to switch cards
    private void wire() {
        map(userdashboardView.getDashboardButton(), "Dashboard");
        map(userdashboardView.getAvailableMoviesButton(), "AvailableMovies");
    }

    // Helper to map a button to a card
    private void map(JButton b, String card) {
        b.addActionListener(e -> cards.show(userdashboardView.getCardPanel(), card));
    }

    // Logout logic
    public class LogoutMovieListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int choice = JOptionPane.showConfirmDialog(
                userdashboardView,
                "Are you sure you want to log out?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );
            if (choice == JOptionPane.YES_OPTION) {
                EntryView entryView = new EntryView();
                EntryController entryController = new EntryController(entryView);
                entryController.open();
                close();
            }
        }
    }

    public void open() {
        this.userdashboardView.setVisible(true);
    }

    public void close() {
        this.userdashboardView.dispose();
    }
}
