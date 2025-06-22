package movieticket.view.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import movieticket.dao.UserDao;
import movieticket.model.UserData;
import org.mindrot.jbcrypt.BCrypt;

public class UserProfilePanel extends JPanel {
    private final UserData user;
    private final JTextField nameField;
    private final JTextField emailField;
    private final JPasswordField currentPasswordField;
    private final JPasswordField newPasswordField;
    private final JButton saveButton;

    public UserProfilePanel(UserData user) {
        this.user = user;

        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        card.setBackground(Color.WHITE);
        card.setMaximumSize(new Dimension(500, 350));
        card.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("Edit Profile");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        nameField = new JTextField(user.getName());
        nameField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        nameField.setAlignmentX(Component.LEFT_ALIGNMENT);

        emailField = new JTextField(user.getEmail());
        emailField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        emailField.setEnabled(false);
        emailField.setAlignmentX(Component.LEFT_ALIGNMENT);

        currentPasswordField = new JPasswordField();
        currentPasswordField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        currentPasswordField.setAlignmentX(Component.LEFT_ALIGNMENT);
        currentPasswordField.setToolTipText("Enter your current password to confirm changes");

        newPasswordField = new JPasswordField();
        newPasswordField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        newPasswordField.setAlignmentX(Component.LEFT_ALIGNMENT);
        newPasswordField.setToolTipText("Leave blank to keep your current password");

        saveButton = new JButton("Save Changes");
        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        saveButton.setBackground(new Color(0, 123, 255));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveUserChanges();
            }
        });

        card.add(titleLabel);
        card.add(Box.createVerticalStrut(15));
        card.add(new JLabel("Name:"));
        card.add(nameField);
        card.add(Box.createVerticalStrut(10));
        card.add(new JLabel("Email:"));
        card.add(emailField);
        card.add(Box.createVerticalStrut(10));
        card.add(new JLabel("Current Password (required):"));
        card.add(currentPasswordField);
        card.add(Box.createVerticalStrut(10));
        card.add(new JLabel("New Password (optional):"));
        card.add(newPasswordField);
        card.add(Box.createVerticalStrut(20));
        card.add(saveButton);

        JPanel centerWrapper = new JPanel();
        centerWrapper.setLayout(new BoxLayout(centerWrapper, BoxLayout.Y_AXIS));
        centerWrapper.setBackground(new Color(245, 245, 245));
        centerWrapper.add(Box.createVerticalGlue());
        centerWrapper.add(card);
        centerWrapper.add(Box.createVerticalGlue());

        add(centerWrapper, BorderLayout.CENTER);
    }

    private void saveUserChanges() {
        String newName = nameField.getText().trim();
        String currentPassword = new String(currentPasswordField.getPassword()).trim();
        String newPassword = new String(newPasswordField.getPassword()).trim();

        if (newName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (currentPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your current password to confirm changes.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verify current password using stored hash
        if (!BCrypt.checkpw(currentPassword, user.getPassword())) {
            JOptionPane.showMessageDialog(this, "Current password is incorrect.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Proceed with update
        UserDao userDao = new UserDao();
        boolean success = userDao.updateUser(user.getId(), newName, newPassword.isEmpty() ? null : newPassword);

        if (success) {
            JOptionPane.showMessageDialog(this, "Profile updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            user.setName(newName);
            currentPasswordField.setText("");
            newPasswordField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update profile.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
