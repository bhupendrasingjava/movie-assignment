package com.example.theaterservice.service;

import com.example.theaterservice.entity.Shows;
import com.example.theaterservice.entity.Theater;
import com.example.theaterservice.repository.ShowRepository;
import com.example.theaterservice.repository.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;

@Service
public class TheatreService {

    @Autowired
    private TheatreRepository theatreRepository;

    @Autowired
    private ShowRepository showRepository;

    @Transactional
    public Theater addTheatre(Theater theatre) {
        Theater savedTheatre = theatreRepository.save(theatre);
        for (Shows show : theatre.getShows()) {
            show.setTheatre(savedTheatre);
            showRepository.save(show);
        }
        return savedTheatre;
    }

    public List<Theater> getAllTheatres() {
        return theatreRepository.findAll();
    }

    public Theater updateTheatre(Long id, Theater theatreDetails) {
        Theater theatre = theatreRepository.findById(id).orElseThrow(() -> new RuntimeException("Theatre not found"));
        theatre.setName(theatreDetails.getName());
        theatre.setCity(theatreDetails.getCity());
        theatre.setAddress(theatreDetails.getAddress());
        theatre.setContactNumber(theatreDetails.getContactNumber());
        return theatreRepository.save(theatre);
    }

    public void deleteTheatre(Long id) {
        theatreRepository.deleteById(id);
    }

    @Transactional
    public Shows addShow(Long theatreId, Shows show) {
        Theater theatre = theatreRepository.findById(theatreId).orElseThrow(() -> new RuntimeException("Theatre not found"));
        show.setTheatre(theatre);
        return showRepository.save(show);
    }

    @Transactional
    public Shows updateShow(Long theatreId, Long showId, Shows showDetails) {
        Shows show = showRepository.findById(showId).orElseThrow(() -> new RuntimeException("Show not found"));
        show.setMovieId(showDetails.getMovieId());
        show.setShowTime(showDetails.getShowTime());
        show.setSeatCapacity(showDetails.getSeatCapacity());
        show.setAvailableSeats(showDetails.getAvailableSeats());
        return showRepository.save(show);
    }

    @Transactional
    public void deleteShow(Long theatreId, Long showId) {
        Shows show = showRepository.findById(showId).orElseThrow(() -> new RuntimeException("Show not found"));
        showRepository.delete(show);
    }
}
