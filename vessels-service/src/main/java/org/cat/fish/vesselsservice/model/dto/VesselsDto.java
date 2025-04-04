package org.cat.fish.vesselsservice.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class VesselsDto {

    private Long id;
    private String vesselName;
    private String imageUrl;
    private String description;
    private Integer capacity;
    private Double enginePower;
    private Integer maintenceIntervalHour;
    private Double boatDraftM;
    private Double waterlineHeightM;
    private LocalDateTime lastMaintenance;
    private Boolean forSale;
    private Integer minPph;
}
