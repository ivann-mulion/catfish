package org.cat.fish.tripservice.controller;

import lombok.RequiredArgsConstructor;
import org.cat.fish.tripservice.model.dto.TripDto;
import org.cat.fish.tripservice.service.TripBookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trip/booking")
@RequiredArgsConstructor
public class TripBookingController {

    private final TripBookingService tripBookingService;

    @PostMapping("/{tripId}/book")
    public ResponseEntity<TripDto> bookTrip(@PathVariable Long tripId) {
        try {
            TripDto result = tripBookingService.bookTrip(tripId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new TripDto());
        }
    }


}