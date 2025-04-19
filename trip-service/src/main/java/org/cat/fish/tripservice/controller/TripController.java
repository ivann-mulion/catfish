package org.cat.fish.tripservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cat.fish.tripservice.model.dto.TripDto;
import org.cat.fish.tripservice.service.TripService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/trip")
public class TripController {

    private final TripService tripService;

    @GetMapping
    public List<TripDto> findAll() {
        log.info("TripDto List, controller; fetch all trips");
        return tripService.findAll();
    }

    @GetMapping("/{tripId}")
    public ResponseEntity<TripDto> findById(@PathVariable("tripId")
                                            @NotBlank(message = "Input must not be blank!")
                                            @Valid final Long tripId) {
        log.info("TripDto, resource; fetch trip by id: {}", tripId);
        try {
            TripDto tripDto = tripService.findById(tripId);
            return ResponseEntity.ok(tripDto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<TripDto> save(@RequestBody
                                        @NotNull(message = "Input must not be NULL!")
                                        @Valid final TripDto tripDto) {
        log.info("TripDto, resource; save trip");
        try {
            TripDto savedTrip = tripService.save(tripDto);
            return ResponseEntity.ok(savedTrip);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping
    public ResponseEntity<TripDto> update(@RequestBody
                                          @NotNull(message = "Input must not be NULL!")
                                          @Valid final TripDto tripDto) {
        log.info("TripDto, resource; update trip");
        try {
            TripDto updatedTrip = tripService.update(tripDto);
            return ResponseEntity.ok(updatedTrip);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{tripId}")
    public ResponseEntity<TripDto> update(
            @PathVariable("tripId") @NotBlank final Long tripId,
            @RequestBody @NotNull @Valid final TripDto tripDto) {
        log.info("TripDto, resource; update trip with id: {}", tripId);
        try {
            TripDto updatedTrip = tripService.update(tripId, tripDto);
            return ResponseEntity.ok(updatedTrip);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{tripId}")
    public ResponseEntity<Void> deleteById(@PathVariable("tripId") final Long tripId) {
        log.info("Boolean, resource; delete trip by id: {}", tripId);
        try {
            tripService.deleteById(tripId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}