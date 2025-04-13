package org.cat.fish.tripservice.helper;


import org.cat.fish.tripservice.model.dto.TripDto;
import org.cat.fish.tripservice.model.entity.Trip;

public interface TripMappingHelper {
    static TripDto mapToDto(final Trip trip) {
        return TripDto.builder()
                .tripId(trip.getTripId())
                .vesselId(trip.getVesselId())
                .userId(trip.getUserId())
                .departureTime(trip.getDepartureTime())
                .arrivalTime(trip.getArrivalTime())
                .routeId(trip.getRouteId())
                .createdAt(trip.getCreatedAt())
                .updatedAt(trip.getUpdatedAt())
                .price(trip.getPrice())
                .payment(trip.getPayment())
                .status(trip.getStatus())
                .build();
    }

    static Trip mapToTrip(final TripDto tripDto) {
        return Trip.builder()
                .tripId(tripDto.getTripId())
                .vesselId(tripDto.getVesselId())
                .userId(tripDto.getUserId())
                .departureTime(tripDto.getDepartureTime())
                .arrivalTime(tripDto.getArrivalTime())
                .routeId(tripDto.getRouteId())
                .createdAt(tripDto.getCreatedAt())
                .updatedAt(tripDto.getUpdatedAt())
                .price(tripDto.getPrice())
                .payment(tripDto.getPayment())
                .status(tripDto.getStatus())
                .build();
    }
}
