package movieticket.model;

public class SeatBooking {
    private int userId;
    private int movieId;
    private String seatNumbers; // e.g., "A1,A2,B3"

    public SeatBooking(int userId, int movieId, String seatNumbers) {
        this.userId = userId;
        this.movieId = movieId;
        this.seatNumbers = seatNumbers;
    }

    public int getUserId() { return userId; }
    public int getMovieId() { return movieId; }
    public String getSeatNumbers() { return seatNumbers; }
}
