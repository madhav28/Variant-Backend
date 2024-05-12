package com.dms.variant.repositories;

import com.dms.variant.domain.CustomersEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomersRepository extends CrudRepository<CustomersEntity, Long>,
        PagingAndSortingRepository<CustomersEntity, Long> {
}
