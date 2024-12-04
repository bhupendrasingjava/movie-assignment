package com.example.theater;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.theaterservice.entity.Shows;
import com.example.theaterservice.entity.Theater;
import com.example.theaterservice.repository.ShowRepository;
import com.example.theaterservice.repository.TheatreRepository;
import com.example.theaterservice.service.TheatreService;

@SpringBootTest
class TheaterServiceApplicationTests {

	@Mock
	private TheatreRepository theatreRepository;

	@Mock
	private ShowRepository showRepository;

	@InjectMocks
	private TheatreService theatreService;

	public TheaterServiceApplicationTests() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void contextLoads() {
	}

	@Test
	void testAddTheatre() {
		Theater theatre = new Theater();
		theatre.setName("Test Theatre");
		when(theatreRepository.save(any(Theater.class))).thenReturn(theatre);

		Theater createdTheatre = theatreService.addTheatre(theatre);

		assertNotNull(createdTheatre);
		assertEquals("Test Theatre", createdTheatre.getName());
	}

	@Test
	void testUpdateTheatre() {
		Theater theatre = new Theater();
		theatre.setName("Test Theatre");
		when(theatreRepository.findById(anyLong())).thenReturn(Optional.of(theatre));
		when(theatreRepository.save(any(Theater.class))).thenReturn(theatre);

		Theater updatedTheatre = theatreService.updateTheatre(1L, theatre);

		assertNotNull(updatedTheatre);
		assertEquals("Test Theatre", updatedTheatre.getName());
	}

	@Test
	void testDeleteTheatre() {
		doNothing().when(theatreRepository).deleteById(anyLong());

		theatreService.deleteTheatre(1L);

		verify(theatreRepository, times(1)).deleteById(1L);
	}

	@Test
	void testAddShow() {
		Theater theatre = new Theater();
		theatre.setName("Test Theatre");
		when(theatreRepository.findById(anyLong())).thenReturn(Optional.of(theatre));

		Shows show = new Shows();
		show.setShowTime("18:00");
		when(showRepository.save(any(Shows.class))).thenReturn(show);

		Shows createdShow = theatreService.addShow(1L, show);

		assertNotNull(createdShow);
		assertEquals("18:00", createdShow.getShowTime());
	}

	@Test
	void testUpdateShow() {
		Shows show = new Shows();
		show.setShowTime("18:00");
		when(showRepository.findById(anyLong())).thenReturn(Optional.of(show));
		when(showRepository.save(any(Shows.class))).thenReturn(show);
		Shows updatedShow = theatreService.updateShow(1L, 1L, show);
		assertNotNull(updatedShow);
		assertEquals("18:00", updatedShow.getShowTime());
	}
}