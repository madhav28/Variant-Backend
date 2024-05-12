package com.dms.variant.repositories;

import com.dms.variant.domain.AssociatesEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssociatesRepository extends CrudRepository<AssociatesEntity, Long>,
        PagingAndSortingRepository<AssociatesEntity, Long> {
}
