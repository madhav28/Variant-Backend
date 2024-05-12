package com.dms.variant.repositories;

import com.dms.variant.domain.VendorsEntity;
import com.dms.variant.domain.dto.VendorsDto;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorsRespository extends CrudRepository<VendorsEntity, Long>,
        PagingAndSortingRepository<VendorsEntity, Long> {
}
