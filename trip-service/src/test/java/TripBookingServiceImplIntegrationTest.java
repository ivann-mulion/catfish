

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
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class TripBookingServiceImplIntegrationTest {

    @Mock
    private TripBookingServiceImpl tripBookingService;

    @Mock
    private TripRepository tripRepository;

    @Mock
    private TripClientRepository tripClientRepository;

    @Mock
    private SecurityUtils securityUtils;

    private UserPrincipal userPrincipal;
    private Trip trip;

    @BeforeEach
    void setUp() {
        userPrincipal = new UserPrincipal(1L, "John", "Doe");

        trip = new Trip();
        trip.setVesselId(100L);
        trip.setStatus("AVAILABLE");
        trip.setPrice(200.0);
        trip.setDepartureTime(LocalDateTime.now().plusDays(1));
        trip.setArrivalTime(LocalDateTime.now().plusDays(3));
        trip = tripRepository.save(trip);

        when(securityUtils.getCurrentUser()).thenReturn(userPrincipal);
    }

    @Test
    void bookTrip_Integration_Success() {
        TripDto result = tripBookingService.bookTrip(trip.getTripId());

        assertNotNull(result);
        assertEquals("BOOKED", result.getStatus());


        Optional<Trip> savedTrip = tripRepository.findById(trip.getTripId());
        assertTrue(savedTrip.isPresent());
        assertEquals("BOOKED", savedTrip.get().getStatus());
        assertFalse(savedTrip.get().getClients().isEmpty());

        Optional<TripClient> client = tripClientRepository.findByClientId(1L);
        assertTrue(client.isPresent());
        assertEquals("John", client.get().getFirstName());
    }

    @Test
    void bookTrip_UserAlreadyBooked_Integration() {
        tripBookingService.bookTrip(trip.getTripId());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> tripBookingService.bookTrip(trip.getTripId()));

        assertEquals("User already booked this trip", exception.getMessage());
    }

    @Test
    void bookTrip_ClientReuse_Integration() {

        TripClient existingClient = new TripClient();
        existingClient.setClientId(1L);
        existingClient.setFirstName("John");
        existingClient.setLastName("Doe");
        tripClientRepository.save(existingClient);

        TripDto result = tripBookingService.bookTrip(trip.getTripId());

        assertNotNull(result);


        long clientCount = tripClientRepository.count();
        assertEquals(1, clientCount);
    }
}