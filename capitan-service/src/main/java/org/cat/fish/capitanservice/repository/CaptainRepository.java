package org.cat.fish.capitanservice.repository;

import org.cat.fish.capitanservice.model.entity.CaptainDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CaptainRepository extends JpaRepository<CaptainDetails, Long> {
    Optional<CaptainDetails> findByUserId(Long userId);
}
