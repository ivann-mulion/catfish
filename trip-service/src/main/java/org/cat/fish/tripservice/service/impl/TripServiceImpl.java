package org.cat.fish.tripservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cat.fish.tripservice.exception.wrapper.TripNotFoundException;
import org.cat.fish.tripservice.helper.TripMappingHelper;
import org.cat.fish.tripservice.model.dto.TripDto;
import org.cat.fish.tripservice.model.entity.Trip;
import org.cat.fish.tripservice.repository.TripRepository;
import org.cat.fish.tripservice.service.TripService;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class TripServiceImpl implements TripService {

    private final TripRepository tripRepository;

    @Override
    public List<TripDto> findAll() {
        log.info("TripDto List, service, fetch all trips");
        try {
            return tripRepository.findAll().stream()
                    .map(TripMappingHelper::mapToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error while fetching trips: {}", e.getMessage());
            throw new RuntimeException("Error fetching trips", e);
        }
    }

    @Override
    public TripDto findById(Long id) {
        log.info("TripDto, service; fetch trip by id: {}", id);
        Trip trip = tripRepository.findById(id)
                .orElseThrow(() -> new TripNotFoundException(String.format("Trip with id %s not found", id)));
        return TripMappingHelper.mapToDto(trip);
    }

    @Override
    public TripDto save(TripDto tripDto) {
        log.info("TripDto, save trip: {}", tripDto);
        try {
            Trip trip = TripMappingHelper.mapToTrip(tripDto);
            Trip savedTrip = tripRepository.save(trip);
            return TripMappingHelper.mapToDto(savedTrip);
        } catch (DataIntegrityViolationException e) {
            throw new TripNotFoundException("Error saving trip: Data integrity violation", e);
        } catch (Exception e) {
            throw new TripNotFoundException("Error saving trip: " + e.getMessage(), e);
        }
    }

    @Override
    public TripDto update(TripDto tripDto) {
        log.info("TripDto, service; update trip");
        Trip existingTrip = tripRepository.findById(tripDto.getTripId())
                .orElseThrow(() -> new TripNotFoundException("Trip not found with id: " + tripDto.getTripId()));

        BeanUtils.copyProperties(tripDto, existingTrip, "tripId");
        Trip updatedTrip = tripRepository.save(existingTrip);
        return TripMappingHelper.mapToDto(updatedTrip);
    }

    @Override
    public TripDto update(Long id, TripDto tripDto) {
        Trip existingTrip = tripRepository.findById(id)
                .orElseThrow(() -> new TripNotFoundException(String.format("Trip with id %s not found", id)));

        BeanUtils.copyProperties(tripDto, existingTrip);
        Trip updatedTrip = tripRepository.save(existingTrip);
        return TripMappingHelper.mapToDto(updatedTrip);
    }

    @Override
    public void deleteById(final Long id) {
        log.info("TripDto, service; delete trip with tripId");
        Trip trip = tripRepository.findById(id)
                .orElseThrow(() -> new TripNotFoundException("Trip not found with id: " + id));
        tripRepository.delete(trip);
    }
}