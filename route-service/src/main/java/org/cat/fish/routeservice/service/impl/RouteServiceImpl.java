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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class RouteServiceImpl implements RouteService {

    @Autowired
    private RouteRepository routeRepository;

    @Override
    public RouteDto findById(Long id) {
        log.info("RouteDto, service; fetch product by id");
        return routeRepository.findById(id)
                .map(RouteMappingHelper::mapToDto)
                .orElseThrow();
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
        return null;
    }

    @Override
    public void deleteRoute(Long id) {
        log.info("routeDto, service; update route with productId");
        this.routeRepository.deleteById(id);
    }
}
