package org.cat.fish.routeservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cat.fish.routeservice.exception.wrapper.RouteNotFoundException;
import org.cat.fish.routeservice.exception.wrapper.RoutePhotoNotFoundException;
import org.cat.fish.routeservice.helper.RoutePhotosMappingHelper;
import org.cat.fish.routeservice.model.dto.request.RoutePhotosDto;
import org.cat.fish.routeservice.model.entity.Route;
import org.cat.fish.routeservice.model.entity.RoutePhotos;
import org.cat.fish.routeservice.repository.RoutePhotosRepository;
import org.cat.fish.routeservice.repository.RouteRepository;
import org.cat.fish.routeservice.service.RoutePhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class RoutePhotosServiceImpl implements RoutePhotoService {

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private RoutePhotosRepository routePhotosRepository;

    @Override
    public RoutePhotosDto findById(Long id) {
        log.info("RoutePhotosService: fetch photo by id {}", id);
        return routePhotosRepository.findById(id)
                .map(RoutePhotosMappingHelper::mapToDto)
                .orElseThrow(() -> new RoutePhotoNotFoundException("Photo not found with id " + id));
    }

    @Override
    public List<RoutePhotosDto> findAllPhotoByRouteId(Long routeId) {
        log.info("RoutePhotosService: fetch photos by route id {}", routeId);
        if (!routeRepository.existsById(routeId)) {
            throw new RoutePhotoNotFoundException("Photo not found with id " + routeId);
        }

        return routePhotosRepository.findByRouteRouteId(routeId).stream()
                .map(RoutePhotosMappingHelper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public RoutePhotosDto createPhoto(RoutePhotosDto photoDto) {
        log.info("RoutePhotosService: create new photo");
        try {
            Route route = routeRepository.findById(photoDto.getRouteDto().getRouteId())
                    .orElseThrow(() -> new RouteNotFoundException("Route not found with id: " + photoDto.getRouteDto().getRouteId()));

            RoutePhotos photo = RoutePhotos.builder()
                    .photoLink(photoDto.getPhotoLink())
                    .route(route)
                    .build();

            RoutePhotos savedPhoto = routePhotosRepository.save(photo);
            return RoutePhotosMappingHelper.mapToDto(savedPhoto);

        } catch (DataIntegrityViolationException e) {
            throw new RouteNotFoundException("Error saving photo: Data integrity violation", e);
        } catch (Exception e) {
            throw new RouteNotFoundException("Error saving photo: ", e);
        }
    }

    @Override
    public RoutePhotosDto createPhotoForRoute(Long routeId, RoutePhotosDto photoDto) {
        log.info("RoutePhotosService: create photo for route id {}", routeId);

        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new RouteNotFoundException("Route not found with id: " + routeId));

        RoutePhotos photo = RoutePhotos.builder()
                .photoLink(photoDto.getPhotoLink())
                .route(route)
                .build();

        RoutePhotos savedPhoto = routePhotosRepository.save(photo);
        return RoutePhotosMappingHelper.mapToDto(savedPhoto);
    }

    @Override
    public RoutePhotosDto updatePhoto(Long id, RoutePhotosDto photoDto) {
        log.info("RoutePhotosService: update photo with id {}", id);

        RoutePhotos existingPhoto = routePhotosRepository.findById(id)
                .orElseThrow(() -> new RoutePhotoNotFoundException("Photo not found with id: " + id));

        existingPhoto.setPhotoLink(photoDto.getPhotoLink());

        if (photoDto.getRouteDto().getRouteId() != null &&
                !existingPhoto.getRoute().getRouteId().equals(photoDto.getRouteDto().getRouteId())) {
            Route newRoute = routeRepository.findById(photoDto.getRouteDto().getRouteId())
                    .orElseThrow(() -> new RouteNotFoundException("Route not found with id: " + photoDto.getRouteDto().getRouteId()));
            existingPhoto.setRoute(newRoute);
        }

        RoutePhotos updatedPhoto = routePhotosRepository.save(existingPhoto);
        return RoutePhotosMappingHelper.mapToDto(updatedPhoto);
    }

    @Override
    public void deletePhoto(Long id) {
        log.info("RoutePhotosService: delete photo with id {}", id);

        if (!routePhotosRepository.existsById(id)) {
            throw new RoutePhotoNotFoundException("Photo not found with id: " + id);
        }

        routePhotosRepository.deleteById(id);
    }

    @Override
    public void deletePhotosForRoute(Long routeId) {
        log.info("RoutePhotosService: delete all photos for route id {}", routeId);

        if (!routeRepository.existsById(routeId)) {
            throw new RouteNotFoundException("Route not found with id: " + routeId);
        }

        routePhotosRepository.deleteByRouteRouteId(routeId);
    }
}
