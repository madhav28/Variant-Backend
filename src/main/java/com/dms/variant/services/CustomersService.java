package com.dms.variant.services;

import com.dms.variant.domain.CustomersEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CustomersService {
    CustomersEntity save(CustomersEntity customersEntity);

    Page<CustomersEntity> findAll(Pageable pageable);

    Optional<CustomersEntity> findOne(long id);

    boolean isExists(long id);

    void delete(long id);
}
