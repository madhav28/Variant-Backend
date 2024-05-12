package com.dms.variant.repositories;

import com.dms.variant.domain.TaxCodesEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxCodesRepository extends CrudRepository<TaxCodesEntity, Long>,
        PagingAndSortingRepository<TaxCodesEntity, Long> {
}
