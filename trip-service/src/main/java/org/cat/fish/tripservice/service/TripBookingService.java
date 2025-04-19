package org.cat.fish.tripservice.service;

import org.cat.fish.tripservice.model.dto.TripDto;

public interface TripBookingService {
    TripDto bookTrip(Long tripId);
}