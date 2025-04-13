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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class TripServiceImpl implements TripService {

    @Autowired
    private TripRepository tripRepository;

    @Override
    public TripDto findById(Long id) {
        log.info("TripDto, service; fetch trip by id: {}", id);
        return tripRepository.findById(id)
                .map(TripMappingHelper::mapToDto)
                .orElseThrow(() -> new TripNotFoundException(String.format("Trip with id %s not found", id)));
    }

    @Override
    public TripDto save(TripDto tripDto) {
        log.info("TripDto, save trip: {}", tripDto);
        try {
            return TripMappingHelper.mapToDto(tripRepository.save(TripMappingHelper.mapToTrip(tripDto)));
        } catch (DataIntegrityViolationException e) {
            throw new TripNotFoundException("Error saving trip: Data integrity violation", e);
        } catch (Exception e) {
            throw new TripNotFoundException("Error saving trip: ", e);
        }
    }

    @Override
    public TripDto update(TripDto tripDto) {
        log.info("TripDto, service; update trip");
        Trip existingTrip = tripRepository.findById(tripDto.getTripId())
                .orElseThrow(() -> new TripNotFoundException("Trip not found with id: " + tripDto.getVesselId()));

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
        this.tripRepository.deleteById(id);
    }
}
