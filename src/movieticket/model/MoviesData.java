package movieticket.model;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.ArrayList; // Import ArrayList
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class MoviesData {
    private int movie_id;
    private String title;
    private String genre;
    private String duration;
    private String date;
    private byte[] posterPath;
    
     /* NEW: all show-times in one list */
    private List<String> showTimes;
    
    public MoviesData(String title, String genre, String duration, String date, byte[] posterPath, List<String> times) {
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.date = date;
        this.posterPath = posterPath;
        this.showTimes = times != null ? times : new ArrayList<>(); // Initialize to empty list if null
    }
    
    public MoviesData(int movie_id, String title, String genre, String duration, String date, byte[] posterPath) {
        this.movie_id = movie_id;
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.date = date;
        this.posterPath = posterPath;
        this.showTimes = new ArrayList<>(); // Initialize to an empty list
    }

    // New constructor to include showTimes for cases where movie_id is known
    public MoviesData(int movie_id, String title, String genre, String duration, String date, byte[] posterPath, List<String> times) {
        this.movie_id = movie_id;
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.date = date;
        this.posterPath = posterPath;
        this.showTimes = times != null ? times : new ArrayList<>(); // Initialize to empty list if null
    }
    
    public int getMovie_id() {
        return movie_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public byte[] getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(byte[] posterPath) {
        this.posterPath = posterPath;
    }
    
    public List<String> getShowTimes(){
        return showTimes != null ? showTimes : new ArrayList<>(); // Always return a list, never null
    }

    public void setShowTimes(List<String> st){
        this.showTimes = st != null ? st : new ArrayList<>(); // Ensure nulls are converted to empty list
    }
    
    
    public ImageIcon getScaledPoster(int w, int h) {            // NEW
    if (posterPath == null) return null;
    try (ByteArrayInputStream in = new ByteArrayInputStream(posterPath)) {
        BufferedImage img = ImageIO.read(in);
        if (img == null) return null;
        Image scaled = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    } catch (Exception ex) {
        return null;
    }
}
}