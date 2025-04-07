package org.cat.fish.vesselsservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cat.fish.vesselsservice.exception.wrapper.VesselNotFoundException;
import org.cat.fish.vesselsservice.helper.VesselMappingHelper;
import org.cat.fish.vesselsservice.model.dto.request.VesselsDto;
import org.cat.fish.vesselsservice.model.entity.Vessel;
import org.cat.fish.vesselsservice.repository.VesselRepository;
import org.cat.fish.vesselsservice.service.VesselsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class VesselsServiceImpl implements VesselsService {

    @Autowired
    private VesselRepository vesselRepository;

    @Override
    public VesselsDto findById(Long id) {
        log.info("VesselsDto, service; fetch vessel by id");
        return vesselRepository.findById(id)
                .map(VesselMappingHelper::map)
                .orElseThrow(() -> new VesselNotFoundException(String.format("Vessel with id %s not found", id)));
    }

    @Override
    public VesselsDto save(VesselsDto vesselsDto) {
        log.info("VesselsDto, service; save vessel");
        try{
            return VesselMappingHelper.map(vesselRepository.save(VesselMappingHelper.map(vesselsDto)));
        } catch (DataIntegrityViolationException e) {
            throw new VesselNotFoundException("Error saving vessel: Data integrity violation", e);
        } catch (Exception e) {
            throw new VesselNotFoundException("Error saving vessel: ", e);
        }

    }

    @Override
    public VesselsDto update(VesselsDto vesselsDto) {
        log.info("VesselsDto, service; update vessel");
        Vessel existingVessel = vesselRepository.findById(vesselsDto.getVesselId())
                .orElseThrow(() -> new VesselNotFoundException("Vessel not found with id: " + vesselsDto.getVesselId()));

        BeanUtils.copyProperties(vesselsDto, existingVessel, "vesselId");

        Vessel updatedVessel = vesselRepository.save(existingVessel);

        return VesselMappingHelper.map(updatedVessel);
    }

    @Override
    public VesselsDto update(Long id, VesselsDto updateDto) {
        Vessel existingVessel = vesselRepository.findById(id)
                .orElseThrow(() -> new VesselNotFoundException(String.format("Vessel with id %s not found", id)));

        BeanUtils.copyProperties(updateDto, existingVessel);

        Vessel updatedVessel = vesselRepository.save(existingVessel);

        return VesselMappingHelper.map(updatedVessel);
    }

    @Override
    public void deleteById(Long id) {
        log.info("VesselsDto, service; delete vessel with vesselId");
        this.vesselRepository.deleteById(id);
    }
}
