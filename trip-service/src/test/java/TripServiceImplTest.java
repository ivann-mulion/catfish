

import org.cat.fish.tripservice.exception.wrapper.TripNotFoundException;
import org.cat.fish.tripservice.model.dto.TripDto;
import org.cat.fish.tripservice.model.entity.Trip;
import org.cat.fish.tripservice.repository.TripRepository;
import org.cat.fish.tripservice.service.impl.TripServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class TripServiceImplTest {

    @Mock
    private TripRepository tripRepository;

    @InjectMocks
    private TripServiceImpl tripService;

    private Trip trip;
    private TripDto tripDto;

    @BeforeEach
    void setUp() {
        trip = new Trip();
        trip.setTripId(1L);
        trip.setVesselId(100L);
        trip.setStatus("AVAILABLE");
        trip.setPrice(150.0);
        trip.setDepartureTime(LocalDateTime.now().plusDays(1));
        trip.setArrivalTime(LocalDateTime.now().plusDays(3));

        tripDto = new TripDto();
        tripDto.setTripId(1L);
        tripDto.setVesselId(100L);
        tripDto.setStatus("AVAILABLE");
        tripDto.setPrice(150.0);
        tripDto.setDepartureTime(LocalDateTime.now().plusDays(1));
        tripDto.setArrivalTime(LocalDateTime.now().plusDays(3));
    }

    @Test
    void findAll_Success() {
        when(tripRepository.findAll()).thenReturn(Arrays.asList(trip));

        List<TripDto> result = tripService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getTripId());
        assertEquals(100L, result.get(0).getVesselId());
    }

    @Test
    void findAll_Exception() {
        when(tripRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> tripService.findAll());

        assertEquals("Error fetching trips", exception.getMessage());
    }

    @Test
    void findById_Success() {
        when(tripRepository.findById(1L)).thenReturn(Optional.of(trip));

        TripDto result = tripService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getTripId());
        assertEquals(100L, result.getVesselId());
    }

    @Test
    void findById_NotFound() {
        when(tripRepository.findById(1L)).thenReturn(Optional.empty());

        TripNotFoundException exception = assertThrows(TripNotFoundException.class,
                () -> tripService.findById(1L));

        assertEquals("Trip with id 1 not found", exception.getMessage());
    }

    @Test
    void save_Success() {
        when(tripRepository.save(any(Trip.class))).thenReturn(trip);

        TripDto result = tripService.save(tripDto);

        assertNotNull(result);
        assertEquals(1L, result.getTripId());
        assertEquals(100L, result.getVesselId());
        verify(tripRepository).save(any(Trip.class));
    }

    @Test
    void save_DataIntegrityViolation() {
        when(tripRepository.save(any(Trip.class))).thenThrow(new DataIntegrityViolationException("Constraint violation"));

        TripNotFoundException exception = assertThrows(TripNotFoundException.class,
                () -> tripService.save(tripDto));

        assertEquals("Error saving trip: Data integrity violation", exception.getMessage());
    }

    @Test
    void update_Success() {
        when(tripRepository.findById(1L)).thenReturn(Optional.of(trip));
        when(tripRepository.save(any(Trip.class))).thenReturn(trip);

        TripDto result = tripService.update(tripDto);

        assertNotNull(result);
        assertEquals(1L, result.getTripId());
        verify(tripRepository).save(any(Trip.class));
    }

    @Test
    void update_NotFound() {
        when(tripRepository.findById(1L)).thenReturn(Optional.empty());

        TripNotFoundException exception = assertThrows(TripNotFoundException.class,
                () -> tripService.update(tripDto));

        assertEquals("Trip not found with id: 1", exception.getMessage());
    }

    @Test
    void updateWithId_Success() {
        when(tripRepository.findById(1L)).thenReturn(Optional.of(trip));
        when(tripRepository.save(any(Trip.class))).thenReturn(trip);

        TripDto result = tripService.update(1L, tripDto);

        assertNotNull(result);
        assertEquals(1L, result.getTripId());
    }

    @Test
    void deleteById_Success() {
        when(tripRepository.findById(1L)).thenReturn(Optional.of(trip));
        doNothing().when(tripRepository).delete(trip);

        assertDoesNotThrow(() -> tripService.deleteById(1L));
        verify(tripRepository).delete(trip);
    }

    @Test
    void deleteById_NotFound() {
        when(tripRepository.findById(1L)).thenReturn(Optional.empty());

        TripNotFoundException exception = assertThrows(TripNotFoundException.class,
                () -> tripService.deleteById(1L));

        assertEquals("Trip not found with id: 1", exception.getMessage());
    }
}