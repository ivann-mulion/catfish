package org.cat.fish.vesselsservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.cat.fish.vesselsservice.model.dto.VesselsDto;
import org.cat.fish.vesselsservice.service.VesselsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/vessels")
public class VesselsController {

    @Autowired
    private VesselsService vesselsService;

    @GetMapping("/{vesselId}")
    public ResponseEntity<VesselsDto> findById(@PathVariable("vesselId")
                                               @NotBlank(message = "Input must not be blamk!")
                                               @Valid final String vesselId) {
        return ResponseEntity.ok(vesselsService.findById(Long.parseLong(vesselId)));
    }

    @PostMapping
    public ResponseEntity<VesselsDto> save(@RequestBody
                                           @NotNull(message = "Input must not be NULL!")
                                           @Valid final VesselsDto vesselId) {
        return ResponseEntity.ok(vesselsService.save(vesselId));
    }

    @PutMapping
    public ResponseEntity<VesselsDto> update(@RequestBody
                                             @NotNull(message = "Input must not be NULL!")
                                             @Valid final VesselsDto vesselId) {
        return ResponseEntity.ok(vesselsService.update(vesselId));
    }

    @DeleteMapping("/{vesselId}")
    public ResponseEntity<Boolean> deleteById(@PathVariable("vesselId") final String vesselId) {
        vesselsService.deleteById(Long.parseLong(vesselId));
        return ResponseEntity.ok(true);
    }

}
