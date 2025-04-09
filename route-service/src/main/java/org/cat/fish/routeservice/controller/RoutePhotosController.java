package org.cat.fish.routeservice.controller;

import lombok.RequiredArgsConstructor;
import org.cat.fish.routeservice.model.dto.request.RoutePhotosDto;
import org.cat.fish.routeservice.service.RoutePhotoService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/routes/{routeId}/photos")
@RequiredArgsConstructor
public class RoutePhotosController {

    private final RoutePhotoService routePhotosService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RoutePhotosDto> uploadPhoto(
            @PathVariable Long routeId,
            @RequestParam("photo") MultipartFile photo) {

        RoutePhotosDto photoDto = routePhotosService.createPhotoForRoute(routeId, photo);
        return ResponseEntity.status(HttpStatus.CREATED).body(photoDto);
    }

    @GetMapping("/{photoId}/content")
    public ResponseEntity<byte[]> getPhotoContent(
            @PathVariable Long routeId,
            @PathVariable Long photoId) {

        byte[] content = routePhotosService.getPhotoContent(photoId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE)
                .body(content);
    }

    @GetMapping
    public ResponseEntity<List<RoutePhotosDto>> getRoutePhotos(@PathVariable Long routeId) {
        List<RoutePhotosDto> photos = routePhotosService.findAllPhotoByRouteId(routeId);
        return ResponseEntity.ok(photos);
    }

    @GetMapping("/{photoId}")
    public ResponseEntity<RoutePhotosDto> getPhoto(@PathVariable Long routeId,
                                                   @PathVariable Long photoId) {
        RoutePhotosDto photo = routePhotosService.findById(photoId);
        return ResponseEntity.ok(photo);
    }
}
