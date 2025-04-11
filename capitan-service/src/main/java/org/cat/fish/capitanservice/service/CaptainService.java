package org.cat.fish.capitanservice.service;

import org.cat.fish.capitanservice.model.dto.request.CaptainDetailsDto;
import org.cat.fish.capitanservice.model.dto.request.VesselCreationRequest;
import reactor.core.publisher.Mono;

public interface CaptainService {

    CaptainDetailsDto findById(Long id);
    CaptainDetailsDto update(final CaptainDetailsDto captainDetailsDto);
    CaptainDetailsDto update (final Long id, final CaptainDetailsDto captainDetailsDto);
    Mono<VesselCreationRequest> createVessel(final VesselCreationRequest vesselCreationRequest);


}
