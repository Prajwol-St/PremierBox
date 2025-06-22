/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package movieticket.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import movieticket.dao.UserDao;                       // ─── NEW
import javax.swing.DefaultListModel;                  // ─── NEW
import java.util.List; 
import movieticket.view.components.MovieCard;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import movieticket.dao.CRUDAdminDAO;
import movieticket.model.MoviesData;
import java.sql.SQLException;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JList;

import javax.swing.JScrollPane;
// ─── NEW
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

     // put in the import section




/**
 *
 * @author Hp
 */
public class UserDashboardView extends javax.swing.JFrame {

    /**
     * Creates new form UserDashboardView
     */
       private final javax.swing.JScrollPane availableMoviesScrollPane;
     private final JPanel          trendingStrip = new JPanel();
     private final DefaultListModel<String> notiModel = new DefaultListModel<>();
     private final UserDao         userDao       = new UserDao();
     private final int userId = 0;  
     // ─── NEW  : auto-scroll helper
     private javax.swing.Timer trendTimer;
     private javax.swing.JScrollPane trendScroll;
     // ─── NEW : constant height for the notification band
    private static final int NOTI_HEIGHT = 80;



    
      public UserDashboardView() {
        
        
        initComponents();
                /* refresh once the frame is visible */                       // ─── NEW
        addWindowListener(new WindowAdapter(){                         // ─── NEW
            @Override public void windowOpened(WindowEvent e){         // ─── NEW
                loadTrending(); loadNotifications();                   // ─── NEW
            }});                                                       // ─── NEW

                // ─── NEW – build “Trending” + “Notifications” card
        
        logoutButton   .setBackground(new Color(204,0,0));
        for (var b : List.of(userAvailableMoviesButton,userAvailableMoviesButton,logoutButton)){
            b.setForeground(Color.WHITE);
            b.setFocusPainted(false);
        }

        /* ---------- build DASHBOARD content ---------- */
        UserDashboard.removeAll();
        UserDashboard.setLayout(
        new javax.swing.BoxLayout(UserDashboard, javax.swing.BoxLayout.Y_AXIS));   // ─── NEW

        UserDashboard.setBackground(new Color(30,34,40));               // ─── NEW ───

        // title helper
        java.util.function.Function<String,JLabel> h =
            s -> { JLabel l=new JLabel(s); l.setFont(new Font("Segoe UI",1,18));
                   l.setForeground(Color.WHITE); return l; };

        /* 1. TRENDING STRIP (top) */
        trendingStrip.setLayout(new FlowLayout(FlowLayout.LEFT,15,15));
        trendingStrip.setOpaque(false);
         JScrollPane trendScroll = new JScrollPane(trendingStrip,
                 JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                 JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        trendScroll.setBorder(null);
        trendScroll.getViewport().setOpaque(false);

        JPanel trendingBlock = new JPanel(new BorderLayout());
        trendingBlock.setOpaque(false);
        trendingBlock.add(h.apply("Trending this week"), BorderLayout.NORTH);
        trendingBlock.add(trendScroll,                     BorderLayout.CENTER);
        trendingBlock.setPreferredSize(new Dimension(0,330));           // ─── NEW ───
       

        /* 2. NOTIFICATIONS (bottom) */
        JList<String> notiList = new JList<>(notiModel);
                // in UserDashboardView constructor, after you create notiList
            notiList.addListSelectionListener(e -> {
         if (!e.getValueIsAdjusting()) {
             String msg = notiList.getSelectedValue();
             if (msg != null && !msg.equals("No new notifications")) {
                 userDao.markNotificationRead(userId, msg);   // << calls helper
                 loadNotifications();                         // refresh list
             }
         }
     });


        notiList.setBackground(new Color(34,34,40));
        notiList.setForeground(Color.WHITE);
        JScrollPane notiScroll = new JScrollPane(notiList);
        notiScroll.setBorder(null);
                // ─── NEW : lock the block at 120 px
        notiScroll.setPreferredSize(new Dimension(0, NOTI_HEIGHT));
        notiScroll.setMaximumSize  (new Dimension(Integer.MAX_VALUE, NOTI_HEIGHT));

                 

        JPanel notiBlock = new JPanel(new BorderLayout());
        notiBlock.setOpaque(false);
        notiBlock.add(h.apply("Notifications"), BorderLayout.NORTH);
        notiBlock.add(notiScroll,               BorderLayout.CENTER);
         UserDashboard.add(trendingBlock);                                      // ─── NEW
        UserDashboard.add(Box.createVerticalStrut(15));  // optional gap       // ─── NEW
        UserDashboard.add(notiBlock);                                           // ─── NEW

        

        /* ---------- listeners ---------- */
        userDashboardButton.addActionListener(e -> {
            ((CardLayout)UserDashboardCardPanel.getLayout()).show(UserDashboardCardPanel,"UserDashboard");
            loadTrending(); loadNotifications();                        // ─── NEW ───
        });
        userAvailableMoviesButton.addActionListener(e -> {
            ((CardLayout)UserDashboardCardPanel.getLayout()).show(UserDashboardCardPanel,"AvailableMovies");
            displayAvailableMovies();
        });

       
    


        AvailableMovies = new JPanel();
        AvailableMovies.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));

