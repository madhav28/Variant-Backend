package com.dms.variant.repositories;

import com.dms.variant.domain.ServicesEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicesRepository extends CrudRepository<ServicesEntity, Long>,
        PagingAndSortingRepository<ServicesEntity, Long> {
}
