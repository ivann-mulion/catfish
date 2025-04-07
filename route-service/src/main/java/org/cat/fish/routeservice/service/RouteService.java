package org.cat.fish.routeservice.service;

import org.cat.fish.routeservice.model.dto.request.RouteDto;

public interface RouteService {

    RouteDto findById(Long id);

    RouteDto createRoute(RouteDto routeDto);

    RouteDto updateRoute(RouteDto routeDto);

    RouteDto updateRoute(Long id, RouteDto routeDto);

    void deleteRoute(Long id);


}
