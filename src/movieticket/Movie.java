package movieticket.model;

public class Movie {
    private String title;
    private String genre;
    private String duration;
    private String date;
    private String posterPath;

    public Movie(String title, String genre, String duration, String date, String posterPath) {
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.date = date;
        this.posterPath = posterPath;
    }

    public String getTitle() { return title; }
    public String getGenre() { return genre; }
    public String getDuration() { return duration; }
    public String getDate() { return date; }
    public String getPosterPath() { return posterPath; }
}
