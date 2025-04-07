package org.cat.fish.routeservice.helper;

import org.cat.fish.routeservice.model.dto.request.RouteDto;
import org.cat.fish.routeservice.model.dto.request.RoutePhotosDto;
import org.cat.fish.routeservice.model.entity.Route;
import org.cat.fish.routeservice.model.entity.RoutePhotos;

public interface RoutePhotosMappingHelper {
    static RoutePhotosDto mapToDto(final RoutePhotos routePhotos) {
        return RoutePhotosDto.builder()
                .photoId(routePhotos.getPhotoId())
                .routeDto(
                        RouteDto.builder()
                                .routeId(routePhotos.getRoute().getRouteId())
                                .name(routePhotos.getRoute().getName())
                                .description(routePhotos.getRoute().getDescription())
                                .build())
                .photoLink(routePhotos.getPhotoLink())
                .build();
    }

    static RoutePhotos mapToEntity(RoutePhotosDto routePhotosDto) {
        return RoutePhotos.builder()
                .photoId(routePhotosDto.getPhotoId())
                .route(
                        Route.builder()
                                .routeId(routePhotosDto.getRouteDto().getRouteId())
                                .name(routePhotosDto.getRouteDto().getName())
                                .description(routePhotosDto.getRouteDto().getDescription())
                                .build()
                )
                .photoLink(routePhotosDto.getPhotoLink())
                .build();
    }
}
