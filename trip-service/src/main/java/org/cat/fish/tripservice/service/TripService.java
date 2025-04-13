package org.cat.fish.tripservice.service;

import org.cat.fish.tripservice.model.dto.TripDto;

public interface TripService {
    TripDto findById(final Long id);

    TripDto save(final TripDto vesselsDto);

    TripDto update(final TripDto vesselsDto);

    TripDto update (final Long id, final TripDto vesselsDto);

    void deleteById(final Long id);
}
