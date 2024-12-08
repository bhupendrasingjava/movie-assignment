package com.example.movieservice.service;

import com.example.movieservice.entity.Movie;
import com.example.movieservice.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private static final Logger logger = LoggerFactory.getLogger(MovieService.class);

    @Autowired
    private MovieRepository movieRepository;

    public List<Movie> getAllMovies() {
        logger.debug("Received request to get all movies");
        List<Movie> movies = movieRepository.findAll();
        logger.info("Retrieved {} movies", movies.size());
        return movies;
    }

    public Movie getMovieById(Long id) {
        logger.debug("Received request to get movie by id: {}", id);
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Movie not found"));
        logger.info("Retrieved movie: {}", movie);
        return movie;
    }

    public Movie addMovie(Movie movie) {
        logger.debug("Received request to add a new movie: {}", movie);
        Movie savedMovie = movieRepository.save(movie);
        logger.info("Movie added successfully: {}", savedMovie);
        return savedMovie;
    }

    public Movie updateMovie(Long id, Movie movieDetails) {
        logger.debug("Received request to update movie with id: {}", id);
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Movie not found"));
        movie.setTitle(movieDetails.getTitle());
        movie.setLanguage(movieDetails.getLanguage());
        movie.setGenre(movieDetails.getGenre());
        movie.setReleaseDate(movieDetails.getReleaseDate());
        movie.setDuration(movieDetails.getDuration());
        Movie updatedMovie = movieRepository.save(movie);
        logger.info("Movie with id {} updated successfully: {}", id, updatedMovie);
        return updatedMovie;
    }

    public void deleteMovie(Long id) {
        logger.debug("Received request to delete movie with id: {}", id);
        movieRepository.deleteById(id);
        logger.info("Movie with id {} deleted successfully", id);
    }
}
