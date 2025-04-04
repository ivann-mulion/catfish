package org.cat.fish.vesselsservice.repository;

import org.cat.fish.vesselsservice.model.entity.Vessel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VesselRepository extends JpaRepository<Vessel, Long> {
}
