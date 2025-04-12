package org.cat.fish.capitanservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.cat.fish.capitanservice.exception.wrapper.CaptainNotFoundException;
import org.cat.fish.capitanservice.helper.CaptainDetailsHelperMapping;
import org.cat.fish.capitanservice.model.dto.request.CaptainDetailsDto;
import org.cat.fish.capitanservice.model.dto.request.VesselCreationRequest;
import org.cat.fish.capitanservice.model.entity.CaptainDetails;
import org.cat.fish.capitanservice.repository.CaptainRepository;
import org.cat.fish.capitanservice.service.CaptainService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class CaptainServiceImpl implements CaptainService {

    @Autowired
    private CaptainRepository captainRepository;

    private final WebClient.Builder webClientBuilder;

    @Autowired
    public CaptainServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public CaptainDetailsDto findById(Long id) {
        log.info("Find captain by id: {}", id);
        return captainRepository.findById(id)
                .map(CaptainDetailsHelperMapping::mapToDto)
                .orElseThrow(() -> new CaptainNotFoundException("Captain not found"));
    }

    @Override
    public CaptainDetailsDto update(CaptainDetailsDto captainDetailsDto) {
        log.info("Update captain details:");
        CaptainDetails existingCaptain = captainRepository.findById(captainDetailsDto.getCaptainId())
                .orElseThrow(() -> new CaptainNotFoundException("Captain not found"));

        BeanUtils.copyProperties(captainDetailsDto, existingCaptain, "captainId");

        CaptainDetails updatedCaptain = captainRepository.save(existingCaptain);

        return CaptainDetailsHelperMapping.mapToDto(updatedCaptain);
    }

    @Override
    public CaptainDetailsDto update(Long id, CaptainDetailsDto captainDetailsDto) {
        CaptainDetails existingCaptain = captainRepository.findById(id)
                .orElseThrow(() -> new CaptainNotFoundException(String.format("Captain with id %s not found", id)));

        existingCaptain.setPassportDetails(captainDetailsDto.getPassportDetails());
        existingCaptain.setRatePerHour(captainDetailsDto.getRatePerHour());
        existingCaptain.setCurrentAccount(captainDetailsDto.getCurrentAccount());

        CaptainDetails updatedCaptain = captainRepository.save(existingCaptain);
        return CaptainDetailsHelperMapping.mapToDto(updatedCaptain);
    }

    @Override
    public Mono<VesselCreationRequest> createVessel(VesselCreationRequest vesselCreationRequest) {
        return webClientBuilder.baseUrl("http://localhost:8080").build()
                .post()
                .uri("api/vessels")
                .bodyValue(vesselCreationRequest)
                .retrieve()
                .bodyToMono(VesselCreationRequest.class);
    }
}
