package org.cat.fish.tripservice.helper;

import org.cat.fish.tripservice.model.dto.TripClientDto;
import org.cat.fish.tripservice.model.entity.TripClient;
import org.cat.fish.tripservice.security.utils.SecurityUtils;

public interface TripClientMappingHelper {

    static TripClientDto mapToDto(final TripClient tripClient) {
        return TripClientDto.builder()
                .clientId(SecurityUtils.currentUserId())
                .firstName(SecurityUtils.currentFirstName())
                .lastName(SecurityUtils.currentLastName())
                .build();
    }


    static TripClient mapToEntity(final TripClientDto tripClientDto) {
        TripClient tripClient = new TripClient();
        tripClient.setFirstName(tripClientDto.getFirstName());
        tripClient.setLastName(tripClientDto.getLastName());
        return tripClient;
    }
}