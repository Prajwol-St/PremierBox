package movieticket.model;

public class MoviesData {
    private int movie_id;
    private String title;
    private String genre;
    private String duration;
    private String date;
    private byte[] posterPath;
    
    public MoviesData(String title, String genre, String duration, String date, byte[] posterPath) {
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.date = date;
        this.posterPath = posterPath;
    }
    
    public MoviesData(int movie_id, String title, String genre, String duration, String date, byte[] posterPath) {
        this.movie_id = movie_id;
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.date = date;
        this.posterPath = posterPath;
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
}