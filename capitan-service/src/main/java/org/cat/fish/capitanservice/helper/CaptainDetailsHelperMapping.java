package org.cat.fish.capitanservice.helper;

import org.cat.fish.capitanservice.model.dto.request.CaptainDetailsDto;
import org.cat.fish.capitanservice.model.entity.CaptainDetails;

public interface CaptainDetailsHelperMapping {

    static CaptainDetailsDto mapToDto(final CaptainDetails captainDetails) {
        return CaptainDetailsDto.builder()
                .captainId(captainDetails.getCaptainId())
                .userId(captainDetails.getUserId())
                .passportDetails(captainDetails.getPassportDetails())
                .ratePerHour(captainDetails.getRatePerHour())
                .currentAccount(captainDetails.getCurrentAccount())
                .build();
    }

    static CaptainDetails mapToEntity(CaptainDetailsDto captainDetailsDto) {
        return CaptainDetails.builder()
                .captainId(captainDetailsDto.getCaptainId())
                .userId(captainDetailsDto.getUserId())
                .passportDetails(captainDetailsDto.getPassportDetails())
                .ratePerHour(captainDetailsDto.getRatePerHour())
                .currentAccount(captainDetailsDto.getCurrentAccount())
                .build();
    }
}
