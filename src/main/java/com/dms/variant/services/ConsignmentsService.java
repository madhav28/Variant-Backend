package com.dms.variant.services;

import com.dms.variant.domain.ConsignmentsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ConsignmentsService {

    ConsignmentsEntity save(ConsignmentsEntity consignmentsEntity);

    Page<ConsignmentsEntity> findAll(Pageable pageable);

    Optional<ConsignmentsEntity> findOne(long id);

    boolean isExists(long id);

    void delete(long id);
}
