package org.cat.fish.vesselsservice.service;

import org.cat.fish.vesselsservice.model.dto.request.VesselsDto;

public interface VesselsService {

    VesselsDto findById(final Long id);

    VesselsDto save(final VesselsDto vesselsDto);

    VesselsDto update(final VesselsDto vesselsDto);

    VesselsDto update (final Long id, final VesselsDto vesselsDto);

    void deleteById(final Long id);
}
