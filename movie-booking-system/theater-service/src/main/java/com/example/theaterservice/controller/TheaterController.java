package com.example.theaterservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.theaterservice.entity.Movie;
import com.example.theaterservice.entity.Shows;
import com.example.theaterservice.entity.Theater;
import com.example.theaterservice.service.TheatreService;

@RestController
@RequestMapping("/api/theatres")
public class TheaterController {
    
    private static final Logger logger = LoggerFactory.getLogger(TheaterController.class);

    @Autowired
    private TheatreService theatreService;
    
    @Autowired 
    private RestTemplate restTemplate;

    @PostMapping
    public Theater createTheatre(@RequestBody Theater theatre) {
        logger.debug("Received request to create a new theatre: {}", theatre);
        Theater createdTheatre = theatreService.addTheatre(theatre);
        logger.info("Theatre created successfully: {}", createdTheatre);
        return createdTheatre;
    }

    @GetMapping
    public List<Theater> getAllTheatres() {
        logger.info("Received request to get all theatres");
        List<Theater> theatres = theatreService.getAllTheatres();
        logger.info("Retrieved {} theatres", theatres.size());
        return theatres;
    }

    @PutMapping("/{id}")
    public Theater updateTheatre(@PathVariable Long id, @RequestBody Theater theatreDetails) {
        logger.debug("Received request to update theatre with id: {}", id);
        Theater updatedTheatre = theatreService.updateTheatre(id, theatreDetails);
        logger.info("Theatre with id {} updated successfully: {}", id, updatedTheatre);
        return updatedTheatre;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTheatre(@PathVariable Long id) {
        logger.debug("Received request to delete theatre with id: {}", id);
        theatreService.deleteTheatre(id);
        logger.info("Theatre with id {} deleted successfully", id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{theatreId}/shows")
    public Shows createShow(@PathVariable Long theatreId, @RequestBody Shows show) {
        logger.debug("Received request to create a new show for theatreId: {}", theatreId);
        Shows createdShow = theatreService.addShow(theatreId, show);
        logger.info("Show created successfully for theatreId {}: {}", theatreId, createdShow);
        return createdShow;
    }

    @PutMapping("/{theatreId}/shows/{showId}")
    public Shows updateShow(@PathVariable Long theatreId, @PathVariable Long showId, @RequestBody Shows showDetails) {
        logger.debug("Received request to update show with id: {} for theatreId: {}", showId, theatreId);
        Shows updatedShow = theatreService.updateShow(theatreId, showId, showDetails);
        logger.info("Show with id {} updated successfully for theatreId {}: {}", showId, theatreId, updatedShow);
        return updatedShow;
    }

    @DeleteMapping("/{theatreId}/shows/{showId}")
    public ResponseEntity<Void> deleteShow(@PathVariable Long theatreId, @PathVariable Long showId) {
        logger.debug("Received request to delete show with id: {} for theatreId: {}", showId, theatreId);
        theatreService.deleteShow(theatreId, showId);
        logger.info("Show with id {} deleted successfully for theatreId {}", showId, theatreId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/movies")
    public List<Movie> getMoviesFromMovieService() {
        logger.info("Calling movie-service from theater-service");
        List<Movie> movies = restTemplate.getForObject("http://localhost:8083/movies/api/movies", List.class);
        logger.info("Received response from movie-service with {} movies", movies.size());
        return movies;
    }
}
