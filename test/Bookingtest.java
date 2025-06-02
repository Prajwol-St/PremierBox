import org.junit.Test;
import static org.junit.Assert.*;

public class Bookingtest {

    @Test
    public void testBookMovie() {
        BookingManager manager = new BookingManager();
        User user = new User("john");
        Movie movie = new Movie("Inception", "Sci-fi", 148, "A mind-bending thriller");
        Booking booking = manager.bookMovie(user, movie);

        assertNotNull(booking);
        assertEquals(user, booking.getUser());
        assertEquals(movie, booking.getMovie());
    }
}
