package org.cat.fish.routeservice.service.impl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cat.fish.routeservice.exception.wrapper.RouteNotFoundException;
import org.cat.fish.routeservice.helper.RouteMappingHelper;
import org.cat.fish.routeservice.model.dto.request.RouteDto;
import org.cat.fish.routeservice.model.entity.Route;
import org.cat.fish.routeservice.repository.RouteRepository;
import org.cat.fish.routeservice.service.RouteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

import java.util.List;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class RouteServiceImpl implements RouteService {

    @Autowired
    private RouteRepository routeRepository;

    @Override
    public Flux<List<RouteDto>> findAll() {
        log.info("ProductDto List, service, fetch all products");
        return Flux.defer(() -> {
                    List<RouteDto> routeDto = routeRepository.findAll()
                            .stream()
                            .map(RouteMappingHelper::mapToDto)
                            .distinct()
                            .toList();
                    return Flux.just(routeDto);
                })
                .onErrorResume(throwable -> {
                    log.error("Error while fetching products: " + throwable.getMessage());
                    return Flux.empty();
                });
    }

    @Override
    public RouteDto findById(Long id) {
        log.info("RouteDto, service; fetch product by id");
        return routeRepository.findById(id)
                .map(RouteMappingHelper::mapToDto)
                .orElseThrow(() -> new RouteNotFoundException("Route not found"));
    }

    @Override
    public RouteDto createRoute(RouteDto routeDto) {
        log.info("RouteDto, service, create route");
        try {
            return RouteMappingHelper.mapToDto(routeRepository.save(RouteMappingHelper.mapToEntity(routeDto)));
        }   catch (DataIntegrityViolationException e) {
            throw new RouteNotFoundException("Error saving route: Data integrity violation", e);
        }
        catch (Exception e) {
            throw new RouteNotFoundException("Error saving route: ", e);
        }
    }

    @Override
    public RouteDto updateRoute(RouteDto routeDto) {
        log.info("RouteDto, service; update route");
        Route existingVessel = routeRepository.findById(routeDto.getRouteId())
                .orElseThrow(() -> new RouteNotFoundException("Vessel not found with id: " + routeDto.getRouteId()));

        BeanUtils.copyProperties(routeDto, existingVessel, "routeId");

        Route updatedVessel = routeRepository.save(existingVessel);

        return RouteMappingHelper.mapToDto(updatedVessel);
    }

    @Override
    public RouteDto updateRoute(Long id, RouteDto routeDto) {
        Route existingRoute = routeRepository.findById(id)
                .orElseThrow(() -> new RouteNotFoundException(String.format("Route with id %s not found", id)));

        if (routeDto.getName() != null) {
            existingRoute.setName(routeDto.getName());
        }
        if (routeDto.getDescription() != null) {
            existingRoute.setDescription(routeDto.getDescription());
        }
        Route updatedRoute = routeRepository.save(existingRoute);

        return RouteMappingHelper.mapToDto(updatedRoute);
    }

    @Override
    public void deleteRoute(Long id) {
        log.info("routeDto, service; update route with productId");
        this.routeRepository.deleteById(id);
    }
}
