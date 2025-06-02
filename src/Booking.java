public class Booking {
    private User user;
    private Movie movie;

    public Booking(User user, Movie movie) {
        this.user = user;
        this.movie = movie;
    }

    public User getUser() {
        return user;
    }

    public Movie getMovie() {
        return movie;
    }
}