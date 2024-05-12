package com.dms.variant.repositories;

import com.dms.variant.domain.TaxAuthoritiesEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxAuthoritiesRepository extends CrudRepository<TaxAuthoritiesEntity, Long>,
        PagingAndSortingRepository<TaxAuthoritiesEntity, Long> {
}
