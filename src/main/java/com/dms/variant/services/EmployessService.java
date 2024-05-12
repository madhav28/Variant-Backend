package com.dms.variant.services;

import com.dms.variant.domain.EmployeesEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface EmployessService {
    EmployeesEntity save(EmployeesEntity employeesEntity);

    Page<EmployeesEntity> findAll(Pageable pageable);

    Optional<EmployeesEntity> findOne(long id);

    boolean isExists(long id);

    void delete(long id);
}