        availableMoviesScrollPane = new JScrollPane(AvailableMovies);
        availableMoviesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        availableMoviesScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        availableMoviesScrollPane.setBorder(null);
        
        UserDashboardCardPanel.add(UserDashboard, "UserDashboard");
        UserDashboardCardPanel.add(availableMoviesScrollPane, "AvailableMovies");

        /* --- start-up card ------------------------------------ */          // NEW
        ((CardLayout) UserDashboardCardPanel.getLayout())                     // NEW
                .show(UserDashboardCardPanel, "UserDashboard");               // NEW


    }
     private void displayAvailableMovies() {
        AvailableMovies.removeAll(); // Clear previous content
        AvailableMovies.setLayout(new java.awt.GridBagLayout());

        CRUDAdminDAO dao = new CRUDAdminDAO();
    try {
        java.util.List<MoviesData> movies = dao.getAllMoviesWithImages();
        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        gbc.insets = new java.awt.Insets(20, 30, 20, 30); // Top, Left, Bottom, Right spacing
        gbc.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gbc.fill = java.awt.GridBagConstraints.NONE;

        int col = 0;
        int row = 0;

        for (MoviesData movie : movies) {
            MovieCard card = new MovieCard(movie);

            gbc.gridx = col;
            gbc.gridy = row;

            AvailableMovies.add(card, gbc);

            col++;
            if (col >= 2) { 
                col = 0;
                row++;
            }
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Failed to load movies.", "Error", JOptionPane.ERROR_MESSAGE);
    }
    AvailableMovies.revalidate();
    AvailableMovies.repaint();
}
   // ─── NEW : fetch 10 best-selling posters this week
        private void loadTrending() {
            trendingStrip.removeAll();
            try {
                List<MoviesData> list = new CRUDAdminDAO().getTrendingMovies();
                for (MoviesData m : list) {
                    trendingStrip.add(new MovieCard(m));   // 1-arg ctor is fine
                }
            } catch (SQLException ex) {
                trendingStrip.add(new JLabel("DB error"));
            }
            trendingStrip.revalidate();
            trendingStrip.repaint();
        }

        // ─── NEW : unread notifications
        private void loadNotifications() {
            notiModel.clear();
            List<String> rows = userDao.getUnreadNotifications(userId);
            if (rows.isEmpty())
                notiModel.addElement("No new notifications");
            else
                rows.forEach(notiModel::addElement);
        }




    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel1 = new javax.swing.JPanel();
        userDashboardButton = new javax.swing.JButton();
        userAvailableMoviesButton = new javax.swing.JButton();
        logoutButton = new javax.swing.JButton();
        UserDashboardCardPanel = new javax.swing.JPanel();
        UserDashboard = new javax.swing.JPanel();
        AvailableMovies = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mainPanel1.setBackground(new java.awt.Color(163, 25, 25));
        mainPanel1.setForeground(new java.awt.Color(255, 255, 255));

        userDashboardButton.setBackground(new java.awt.Color(0, 0, 0));
        userDashboardButton.setFont(new java.awt.Font("Helvetica Neue", 0, 16)); // NOI18N
        userDashboardButton.setForeground(new java.awt.Color(255, 255, 255));
        userDashboardButton.setText("Dashboard");
        userDashboardButton.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(25, 25, 112), 1, true));
        userDashboardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userDashboardButtonActionPerformed(evt);
            }
        });

        userAvailableMoviesButton.setBackground(new java.awt.Color(0, 0, 0));
        userAvailableMoviesButton.setFont(new java.awt.Font("Helvetica Neue", 0, 16)); // NOI18N
        userAvailableMoviesButton.setForeground(new java.awt.Color(255, 255, 255));
        userAvailableMoviesButton.setText("Available Movies");
        userAvailableMoviesButton.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(25, 25, 112), 1, true));
        userAvailableMoviesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userAvailableMoviesButtonActionPerformed(evt);
            }
        });

        logoutButton.setBackground(new java.awt.Color(204, 0, 0));
        logoutButton.setFont(new java.awt.Font("Helvetica Neue", 0, 16)); // NOI18N
        logoutButton.setForeground(new java.awt.Color(255, 255, 255));
        logoutButton.setText("Log Out");
        logoutButton.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(25, 25, 112), 1, true));
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mainPanel1Layout = new javax.swing.GroupLayout(mainPanel1);
        mainPanel1.setLayout(mainPanel1Layout);
        mainPanel1Layout.setHorizontalGroup(
            mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(userDashboardButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(userAvailableMoviesButton, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                    .addComponent(logoutButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        mainPanel1Layout.setVerticalGroup(
            mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanel1Layout.createSequentialGroup()
                .addGap(220, 220, 220)
                .addComponent(userDashboardButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(userAvailableMoviesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 246, Short.MAX_VALUE)
                .addComponent(logoutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        UserDashboardCardPanel.setLayout(new java.awt.CardLayout());

        javax.swing.GroupLayout UserDashboardLayout = new javax.swing.GroupLayout(UserDashboard);
        UserDashboard.setLayout(UserDashboardLayout);
        UserDashboardLayout.setHorizontalGroup(
            UserDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 651, Short.MAX_VALUE)
        );
        UserDashboardLayout.setVerticalGroup(
            UserDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 590, Short.MAX_VALUE)
        );

        UserDashboardCardPanel.add(UserDashboard, "UserDashboard");

        jLabel2.setText("jLabel2");

        javax.swing.GroupLayout AvailableMoviesLayout = new javax.swing.GroupLayout(AvailableMovies);
        AvailableMovies.setLayout(AvailableMoviesLayout);
        AvailableMoviesLayout.setHorizontalGroup(
            AvailableMoviesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 651, Short.MAX_VALUE)
            .addGroup(AvailableMoviesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(AvailableMoviesLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel2)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        AvailableMoviesLayout.setVerticalGroup(
            AvailableMoviesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 590, Short.MAX_VALUE)
            .addGroup(AvailableMoviesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(AvailableMoviesLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel2)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        UserDashboardCardPanel.add(AvailableMovies, "AvailableMovies");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(mainPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 661, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(184, Short.MAX_VALUE)
                    .addComponent(UserDashboardCardPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 651, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(mainPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(UserDashboardCardPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void userDashboardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userDashboardButtonActionPerformed
        // TODO add your handling code here:
         CardLayout cl = (CardLayout) UserDashboardCardPanel.getLayout();
        cl.show(UserDashboardCardPanel, "UserDashboard");

        // ─── NEW : refresh panels
        loadTrending();
        loadNotifications();
    }//GEN-LAST:event_userDashboardButtonActionPerformed

    private void userAvailableMoviesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userAvailableMoviesButtonActionPerformed
        // TODO add your handling code here:
        CardLayout cl = (CardLayout) UserDashboardCardPanel.getLayout();
        cl.show(UserDashboardCardPanel, "AvailableMovies");
        displayAvailableMovies();
    }//GEN-LAST:event_userAvailableMoviesButtonActionPerformed

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_logoutButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UserDashboardView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserDashboardView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserDashboardView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserDashboardView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
         
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserDashboardView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AvailableMovies;
    private javax.swing.JPanel UserDashboard;
    private javax.swing.JPanel UserDashboardCardPanel;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JButton logoutButton;
    private javax.swing.JPanel mainPanel1;
    private javax.swing.JButton userAvailableMoviesButton;
    private javax.swing.JButton userDashboardButton;
    // End of variables declaration//GEN-END:variables

  
     public JPanel getCardPanel(){
        return UserDashboardCardPanel;
    }
    
    public JButton getDashboard(){
        return userDashboardButton;
    }
  
  public JButton getAvailableMovies(){
        return userAvailableMoviesButton;
    }
  
   public void logoutMovieListener(ActionListener listener){
        logoutButton.addActionListener(listener);
    }
  

}
