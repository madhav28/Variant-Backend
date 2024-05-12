package com.dms.variant.services;

import com.dms.variant.domain.PackagesEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PackagesService {

    PackagesEntity save(PackagesEntity packagesEntity);

    Page<PackagesEntity> findAll(Pageable pageable);

    Optional<PackagesEntity> findOne(long id);

    boolean isExists(long id);

    void delete(long id);
}
