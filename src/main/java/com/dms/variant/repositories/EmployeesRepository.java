package com.dms.variant.repositories;

import com.dms.variant.domain.EmployeesEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeesRepository extends CrudRepository<EmployeesEntity, Long>,
        PagingAndSortingRepository<EmployeesEntity, Long> {
}
