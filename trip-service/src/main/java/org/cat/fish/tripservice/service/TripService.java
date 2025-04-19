package org.cat.fish.tripservice.service;

import org.cat.fish.tripservice.model.dto.TripDto;
import java.util.List;

public interface TripService {
    List<TripDto> findAll();
    TripDto findById(Long id);
    TripDto save(TripDto tripDto);
    TripDto update(TripDto tripDto);
    TripDto update(Long id, TripDto tripDto);
    void deleteById(Long id);
}