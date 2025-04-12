package org.cat.fish.capitanservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.cat.fish.capitanservice.exception.wrapper.CaptainNotFoundException;
import org.cat.fish.capitanservice.helper.CaptainDetailsHelperMapping;
import org.cat.fish.capitanservice.model.dto.request.CaptainDetailsDto;
import org.cat.fish.capitanservice.model.dto.request.VesselCreationRequest;
import org.cat.fish.capitanservice.model.entity.CaptainDetails;
import org.cat.fish.capitanservice.repository.CaptainRepository;
import org.cat.fish.capitanservice.security.utils.SecurityUtils;
import org.cat.fish.capitanservice.service.CaptainService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@Service
public class CaptainServiceImpl implements CaptainService {

    @Autowired
    private CaptainRepository captainRepository;

    private final RestTemplate restTemplate;

    private final WebClient.Builder webClientBuilder;

    @Autowired
    public CaptainServiceImpl(WebClient.Builder webClientBuilder, RestTemplate restTemplate) {
        this.webClientBuilder = webClientBuilder;
        this.restTemplate = restTemplate;
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
    public VesselCreationRequest createVessel(VesselCreationRequest vesselCreationRequest) {
        Long userId = SecurityUtils.getCurrentUserId();

        System.out.println(String.format("Captain id %d", userId));
        CaptainDetails captainDetails = captainRepository.findByUserId(userId)
                .orElseThrow(() -> new CaptainNotFoundException(
                        String.format("Captain with userId %s not found", userId)
                ));

        vesselCreationRequest.setCaptainId(captainDetails.getCaptainId());

        ResponseEntity<VesselCreationRequest> response = restTemplate.postForEntity(
                "http://localhost:8086/api/vessels",
                vesselCreationRequest,
                VesselCreationRequest.class
        );

        return response.getBody();
    }
}
