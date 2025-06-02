import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

public class MovieDashboardtest {

    @Test
    public void testAddAndGetAllMovies() {
        MovieDashboard dashboard = new MovieDashboard();
        Movie m1 = new Movie("Inception", "Sci-fi", 148, "Mind-bending thriller");
        Movie m2 = new Movie("Titanic", "Romance", 195, "Epic romance");

        dashboard.addMovie(m1);
        dashboard.addMovie(m2);

        List<Movie> movies = dashboard.getAllMovies();
        assertEquals(2, movies.size());
        assertTrue(movies.contains(m1));
        assertTrue(movies.contains(m2));
    }

    @Test
    public void testGetMovieByTitle() {
        MovieDashboard dashboard = new MovieDashboard();
        Movie m1 = new Movie("Inception", "Sci-fi", 148, "Mind-bending thriller");
        dashboard.addMovie(m1);

        Movie found = dashboard.getMovieByTitle("Inception");
        assertNotNull(found);
        assertEquals("Sci-fi", found.getGenre());

        Movie notFound = dashboard.getMovieByTitle("Avatar");
        assertNull(notFound);
    }

    @Test
    public void testUpdateMovie() {
        MovieDashboard dashboard = new MovieDashboard();
        Movie original = new Movie("Inception", "Sci-fi", 148, "Thriller");
        dashboard.addMovie(original);

        Movie updated = new Movie("Inception", "Sci-fi", 150, "Updated description");
        dashboard.updateMovie(updated);

        Movie result = dashboard.getMovieByTitle("Inception");
        assertEquals(150, result.getDuration());
        assertEquals("Updated description", result.getDescription());
    }
}
