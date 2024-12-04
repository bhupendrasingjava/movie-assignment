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
		return theatreService.addTheatre(theatre);
	}

	@GetMapping
	public List<Theater> getAllTheatres() {
		return theatreService.getAllTheatres();
	}

	@PutMapping("/{id}")
	public Theater updateTheatre(@PathVariable Long id, @RequestBody Theater theatreDetails) {
		return theatreService.updateTheatre(id, theatreDetails);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTheatre(@PathVariable Long id) {
		theatreService.deleteTheatre(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/{theatreId}/shows")
	public Shows createShow(@PathVariable Long theatreId, @RequestBody Shows show) {
		return theatreService.addShow(theatreId, show);
	}

	@PutMapping("/{theatreId}/shows/{showId}")
	public Shows updateShow(@PathVariable Long theatreId, @PathVariable Long showId, @RequestBody Shows showDetails) {
		return theatreService.updateShow(theatreId, showId, showDetails);
	}

	@DeleteMapping("/{theatreId}/shows/{showId}")
	public ResponseEntity<Void> deleteShow(@PathVariable Long theatreId, @PathVariable Long showId) {
		theatreService.deleteShow(theatreId, showId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/movies")
	public List<Movie> getMoviesFromMovieService() {
		logger.info("Calling movie-service from theater-service");
		List<Movie> movies = restTemplate.getForObject("http://localhost:8083/movies/api/movies", List.class);
		logger.info("Received response from movie-service");
		return movies;
	}
}
