package org.cat.fish.routeservice.service;

import org.cat.fish.routeservice.model.dto.request.RoutePhotosDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RoutePhotoService {

    RoutePhotosDto findById(Long id);
    List<RoutePhotosDto> findAllPhotoByRouteId(Long routeId);
    byte[] getPhotoContent(Long photoId);
    RoutePhotosDto createPhoto(RoutePhotosDto routePhotosDto);
    RoutePhotosDto createPhotoForRoute(Long routeId, RoutePhotosDto routePhotosDto);
    RoutePhotosDto createPhotoForRoute(Long routeId, MultipartFile photoFile);
    RoutePhotosDto updatePhoto(Long id, RoutePhotosDto routePhotosDto);
    void deletePhoto(Long id);
    void deletePhotosForRoute(Long routeId);
}
