package org.cat.fish.routeservice.helper;

import org.cat.fish.routeservice.model.dto.request.RouteDto;
import org.cat.fish.routeservice.model.entity.Route;

public interface RouteMappingHelper {
    static RouteDto mapToDto(final Route route) {
        return RouteDto.builder()
                .routeId(route.getRouteId())
                .name(route.getName())
                .description(route.getDescription())
                .build();
    }

    static Route mapToEntity(final RouteDto routeDto) {
        return Route.builder()
                .routeId(routeDto.getRouteId())
                .name(routeDto.getName())
                .description(routeDto.getDescription())
                .build();
    }
}
