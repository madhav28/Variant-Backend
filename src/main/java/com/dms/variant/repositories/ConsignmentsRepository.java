package com.dms.variant.repositories;

import com.dms.variant.domain.ConsignmentsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsignmentsRepository extends CrudRepository<ConsignmentsEntity, Long>,
        PagingAndSortingRepository<ConsignmentsEntity, Long> {
}
