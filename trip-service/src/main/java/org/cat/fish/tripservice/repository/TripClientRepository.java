package org.cat.fish.tripservice.repository;

import org.cat.fish.tripservice.model.entity.TripClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TripClientRepository extends JpaRepository<TripClient, Long> {
    Optional<TripClient> findByClientId(Long clientId);

    @Query("SELECT CASE WHEN COUNT(tc) > 0 THEN true ELSE false END " +
            "FROM TripClient tc JOIN tc.trips t " +
            "WHERE tc.clientId = :clientId AND t.tripId = :tripId")
    boolean existsByClientIdAndTripId(@Param("clientId") Long clientId,
                                      @Param("tripId") Long tripId);

    boolean existsByClientId(Long clientId);
}