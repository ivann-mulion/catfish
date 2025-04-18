package org.cat.fish.vesselsservice.service;

import org.cat.fish.vesselsservice.model.dto.request.VesselsDto;
import reactor.core.publisher.Flux;

import java.util.List;

public interface VesselsService {

    Flux<List<VesselsDto>> findAll();

    VesselsDto findById(final Long id);

    VesselsDto save(final VesselsDto vesselsDto);

    VesselsDto update(final VesselsDto vesselsDto);

    VesselsDto update (final Long id, final VesselsDto vesselsDto);

    void deleteById(final Long id);
}
