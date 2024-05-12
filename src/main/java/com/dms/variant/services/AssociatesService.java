package com.dms.variant.services;

import com.dms.variant.domain.AssociatesEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AssociatesService {
    AssociatesEntity save(AssociatesEntity associatesEntity);

    Page<AssociatesEntity> findAll(Pageable pageable);

    Optional<AssociatesEntity> findOne(long id);

    boolean isExists(long id);

    void delete(long id);
}
