package com.example.theaterservice.service;

import com.example.theaterservice.entity.Shows;
import com.example.theaterservice.entity.Theater;
import com.example.theaterservice.repository.ShowRepository;
import com.example.theaterservice.repository.TheatreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;

@Service
public class TheatreService {

    private static final Logger logger = LoggerFactory.getLogger(TheatreService.class);

    @Autowired
    private TheatreRepository theatreRepository;

    @Autowired
    private ShowRepository showRepository;

    @Transactional
    public Theater addTheatre(Theater theatre) {
        logger.debug("Received request to add a new theatre: {}", theatre);
        Theater savedTheatre = theatreRepository.save(theatre);
        for (Shows show : theatre.getShows()) {
            show.setTheatre(savedTheatre);
            showRepository.save(show);
            logger.info("Show added to theatre {}: {}", savedTheatre.getId(), show);
        }
        logger.info("Theatre added successfully: {}", savedTheatre);
        return savedTheatre;
    }

    public List<Theater> getAllTheatres() {
        logger.debug("Received request to get all theatres");
        List<Theater> theatres = theatreRepository.findAll();
        logger.info("Retrieved {} theatres", theatres.size());
        return theatres;
    }

    public Theater updateTheatre(Long id, Theater theatreDetails) {
        logger.debug("Received request to update theatre with id: {}", id);
        Theater theatre = theatreRepository.findById(id).orElseThrow(() -> new RuntimeException("Theatre not found"));
        theatre.setName(theatreDetails.getName());
        theatre.setCity(theatreDetails.getCity());
        theatre.setAddress(theatreDetails.getAddress());
        theatre.setContactNumber(theatreDetails.getContactNumber());
        Theater updatedTheatre = theatreRepository.save(theatre);
        logger.info("Theatre with id {} updated successfully: {}", id, updatedTheatre);
        return updatedTheatre;
    }

    public void deleteTheatre(Long id) {
        logger.debug("Received request to delete theatre with id: {}", id);
        theatreRepository.deleteById(id);
        logger.info("Theatre with id {} deleted successfully", id);
    }

    @Transactional
    public Shows addShow(Long theatreId, Shows show) {
        logger.debug("Received request to add a new show for theatreId: {}", theatreId);
        Theater theatre = theatreRepository.findById(theatreId).orElseThrow(() -> new RuntimeException("Theatre not found"));
        show.setTheatre(theatre);
        Shows savedShow = showRepository.save(show);
        logger.info("Show added successfully for theatreId {}: {}", theatreId, savedShow);
        return savedShow;
    }

    @Transactional
    public Shows updateShow(Long theatreId, Long showId, Shows showDetails) {
        logger.debug("Received request to update show with id: {} for theatreId: {}", showId, theatreId);
        Shows show = showRepository.findById(showId).orElseThrow(() -> new RuntimeException("Show not found"));
        show.setMovieId(showDetails.getMovieId());
        show.setShowTime(showDetails.getShowTime());
        show.setSeatCapacity(showDetails.getSeatCapacity());
        show.setAvailableSeats(showDetails.getAvailableSeats());
        Shows updatedShow = showRepository.save(show);
        logger.info("Show with id {} updated successfully for theatreId {}: {}", showId, theatreId, updatedShow);
        return updatedShow;
    }

    @Transactional
    public void deleteShow(Long theatreId, Long showId) {
        logger.debug("Received request to delete show with id: {} for theatreId: {}", showId, theatreId);
        Shows show = showRepository.findById(showId).orElseThrow(() -> new RuntimeException("Show not found"));
        showRepository.delete(show);
        logger.info("Show with id {} deleted successfully for theatreId {}", showId, theatreId);
    }
}
