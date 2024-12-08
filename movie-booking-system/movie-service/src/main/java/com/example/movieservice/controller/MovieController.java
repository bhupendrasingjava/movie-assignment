package com.example.movieservice.controller;

import com.example.movieservice.entity.Movie;
import com.example.movieservice.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private static final Logger logger = LoggerFactory.getLogger(MovieController.class);

    @Autowired
    private MovieService movieService;

    @GetMapping
    public List<Movie> getAllMovies() {
        logger.debug("Received request to get all movies");
        List<Movie> movies = movieService.getAllMovies();
        logger.info("Retrieved {} movies", movies.size());
        return movies;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        logger.debug("Received request to get movie by id: {}", id);
        Movie movie = movieService.getMovieById(id);
        if (movie != null) {
            logger.info("Retrieved movie: {}", movie);
            return ResponseEntity.ok(movie);
        } else {
            logger.warn("Movie not found with id: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
        logger.debug("Received request to add a new movie: {}", movie);
        Movie savedMovie = movieService.addMovie(movie);
        logger.info("Movie added successfully: {}", savedMovie);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMovie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody Movie movieDetails) {
        logger.debug("Received request to update movie with id: {}", id);
        Movie updatedMovie = movieService.updateMovie(id, movieDetails);
        if (updatedMovie != null) {
            logger.info("Movie with id {} updated successfully: {}", id, updatedMovie);
            return ResponseEntity.ok(updatedMovie);
        } else {
            logger.warn("Movie not found with id: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMovie(@PathVariable Long id) {
        logger.debug("Received request to delete movie with id: {}", id);
        movieService.deleteMovie(id);
        logger.info("Movie with id {} deleted successfully", id);
    }
}
