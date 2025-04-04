package org.cat.fish.vesselsservice.helper;

import org.cat.fish.vesselsservice.model.dto.VesselsDto;
import org.cat.fish.vesselsservice.model.entity.Vessel;

public interface VesselMappingHelper {
    static VesselsDto map(final Vessel vessel) {
        return VesselsDto.builder()
                .id(vessel.getId())
                .vesselName(vessel.getVesselName())
                .imageUrl(vessel.getImageUrl())
                .description(vessel.getDescription())
                .enginePower(vessel.getEnginePower())
                .maintenceIntervalHour(vessel.getMaintenceIntervalHour())
                .boatDraftM(vessel.getBoatDraftM())
                .waterlineHeightM(vessel.getWaterlineHeightM())
                .lastMaintenance(vessel.getLastMaintenance())
                .forSale(vessel.getForSale())
                .minPph(vessel.getMinPph())
                .build();
    }

    static Vessel map(final VesselsDto vesselsDto) {
        return Vessel.builder()
                .id(vesselsDto.getId())
                .vesselName(vesselsDto.getVesselName())
                .imageUrl(vesselsDto.getImageUrl())
                .description(vesselsDto.getDescription())
                .enginePower(vesselsDto.getEnginePower())
                .maintenceIntervalHour(vesselsDto.getMaintenceIntervalHour())
                .boatDraftM(vesselsDto.getBoatDraftM())
                .waterlineHeightM(vesselsDto.getWaterlineHeightM())
                .lastMaintenance(vesselsDto.getLastMaintenance())
                .forSale(vesselsDto.getForSale())
                .minPph(vesselsDto.getMinPph())
                .build();
    }

}
