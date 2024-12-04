package com.example.movie;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.movieservice.entity.Movie;
import com.example.movieservice.repository.MovieRepository;
import com.example.movieservice.service.MovieService;

class MovieServiceTests {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllMovies() {
        Movie movie1 = new Movie();
        movie1.setId(1L);
        movie1.setTitle("Avengers: Endgame");

        Movie movie2 = new Movie();
        movie2.setId(2L);
        movie2.setTitle("Dangal");

        List<Movie> movies = Arrays.asList(movie1, movie2);
        when(movieRepository.findAll()).thenReturn(movies);

        List<Movie> result = movieService.getAllMovies();

        assertEquals(2, result.size());
        assertEquals("Avengers: Endgame", result.get(0).getTitle());
        assertEquals("Dangal", result.get(1).getTitle());
    }

    @Test
    void testGetMovieById() {
        Movie movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Avengers: Endgame");

        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));

        Movie result = movieService.getMovieById(1L);

        assertNotNull(result);
        assertEquals("Avengers: Endgame", result.getTitle());
    }

    @Test
    void testAddMovie() {
        Movie movie = new Movie();
        movie.setTitle("Avengers: Endgame");

        when(movieRepository.save(any(Movie.class))).thenReturn(movie);

        Movie result = movieService.addMovie(movie);

        assertNotNull(result);
        assertEquals("Avengers: Endgame", result.getTitle());
    }

    @Test
    void testUpdateMovie() {
        Movie existingMovie = new Movie();
        existingMovie.setId(1L);
        existingMovie.setTitle("Avengers: Endgame");

        Movie updatedMovie = new Movie();
        updatedMovie.setTitle("Avengers: Endgame Updated");

        when(movieRepository.findById(1L)).thenReturn(Optional.of(existingMovie));
        when(movieRepository.save(any(Movie.class))).thenReturn(updatedMovie);

        Movie result = movieService.updateMovie(1L, updatedMovie);

        assertNotNull(result);
        assertEquals("Avengers: Endgame Updated", result.getTitle());
    }

    @Test
    void testDeleteMovie() {
        Movie movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Avengers: Endgame");

        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        doNothing().when(movieRepository).deleteById(1L);

        movieService.deleteMovie(1L);

        verify(movieRepository, times(1)).deleteById(1L);
    }
}
