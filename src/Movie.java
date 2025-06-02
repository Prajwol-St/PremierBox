public class Movie {
    private String title;
    private String genre;
    private int duration;
    private String description;

    public Movie(String title, String genre, int duration, String description) {
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public int getDuration() {
        return duration;
    }

    public String getDescription() {
        return description;
    }
}
