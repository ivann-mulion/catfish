package org.cat.fish.routeservice.helper;

import org.cat.fish.routeservice.model.dto.request.RouteRestrictionDto;
import org.cat.fish.routeservice.model.entity.RouteRestriction;

public interface RouteRestrictionMappingHelper {
    static RouteRestrictionDto map(final RouteRestriction routeRestriction) {
        return RouteRestrictionDto.builder()

                .build();
    }
}
