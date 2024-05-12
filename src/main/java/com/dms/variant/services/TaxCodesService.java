package com.dms.variant.services;

import com.dms.variant.domain.TaxCodesEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TaxCodesService {
    TaxCodesEntity save(TaxCodesEntity taxCodesEntity);

    Page<TaxCodesEntity> findAll(Pageable pageable);

    Optional<TaxCodesEntity> findOne(long id);

    boolean isExists(long id);

    void delete(long id);
}
