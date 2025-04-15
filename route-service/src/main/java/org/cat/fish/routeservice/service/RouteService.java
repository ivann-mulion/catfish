package org.cat.fish.routeservice.service;

import org.cat.fish.routeservice.model.dto.request.RouteDto;
import reactor.core.publisher.Flux;

import java.util.List;

public interface RouteService {

    Flux<List<RouteDto>> findAll();

    RouteDto findById(Long id);

    RouteDto createRoute(RouteDto routeDto);

    RouteDto updateRoute(RouteDto routeDto);

    RouteDto updateRoute(Long id, RouteDto routeDto);

    void deleteRoute(Long id);


}
