
package movieticket.view;


import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;


public class LoginView extends javax.swing.JFrame {

     
    /**
     * Creates new form LoginView
     */
    public LoginView() {
        initComponents();
          setTitle("Login Screen");
           setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    private void init(){
     
    }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        LoginTitle = new javax.swing.JLabel();
        EmailIcon = new javax.swing.JLabel();
        txtEmailField = new javax.swing.JTextField();
        PasswordIcon = new javax.swing.JLabel();
        txtPasswordField = new javax.swing.JPasswordField();
        LogInBtn = new javax.swing.JButton();
        AppLogo = new javax.swing.JLabel();
        AppName = new javax.swing.JLabel();
        LogInSubtitle = new javax.swing.JLabel();
        ForgotPassword = new javax.swing.JLabel();
        ShowPasswordBtn = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        SignUpHead = new javax.swing.JLabel();
        SignUpsub1 = new javax.swing.JLabel();
        SignUpsub2 = new javax.swing.JLabel();
        SignUpBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(227, 227, 227));
        jPanel1.setLayout(null);

        LoginTitle.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        LoginTitle.setForeground(new java.awt.Color(0, 128, 102));
        LoginTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LoginTitle.setText("Log In");
        jPanel1.add(LoginTitle);
        LoginTitle.setBounds(150, 80, 120, 40);

        EmailIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/email.png"))); // NOI18N
        jPanel1.add(EmailIcon);
        EmailIcon.setBounds(40, 170, 24, 30);

