package com.dms.variant.services;

import com.dms.variant.domain.VendorsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface VendorsService {
    VendorsEntity save(VendorsEntity vendorsEntity);

    Page<VendorsEntity> findAll(Pageable pageable);

    Optional<VendorsEntity> findOne(long id);

    boolean isExists(long id);

    void delete(long id);
}
