package movieticket.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PaymentView extends JFrame {

    public JTextField amountField, cardNumberField,
                      cardHolderField, expiryField;
    public JPasswordField cvvField;
    public JButton payButton;

    public PaymentView(int userId, Object dao,        // dao kept only to match ctor
                       int movieId, String seats) {   // ─── NEW (extra params) ───
        initUI();
    }

    private void initUI() {

        setTitle("Payment");
        setSize(420, 340);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(21,24,30));
        setLayout(new BorderLayout());

        JPanel p = new JPanel(new GridBagLayout());
        p.setOpaque(false);
        p.setBorder(new EmptyBorder(25,30,15,30));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(10,0,10,0);
        g.fill = GridBagConstraints.HORIZONTAL;

        g.gridx = 0; g.gridy = 0; p.add(label("Amount"),g);
        g.gridx = 1; amountField = field(); p.add(amountField,g);

        g.gridy++; g.gridx=0; p.add(label("Card Number"),g);
        g.gridx=1; cardNumberField = field(); p.add(cardNumberField,g);

        g.gridy++; g.gridx=0; p.add(label("Card Holder"),g);
        g.gridx=1; cardHolderField = field(); p.add(cardHolderField,g);

        g.gridy++; g.gridx=0; p.add(label("Expiry (MM/YY)"),g);
        g.gridx=1; expiryField = field(); p.add(expiryField,g);

        g.gridy++; g.gridx=0; p.add(label("CVV"),g);
        g.gridx=1; cvvField = new JPasswordField(4); stylise(cvvField); p.add(cvvField,g);

        g.gridy++; g.gridx=0; g.gridwidth=2; g.anchor=GridBagConstraints.CENTER;
        payButton = new JButton("PAY NOW");
        stylePrimary(payButton);
        p.add(payButton,g);

        add(p,BorderLayout.CENTER);
    }

    private JLabel label(String t){ JLabel l=new JLabel(t); l.setForeground(Color.WHITE); return l; }
    private JTextField field(){ JTextField f=new JTextField(16); stylise(f); return f; }

    private void stylise(JTextField f){
        f.setForeground(Color.WHITE);
        f.setBackground(new Color(44,47,55));
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60,60,70)),
                new EmptyBorder(6,8,6,8)));
    }
    private void stylePrimary(JButton b){
        b.setForeground(Color.white);
        b.setBackground(new Color(0x0E63C4));
        b.setFont(new Font("Segoe UI",Font.BOLD,16));
        b.setBorder(BorderFactory.createEmptyBorder(10,40,10,40));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
}