        txtEmailField.setForeground(new java.awt.Color(153, 153, 153));
        txtEmailField.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtEmailField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtEmailFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtEmailFieldFocusLost(evt);
            }
        });
        txtEmailField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailFieldActionPerformed(evt);
            }
        });
        jPanel1.add(txtEmailField);
        txtEmailField.setBounds(70, 170, 280, 30);

        PasswordIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/lock.png"))); // NOI18N
        jPanel1.add(PasswordIcon);
        PasswordIcon.setBounds(40, 210, 24, 30);

        txtPasswordField.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtPasswordField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPasswordFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPasswordFieldFocusLost(evt);
            }
        });
        txtPasswordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswordFieldActionPerformed(evt);
            }
        });
        jPanel1.add(txtPasswordField);
        txtPasswordField.setBounds(70, 210, 280, 30);

        LogInBtn.setBackground(new java.awt.Color(0, 153, 102));
        LogInBtn.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        LogInBtn.setForeground(new java.awt.Color(255, 255, 255));
        LogInBtn.setText("Log In");
        LogInBtn.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        LogInBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogInBtnActionPerformed(evt);
            }
        });
        jPanel1.add(LogInBtn);
        LogInBtn.setBounds(160, 290, 100, 40);

        AppLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/clap.png"))); // NOI18N
        jPanel1.add(AppLogo);
        AppLogo.setBounds(0, 0, 64, 64);

        AppName.setFont(new java.awt.Font("Segoe UI Semibold", 1, 23)); // NOI18N
        AppName.setForeground(new java.awt.Color(255, 51, 51));
        AppName.setText("PremierBox");
        AppName.setToolTipText("");
        AppName.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(AppName);
        AppName.setBounds(70, 10, 140, 40);

        LogInSubtitle.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        LogInSubtitle.setForeground(new java.awt.Color(102, 102, 102));
        LogInSubtitle.setText("Enter your credentials");
        jPanel1.add(LogInSubtitle);
        LogInSubtitle.setBounds(140, 130, 150, 20);

        ForgotPassword.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        ForgotPassword.setForeground(new java.awt.Color(255, 0, 0));
        ForgotPassword.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ForgotPassword.setText("Forgot Password?");
        ForgotPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ForgotPasswordMouseClicked(evt);
            }
        });
        jPanel1.add(ForgotPassword);
        ForgotPassword.setBounds(270, 250, 100, 16);

        ShowPasswordBtn.setText("Show");
        ShowPasswordBtn.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.black, java.awt.Color.black));
        ShowPasswordBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ShowPasswordBtnActionPerformed(evt);
            }
        });
        jPanel1.add(ShowPasswordBtn);
        ShowPasswordBtn.setBounds(360, 210, 50, 30);

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 420, 460));

        jPanel2.setBackground(new java.awt.Color(0, 153, 102));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(null);

        SignUpHead.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        SignUpHead.setForeground(new java.awt.Color(255, 255, 255));
        SignUpHead.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        SignUpHead.setText("Don't have an account?");
        jPanel2.add(SignUpHead);
        SignUpHead.setBounds(40, 100, 290, 60);

        SignUpsub1.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        SignUpsub1.setForeground(new java.awt.Color(204, 204, 204));
        SignUpsub1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        SignUpsub1.setText("Enter your personal details");
        jPanel2.add(SignUpsub1);
        SignUpsub1.setBounds(80, 170, 190, 20);

        SignUpsub2.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        SignUpsub2.setForeground(new java.awt.Color(204, 204, 204));
        SignUpsub2.setText("and start your journey with us.");
        jPanel2.add(SignUpsub2);
        SignUpsub2.setBounds(70, 200, 210, 16);

        SignUpBtn.setBackground(new java.awt.Color(0, 153, 102));
        SignUpBtn.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        SignUpBtn.setForeground(new java.awt.Color(255, 255, 255));
        SignUpBtn.setText("Sign Up");
        SignUpBtn.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.white));
        SignUpBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SignUpBtnActionPerformed(evt);
            }
        });
        jPanel2.add(SignUpBtn);
        SignUpBtn.setBounds(130, 250, 100, 40);

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 0, 350, 460));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtEmailFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailFieldActionPerformed

    private void txtEmailFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEmailFieldFocusGained
         if (txtEmailField.getText().equals("Email")) {
        txtEmailField.setText("");
        txtEmailField.setForeground(Color.BLACK);
    }        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailFieldFocusGained

    private void txtEmailFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEmailFieldFocusLost
     if (txtEmailField.getText().trim().isEmpty()) {
        txtEmailField.setText("Email");
        txtEmailField.setForeground(Color.GRAY);
    }        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailFieldFocusLost

    private void txtPasswordFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPasswordFieldActionPerformed

    private void LogInBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogInBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LogInBtnActionPerformed

    private void SignUpBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SignUpBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SignUpBtnActionPerformed

    private void ForgotPasswordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ForgotPasswordMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_ForgotPasswordMouseClicked

    private void ShowPasswordBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ShowPasswordBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ShowPasswordBtnActionPerformed

    private void txtPasswordFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPasswordFieldFocusGained
            if (txtPasswordField.getText().equals("Password")) {
        txtPasswordField.setText("");
        txtPasswordField.setForeground(Color.BLACK);
    }  // TODO add your handling code here:
    }//GEN-LAST:event_txtPasswordFieldFocusGained

    private void txtPasswordFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPasswordFieldFocusLost
           if (txtPasswordField.getText().trim().isEmpty()) {
        txtPasswordField.setText("Password");
        txtPasswordField.setForeground(Color.GRAY);
    }   // TODO add your handling code here:
    }//GEN-LAST:event_txtPasswordFieldFocusLost

    

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
            java.util.logging.Logger.getLogger(LoginView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
         try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LoginView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AppLogo;
    private javax.swing.JLabel AppName;
    private javax.swing.JLabel EmailIcon;
    private javax.swing.JLabel ForgotPassword;
    private javax.swing.JButton LogInBtn;
    private javax.swing.JLabel LogInSubtitle;
    private javax.swing.JLabel LoginTitle;
    private javax.swing.JLabel PasswordIcon;
    private javax.swing.JButton ShowPasswordBtn;
    private javax.swing.JButton SignUpBtn;
    private javax.swing.JLabel SignUpHead;
    private javax.swing.JLabel SignUpsub1;
    private javax.swing.JLabel SignUpsub2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField txtEmailField;
    private javax.swing.JPasswordField txtPasswordField;
    // End of variables declaration//GEN-END:variables

  public javax.swing.JTextField getEmailTextField(){
        return txtEmailField;
    }
     public javax.swing.JPasswordField getPasswordField(){
        return txtPasswordField;
    }
    public void addRegisterListener(MouseListener listener){
        SignUpBtn.addMouseListener(listener);

    }
    public void addLoginUserListener(ActionListener listener){
        LogInBtn.addActionListener(listener);
    }

    public void togglePasswordField(boolean visible) {
        txtPasswordField.setEchoChar(visible ? (char) 0 : '*');
        ShowPasswordBtn.setText(visible ? "Hide" : "Show");
    }
    public void showPasswordButtonListener(ActionListener listener){
        ShowPasswordBtn.addActionListener(listener);
    }
    
    public void forgotPassword(MouseListener listener){
        ForgotPassword.addMouseListener(listener);
    }




}
