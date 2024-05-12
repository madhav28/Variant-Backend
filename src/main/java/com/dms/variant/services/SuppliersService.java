package com.dms.variant.services;

import com.dms.variant.domain.SuppliersEntity;
import com.dms.variant.domain.dto.SuppliersDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SuppliersService {
    SuppliersEntity save(SuppliersEntity suppliersEntity);

    Optional<SuppliersEntity> findOne(long id);

    Page<SuppliersEntity> findAll(Pageable pageable);

    boolean isExists(long id);

    void delete(long id);
}
