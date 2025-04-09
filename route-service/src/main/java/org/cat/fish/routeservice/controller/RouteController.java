package org.cat.fish.routeservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cat.fish.routeservice.model.dto.request.RouteDto;
import org.cat.fish.routeservice.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/routes")
@RequiredArgsConstructor
public class RouteController {

    @Autowired
    private RouteService routeService;

    @GetMapping("/{routeId}")
    public ResponseEntity<RouteDto> findById(@PathVariable("routeId")
                                               @NotBlank(message = "Input must not be blank!")
                                               @Valid final String routeId) {
        log.info("RouteDto, resource; fetch route by id");
        return ResponseEntity.ok(routeService.findById(Long.parseLong(routeId)));
    }

    @PostMapping
    public ResponseEntity<RouteDto> save(@RequestBody
                                           @NotNull(message = "Input must not be NULL!")
                                           @Valid final RouteDto routeId) {
        log.info("RouteDto, resource; save route");
        return ResponseEntity.ok(routeService.createRoute(routeId));
    }

    @PutMapping
    public ResponseEntity<RouteDto> update(@RequestBody
                                             @NotNull(message = "Input must not be NULL!")
                                             @Valid final RouteDto routeId) {
        log.info("Route, resource; update route");
        return ResponseEntity.ok(routeService.updateRoute(routeId));
    }

    @PutMapping("/{routeId}")
    public ResponseEntity<RouteDto> update(
            @PathVariable("routeId") @NotBlank final String routeId,
            @RequestBody @NotNull @Valid final RouteDto routeDto) {
        log.info("RouteDto, resource; update route with id: {}", routeId);
        return ResponseEntity.ok(routeService.updateRoute(Long.parseLong(routeId), routeDto));
    }

    @DeleteMapping("/{routeId}")
    public ResponseEntity<Boolean> deleteById(@PathVariable("routeId") final String vesselId) {
        log.info("Boolean, resource; delete route by id");
        routeService.deleteRoute(Long.parseLong(vesselId));
        return ResponseEntity.ok(true);
    }
}
