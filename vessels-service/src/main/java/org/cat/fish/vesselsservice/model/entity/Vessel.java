package org.cat.fish.vesselsservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "vessels")
public class Vessel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id;

    @Column(name = "boat_name")
    private String vesselName;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "description")
    private String description;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "engine_power")
    private Double enginePower;

    @Column(name = "maintence_interval_hour")
    private Integer maintenceIntervalHour;

    @Column(name = "boat_draft_m")
    private Double boatDraftM;

    @Column(name = "waterline_height_m")
    private Double waterlineHeightM;

    @Column(name = "last_maintenance")
    private LocalDateTime lastMaintenance;

    @Column(name = "for_sale")
    private Boolean forSale;

    @Column(name = "min_pph")
    private Integer minPph;
}
