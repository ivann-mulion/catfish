package org.cat.fish.vesselsservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cat.fish.vesselsservice.model.dto.request.VesselsDto;
import org.cat.fish.vesselsservice.service.VesselsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/vessels")
public class VesselsController {

    @Autowired
    private VesselsService vesselsService;

    @GetMapping
    public Flux<List<VesselsDto>> findAll() {
        log.info("ProductDto List, controller; fetch all categories");
        return vesselsService.findAll();
    }

    @GetMapping("/{vesselId}")
    public ResponseEntity<VesselsDto> findById(@PathVariable("vesselId")
                                               @NotBlank(message = "Input must not be blank!")
                                               @Valid final String vesselId) {
        log.info("VesselsDto, resource; fetch vessel by id");
        return ResponseEntity.ok(vesselsService.findById(Long.parseLong(vesselId)));
    }

    @PostMapping
    public ResponseEntity<VesselsDto> save(@RequestBody
                                           @NotNull(message = "Input must not be NULL!")
                                           @Valid final VesselsDto vesselId) {
        log.info("VesselsDto, resource; save vessel");
        return ResponseEntity.ok(vesselsService.save(vesselId));
    }

    @PutMapping
    public ResponseEntity<VesselsDto> update(@RequestBody
                                             @NotNull(message = "Input must not be NULL!")
                                             @Valid final VesselsDto vesselId) {
        log.info("VesselsDto, resource; update vessel");
        return ResponseEntity.ok(vesselsService.update(vesselId));
    }

    @PutMapping("/{vesselId}")
    public ResponseEntity<VesselsDto> update(
            @PathVariable("vesselId") @NotBlank final String vesselId,
            @RequestBody @NotNull @Valid final VesselsDto vesselsDto) {
        log.info("UpdateVesselDto, resource; update vessel with id: {}", vesselId);
        return ResponseEntity.ok(vesselsService.update(Long.parseLong(vesselId), vesselsDto));
    }

    @DeleteMapping("/{vesselId}")
    public ResponseEntity<Boolean> deleteById(@PathVariable("vesselId") final String vesselId) {
        log.info("Boolean, resource; delete vessel by id");
        vesselsService.deleteById(Long.parseLong(vesselId));
        return ResponseEntity.ok(true);
    }

}
