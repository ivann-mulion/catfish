

import org.cat.fish.tripservice.model.dto.TripDto;
import org.cat.fish.tripservice.model.entity.Trip;
import org.cat.fish.tripservice.model.entity.TripClient;
import org.cat.fish.tripservice.repository.TripClientRepository;
import org.cat.fish.tripservice.repository.TripRepository;
import org.cat.fish.tripservice.security.principal.UserPrincipal;
import org.cat.fish.tripservice.security.utils.SecurityUtils;
import org.cat.fish.tripservice.service.impl.TripBookingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TripBookingServiceImplTest {

    @Mock
    private TripRepository tripRepository;

    @Mock
    private TripClientRepository tripClientRepository;

    @Mock
    private SecurityUtils securityUtils;

    @InjectMocks
    private TripBookingServiceImpl tripBookingService;

    private UserPrincipal userPrincipal;
    private Trip trip;

    @BeforeEach
    void setUp() {
        userPrincipal = new UserPrincipal(1L, "John", "Doe");

        trip = new Trip();
        trip.setTripId(1L);
        trip.setStatus("AVAILABLE");
        trip.setPrice(100.0);
    }

    @Test
    void bookTrip_Success() {
        when(securityUtils.getCurrentUser()).thenReturn(userPrincipal);
        when(tripRepository.findById(1L)).thenReturn(Optional.of(trip));
        when(tripClientRepository.existsByClientIdAndTripId(1L, 1L)).thenReturn(false);
        when(tripClientRepository.findByClientId(1L)).thenReturn(Optional.empty());
        when(tripClientRepository.save(any(TripClient.class))).thenAnswer(invocation -> {
            TripClient client = invocation.getArgument(0);
            client.setClientId(1L);
            return client;
        });
        when(tripRepository.save(any(Trip.class))).thenReturn(trip);

        TripDto result = tripBookingService.bookTrip(1L);

        assertNotNull(result);
        verify(tripRepository).save(trip);
        verify(tripClientRepository).save(any(TripClient.class));
        assertEquals("BOOKED", trip.getStatus());
    }

    @Test
    void bookTrip_TripNotFound() {
        when(securityUtils.getCurrentUser()).thenReturn(userPrincipal);
        when(tripRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> tripBookingService.bookTrip(1L));

        assertEquals("Trip not found with id: 1", exception.getMessage());
    }

    @Test
    void bookTrip_UserAlreadyBooked() {
        when(securityUtils.getCurrentUser()).thenReturn(userPrincipal);
        when(tripRepository.findById(1L)).thenReturn(Optional.of(trip));
        when(tripClientRepository.existsByClientIdAndTripId(1L, 1L)).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> tripBookingService.bookTrip(1L));

        assertEquals("User already booked this trip", exception.getMessage());
    }

    @Test
    void bookTrip_ClientExists() {
        TripClient existingClient = new TripClient();
        existingClient.setClientId(1L);
        existingClient.setFirstName("John");
        existingClient.setLastName("Doe");

        when(securityUtils.getCurrentUser()).thenReturn(userPrincipal);
        when(tripRepository.findById(1L)).thenReturn(Optional.of(trip));
        when(tripClientRepository.existsByClientIdAndTripId(1L, 1L)).thenReturn(false);
        when(tripClientRepository.findByClientId(1L)).thenReturn(Optional.of(existingClient));
        when(tripRepository.save(any(Trip.class))).thenReturn(trip);

        TripDto result = tripBookingService.bookTrip(1L);

        assertNotNull(result);
        verify(tripClientRepository, never()).save(any(TripClient.class));
        verify(tripRepository).save(trip);
    }

    @Test
    void bookTrip_Exception() {
        when(securityUtils.getCurrentUser()).thenReturn(userPrincipal);
        when(tripRepository.findById(1L)).thenThrow(new RuntimeException("DB error"));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> tripBookingService.bookTrip(1L));

        assertTrue(exception.getMessage().contains("Failed to book trip"));
    }
}