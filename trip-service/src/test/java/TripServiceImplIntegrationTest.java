

import org.cat.fish.tripservice.exception.wrapper.TripNotFoundException;
import org.cat.fish.tripservice.model.dto.TripDto;
import org.cat.fish.tripservice.model.entity.Trip;
import org.cat.fish.tripservice.repository.TripRepository;
import org.cat.fish.tripservice.service.impl.TripServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class TripServiceImplIntegrationTest {

    @Mock
    private TripServiceImpl tripService;

    @Mock
    private TripRepository tripRepository;

    private Trip trip;

    @BeforeEach
    void setUp() {
        trip = new Trip();
        trip.setVesselId(100L);
        trip.setStatus("AVAILABLE");
        trip.setPrice(150.0);
        trip.setDepartureTime(LocalDateTime.now().plusDays(1));
        trip.setArrivalTime(LocalDateTime.now().plusDays(3));
        trip = tripRepository.save(trip);
    }

    @Test
    void findAll_Integration() {
        List<TripDto> result = tripService.findAll();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(trip.getTripId(), result.get(0).getTripId());
        assertEquals(100L, result.get(0).getVesselId());
    }

    @Test
    void findById_Integration_Success() {
        TripDto result = tripService.findById(trip.getTripId());

        assertNotNull(result);
        assertEquals(trip.getTripId(), result.getTripId());
        assertEquals(100L, result.getVesselId());
    }

    @Test
    void findById_Integration_NotFound() {
        TripNotFoundException exception = assertThrows(TripNotFoundException.class,
                () -> tripService.findById(999L));

        assertTrue(exception.getMessage().contains("Trip with id 999 not found"));
    }

    @Test
    void save_Integration() {
        TripDto newTripDto = new TripDto();
        newTripDto.setVesselId(200L);
        newTripDto.setStatus("AVAILABLE");
        newTripDto.setPrice(250.0);
        newTripDto.setDepartureTime(LocalDateTime.now().plusDays(5));
        newTripDto.setArrivalTime(LocalDateTime.now().plusDays(7));

        TripDto result = tripService.save(newTripDto);

        assertNotNull(result);
        assertNotNull(result.getTripId());
        assertEquals(200L, result.getVesselId());

        Optional<Trip> savedTrip = tripRepository.findById(result.getTripId());
        assertTrue(savedTrip.isPresent());
        assertEquals(200L, savedTrip.get().getVesselId());
    }

    @Test
    void update_Integration() {
        TripDto updateDto = new TripDto();
        updateDto.setTripId(trip.getTripId());
        updateDto.setVesselId(300L);
        updateDto.setStatus("BOOKED");
        updateDto.setPrice(300.0);

        TripDto result = tripService.update(updateDto);

        assertNotNull(result);
        assertEquals(300L, result.getVesselId());
        assertEquals("BOOKED", result.getStatus());

        Optional<Trip> updatedTrip = tripRepository.findById(trip.getTripId());
        assertTrue(updatedTrip.isPresent());
        assertEquals(300L, updatedTrip.get().getVesselId());
    }

    @Test
    void deleteById_Integration() {
        assertDoesNotThrow(() -> tripService.deleteById(trip.getTripId()));

        Optional<Trip> deletedTrip = tripRepository.findById(trip.getTripId());
        assertFalse(deletedTrip.isPresent());
    }
}