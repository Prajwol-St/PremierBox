package movieticket.view.components;

import movieticket.dao.UserDao;
import movieticket.model.MoviesData;
import movieticket.view.BookSeatView;
import movieticket.controller.BookSeatController;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class MovieCard extends JPanel {

    /* ------------------------------------------------------------------ */
    private final UserDao userDao;
    private final int     userId;
    private final JFrame  parent;

    public MovieCard(MoviesData movie, JFrame owner, UserDao dao, int uid) {
        this.userDao = (dao != null) ? dao : new UserDao();          // ─── NEW ───
        this.userId  = uid;
        this.parent  = owner;
        buildUI(movie);
    }
    public MovieCard(MoviesData movie) { this(movie, null, null, -1); }

    /* ------------------------------------------------------------------ */
    private void buildUI(MoviesData movie) {

        setPreferredSize(new Dimension(240, 370));
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setOpaque(false);

        /* poster */
        JLabel poster = new JLabel();
        poster.setHorizontalAlignment(SwingConstants.CENTER);
        poster.setIcon(scalePoster(movie.getPosterPath(), 200, 240));
        poster.setBorder(new DropShadowBorder());
        add(poster, BorderLayout.NORTH);

        /* glass info panel */
        JPanel info = new GlassPanel();
        info.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        info.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0; gc.anchor = GridBagConstraints.WEST; gc.weightx = 1;

        gc.gridy = 0; info.add(bold(movie.getTitle()), gc);
        gc.gridy = 1; info.add(light("Genre: " + movie.getGenre()), gc);
        gc.gridy = 2; info.add(light("Date : " + movie.getDate()),  gc);

        /* pill bar */
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 4));
        bar.setOpaque(false);

        DateTimeFormatter HHMM = DateTimeFormatter.ofPattern("HH:mm");
        List<String> times = Optional.ofNullable(movie.getShowTimes())
                                     .orElse(List.of())
                                     .stream()
                                     .map(t -> t.length()==8 ? t.substring(0,5) : t)
                                     .map(t -> { try { return java.time.LocalTime.parse(t).format(HHMM);}
                                                 catch(Exception e){ return t; }})
                                     .toList();

        for (String t : times) {
            JButton pill = createPill(t);

            /* open seat-selection on click */
            pill.addActionListener(e -> {                               // ─── NEW ───
                BookSeatView seat = new BookSeatView(userDao, movie.getMovie_id()); // ─── NEW ───
                seat.setTitle(movie.getTitle() + " – " + t);                          // ─── NEW ───
                new BookSeatController(seat, userDao, userId);                        // ─── NEW ───
                seat.setVisible(true);                                                // ─── NEW ───
                if (parent != null) parent.setVisible(false);                         // ─── NEW ───
            });                                                                       // ─── NEW ───

            bar.add(pill);
        }
        gc.gridy = 3; info.add(bar, gc);
        add(info, BorderLayout.CENTER);
        /* old “Book Seat” button removed */
    }

    /* helpers ---------------------------------------------------------- */
    private JLabel bold(String s){ JLabel l=new JLabel(s); l.setFont(getFont().deriveFont(Font.BOLD,15f)); l.setForeground(Color.WHITE); return l; }
    private JLabel light(String s){ JLabel l=new JLabel(s); l.setForeground(new Color(200,200,200)); return l; }

    private JButton createPill(String txt){
        JButton b = new JButton(txt);
        b.setFont(getFont().deriveFont(Font.BOLD, 12f));
        b.setForeground(Color.WHITE);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setBorder(BorderFactory.createEmptyBorder(4, 14, 4, 14));
        b.setContentAreaFilled(false);
        b.setFocusPainted(false);

        b.setUI(new BasicButtonUI(){
            @Override public void paint(Graphics g, JComponent c){
                Graphics2D g2=(Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                AbstractButton btn = (AbstractButton) c;               // ─── NEW ───
                Color fill = btn.getModel().isArmed()                  // ─── NEW ───
                        ? new Color(0x0B4FA3) : new Color(0x0E63C4);
                g2.setColor(fill);
                g2.fillRoundRect(0,0,c.getWidth(),c.getHeight(),20,20);
                g2.dispose();
                super.paint(g, c);
            }
        });
        return b;
    }

    private static ImageIcon scalePoster(byte[] p,int w,int h){
        if(p==null) return null;
        try(var in=new ByteArrayInputStream(p)){
            BufferedImage img=ImageIO.read(in);
            if(img==null) return null;
            return new ImageIcon(img.getScaledInstance(w,h,Image.SCALE_SMOOTH));
        }catch(Exception e){ return null; }
    }

    /* glass background */
    private static class GlassPanel extends JPanel{
        GlassPanel(){ setOpaque(false); }
        @Override protected void paintComponent(Graphics g){
            Graphics2D g2=(Graphics2D)g.create();
            g2.setComposite(AlphaComposite.SrcOver.derive(.8f));
            g2.setPaint(new GradientPaint(0,0,new Color(34,34,34,230),
                                          0,getHeight(),new Color(34,34,34,150)));
            g2.fillRoundRect(0,0,getWidth(),getHeight(),20,20);
            g2.dispose(); super.paintComponent(g);
        }
    }
    private static class DropShadowBorder extends AbstractBorder{
        @Override public void paintBorder(Component c,Graphics g,int x,int y,int w,int h){
            Graphics2D g2=(Graphics2D)g.create();
            g2.setColor(new Color(0,0,0,60));
            g2.fillRoundRect(x+2,y+2,w-4,h-4,18,18);
            g2.dispose();
        }
    }
}
