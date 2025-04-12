package org.cat.fish.capitanservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cat.fish.capitanservice.model.dto.request.CaptainDetailsDto;
import org.cat.fish.capitanservice.model.dto.request.VesselCreationRequest;
import org.cat.fish.capitanservice.service.CaptainService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/captains")
public class CaptainController {

    private final CaptainService captainService;

    @GetMapping("/{captainId}")
    public ResponseEntity<CaptainDetailsDto> findById(@PathVariable("captainId")
                                               @NotBlank(message = "Input must not be blank!")
                                               @Valid final String captainId) {
        log.info("CaptainDetailsDto, resource; fetch captain by id");
        return ResponseEntity.ok(captainService.findById(Long.parseLong(captainId)));
    }

    @PutMapping
    public ResponseEntity<CaptainDetailsDto> update(@RequestBody
                                           @NotNull(message = "Input must not be NULL!")
                                           @Valid final CaptainDetailsDto captainId) {
        log.info("Captain, resource; update captain");
        return ResponseEntity.ok(captainService.update(captainId));
    }

    @PostMapping
    public Mono<ResponseEntity<VesselCreationRequest>> createVessel(@RequestBody @Valid final VesselCreationRequest vesselCreationRequest) {
        log.info("Captain, resource; create vessel");
        return captainService.createVessel(vesselCreationRequest)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{routeId}")
    public ResponseEntity<CaptainDetailsDto> update(
            @PathVariable("routeId") @NotBlank final String captainId,
            @RequestBody @NotNull @Valid final CaptainDetailsDto captainDetailsDto) {
        log.info("CaptainDetailsDto, resource; update captain with id: {}", captainId);
        return ResponseEntity.ok(captainService.update(Long.parseLong(captainId), captainDetailsDto));
    }



}
