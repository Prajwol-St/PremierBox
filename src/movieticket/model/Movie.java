package movieticket.model;

public class Movie {
    private int movie_id;
    private String title;
    private String genre;
    private String duration;
    private String datee;      // Match the DB column name
    private byte[] poster;     // Store image as bytes

    public Movie(int movie_id, String title, String genre, String duration, String datee, byte[] poster) {
        this.movie_id = movie_id;
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.datee = datee;
        this.poster = poster;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public String getDuration() {
        return duration;
    }

    public String getDatee() {
        return datee;
    }

    public byte[] getPoster() {
        return poster;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setDatee(String datee) {
        this.datee = datee;
    }

    public void setPoster(byte[] poster) {
        this.poster = poster;
    }
}
