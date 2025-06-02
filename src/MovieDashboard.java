import java.util.*;

public class MovieDashboard {
    private List<Movie> movies = new ArrayList<>();
    
    public void addMovie(Movie movie) {
        movies.add(movie);
        
    }

    public void updateMovie(Movie updatedMovie) {
        for (int i = 0; i < movies.size(); i++) {
            if (movies.get(i).getTitle().equals(updatedMovie.getTitle())) {
                movies.set(i, updatedMovie);
                return;
            }
        }
    }

    public List<Movie> getAllMovies() {
        return movies;
    }

    public Movie getMovieByTitle(String title) {
        for (Movie movie : movies) {
            if (movie.getTitle().equals(title)) {
                return movie;
            }
        }
        return null;
    }
}