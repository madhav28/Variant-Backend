package com.dms.variant.services;

import com.dms.variant.domain.TaxAuthoritiesEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TaxAuthoritiesService {
    TaxAuthoritiesEntity save(TaxAuthoritiesEntity taxAuthoritiesEntity);

    Page<TaxAuthoritiesEntity> findAll(Pageable pageable);

    Optional<TaxAuthoritiesEntity> findOne(long id);

    boolean isExists(long id);

    void delete(long id);
}
