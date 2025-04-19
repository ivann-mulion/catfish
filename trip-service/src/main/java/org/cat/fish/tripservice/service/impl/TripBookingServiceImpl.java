package org.cat.fish.tripservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cat.fish.tripservice.helper.TripMappingHelper;
import org.cat.fish.tripservice.model.dto.TripDto;
import org.cat.fish.tripservice.model.entity.Trip;
import org.cat.fish.tripservice.model.entity.TripClient;
import org.cat.fish.tripservice.repository.TripClientRepository;
import org.cat.fish.tripservice.repository.TripRepository;
import org.cat.fish.tripservice.security.principal.UserPrincipal;
import org.cat.fish.tripservice.security.utils.SecurityUtils;
import org.cat.fish.tripservice.service.TripBookingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TripBookingServiceImpl implements TripBookingService {

    private final TripRepository tripRepository;
    private final TripClientRepository tripClientRepository;
    private final SecurityUtils securityUtils;

    @Override
    public TripDto bookTrip(Long tripId) {
        try {
            UserPrincipal user = securityUtils.getCurrentUser();
            return processBooking(tripId, user);
        } catch (Exception e) {
            log.error("Failed to book trip: {}", e.getMessage());
            throw new RuntimeException("Failed to book trip: " + e.getMessage(), e);
        }
    }

    private TripDto processBooking(Long tripId, UserPrincipal user) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found with id: " + tripId));

        if (tripClientRepository.existsByClientIdAndTripId(user.getId(), tripId)) {
            throw new RuntimeException("User already booked this trip");
        }

        TripClient client = findOrCreateClient(user);

        trip.addClient(client);
        trip.setStatus("BOOKED");
        trip.setUpdatedAt(LocalDateTime.now());

        Trip savedTrip = tripRepository.save(trip);

        return TripMappingHelper.mapToDto(savedTrip);
    }

    private TripClient findOrCreateClient(UserPrincipal user) {
        Optional<TripClient> existingClient = tripClientRepository.findByClientId(user.getId());

        if (existingClient.isPresent()) {
            return existingClient.get();
        }

        TripClient newClient = new TripClient();
        newClient.setClientId(user.getId());
        newClient.setFirstName(user.getFirstName());
        newClient.setLastName(user.getLastName());

        return tripClientRepository.save(newClient);
    }
}