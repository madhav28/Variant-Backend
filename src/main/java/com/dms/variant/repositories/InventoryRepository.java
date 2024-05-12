package com.dms.variant.repositories;

import com.dms.variant.domain.InventoryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends CrudRepository<InventoryEntity, Integer>,
        PagingAndSortingRepository<InventoryEntity, Integer> {
}
