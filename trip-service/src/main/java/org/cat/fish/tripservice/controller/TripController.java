package org.cat.fish.tripservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cat.fish.tripservice.model.dto.TripDto;
import org.cat.fish.tripservice.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/trip")
public class TripController{

    @Autowired
    private TripService tripService;

    @GetMapping
    public Flux<List<TripDto>> findAll() {
        log.info("ProductDto List, controller; fetch all categories");
        return tripService.findAll();
    }

    @GetMapping("/{tripId}")
    public ResponseEntity<TripDto> findById(@PathVariable("tripId")
                                               @NotBlank(message = "Input must not be blank!")
                                               @Valid final String vesselId) {
        log.info("TripDto, resource; fetch trip by id");
        return ResponseEntity.ok(tripService.findById(Long.parseLong(vesselId)));
    }

    @PostMapping
    public ResponseEntity<TripDto> save(@RequestBody
                                           @NotNull(message = "Input must not be NULL!")
                                           @Valid final TripDto vesselId) {
        log.info("TripDto, resource; save trip");
        return ResponseEntity.ok(tripService.save(vesselId));
    }

    @PutMapping
    public ResponseEntity<TripDto> update(@RequestBody
                                             @NotNull(message = "Input must not be NULL!")
                                             @Valid final TripDto tripId) {
        log.info("TripDto, resource; update trip");
        return ResponseEntity.ok(tripService.update(tripId));
    }

    @PutMapping("/{tripId}")
    public ResponseEntity<TripDto> update(
            @PathVariable("tripId") @NotBlank final String tripId,
            @RequestBody @NotNull @Valid final TripDto tripDto) {
        log.info("TripDto, resource; update trip with id: {}", tripId);
        return ResponseEntity.ok(tripService.update(Long.parseLong(tripId), tripDto));
    }

    @DeleteMapping("/{tripId}")
    public ResponseEntity<Boolean> deleteById(@PathVariable("tripId") final String tripId) {
        log.info("Boolean, resource; delete trip by id");
        tripService.deleteById(Long.parseLong(tripId));
        return ResponseEntity.ok(true);
    }

    @PostMapping("/order")


}