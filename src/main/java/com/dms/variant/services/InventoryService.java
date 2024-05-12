package com.dms.variant.services;

import com.dms.variant.domain.InventoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface InventoryService {
    InventoryEntity save(InventoryEntity inventory);

    List<InventoryEntity> findAll();

    Page<InventoryEntity> findAll(Pageable pageable);

    Optional<InventoryEntity> findOne(Integer id);

    boolean isExists(Integer id);

    InventoryEntity partitalUpdate(Integer id, InventoryEntity inventoryEntity);

    void delete(Integer id);
}
