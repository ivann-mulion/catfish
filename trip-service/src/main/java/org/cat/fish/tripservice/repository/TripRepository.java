package org.cat.fish.tripservice.repository;

import org.cat.fish.tripservice.model.entity.Trip;
import org.cat.fish.tripservice.model.entity.TripClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    @Query("SELECT t FROM Trip t JOIN t.clients c WHERE c.clientId = :clientId")
    List<Trip> findByClientId(@Param("clientId") Long clientId);

    Optional<Trip> findByTripId(Long tripId);
}