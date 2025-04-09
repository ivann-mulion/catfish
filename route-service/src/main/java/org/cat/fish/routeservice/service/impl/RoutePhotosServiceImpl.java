package org.cat.fish.routeservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cat.fish.routeservice.client.MediaServiceClient;
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
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
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

    @Autowired
    private MediaServiceClient mediaServiceClient;

    @Override
    public RoutePhotosDto findById(Long id) {
        RoutePhotos photo = routePhotosRepository.findById(id)
                .orElseThrow(() -> new RoutePhotoNotFoundException("Photo not found"));
        return RoutePhotosMappingHelper.mapToDto(photo);
    }

    @Override
    public List<RoutePhotosDto> findAllPhotoByRouteId(Long routeId) {
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

    @Override
    public byte[] getPhotoContent(Long photoId) {
        log.info("Getting photo content for ID: {}", photoId);

        RoutePhotos photo = routePhotosRepository.findById(photoId)
                .orElseThrow(() -> new RoutePhotoNotFoundException("Photo not found with id: " + photoId));

        try {
            byte[] content = mediaServiceClient.downloadPhoto(photo.getPhotoLink());
            log.info("Successfully downloaded photo content for ID: {}", photoId);
            return content;

        } catch (Exception e) {
            log.error("Failed to download photo content for ID {}: {}", photoId, e.getMessage());
            throw new RuntimeException("Failed to download photo: " + e.getMessage(), e);
        }
    }

    @Override
    public RoutePhotosDto createPhotoForRoute(Long routeId, MultipartFile photoFile) {
        log.info("Creating photo for route ID: {}", routeId);

        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new RouteNotFoundException("Route not found with id: " + routeId));

        try {
            String photoKey = mediaServiceClient.uploadPhoto(photoFile);
            log.info("Photo uploaded successfully. Key: {}", photoKey);

            RoutePhotos routePhoto = RoutePhotos.builder()
                    .photoLink(photoKey) // Ключ из S3
                    .route(route)
                    .originalFileName(photoFile.getOriginalFilename())
                    .fileSize(photoFile.getSize())
                    .contentType(photoFile.getContentType())
                    .uploadedAt(LocalDateTime.now())
                    .build();

            RoutePhotos savedPhoto = routePhotosRepository.save(routePhoto);
            log.info("Photo saved to database with ID: {}", savedPhoto.getPhotoId());

            return RoutePhotosMappingHelper.mapToDto(savedPhoto);

        } catch (Exception e) {
            log.error("Failed to create photo for route {}: {}", routeId, e.getMessage());
            throw new RuntimeException("Failed to upload photo: " + e.getMessage(), e);
        }
    }
}
