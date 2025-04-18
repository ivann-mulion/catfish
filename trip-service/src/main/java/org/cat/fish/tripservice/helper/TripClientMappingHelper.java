package org.cat.fish.tripservice.helper;

import org.cat.fish.tripservice.model.dto.TripClientDto;
import org.cat.fish.tripservice.model.entity.TripClient;
import org.cat.fish.tripservice.security.utils.SecurityUtils;

public interface TripClientMappingHelper {

    static TripClientDto mapToDto(final TripClient captainDetails) {
        return TripClientDto.builder()
                .clientId(SecurityUtils.getCurrentUserId())
                .firstName(SecurityUtils.getCurrentFirstName())
                .lastName(SecurityUtils.getCurrentLastName())
                .build();
    }
}
