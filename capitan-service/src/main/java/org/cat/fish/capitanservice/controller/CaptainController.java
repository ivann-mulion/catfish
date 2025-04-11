package org.cat.fish.capitanservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cat.fish.capitanservice.model.dto.request.CaptainDetailsDto;
import org.cat.fish.capitanservice.service.CaptainService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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



}
