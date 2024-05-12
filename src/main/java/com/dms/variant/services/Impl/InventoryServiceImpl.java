package com.dms.variant.services.Impl;

import com.dms.variant.domain.InventoryEntity;
import com.dms.variant.repositories.InventoryRepository;
import com.dms.variant.services.InventoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class InventoryServiceImpl implements InventoryService {

    private InventoryRepository inventoryRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public InventoryEntity save(InventoryEntity inventoryEntity) {
        return inventoryRepository.save(inventoryEntity);
    }

    @Override
    public List<InventoryEntity> findAll() {
        return StreamSupport.stream(inventoryRepository
                .findAll()
                .spliterator(),
                false)
                .collect(Collectors.toList());
    }

    @Override
    public Page<InventoryEntity> findAll(Pageable pageable) {
        return inventoryRepository.findAll(pageable);
    }

    @Override
    public Optional<InventoryEntity> findOne(Integer id) {
        return inventoryRepository.findById(id);
    }

    @Override
    public boolean isExists(Integer id) {
        return inventoryRepository.existsById(id);
    }

    @Override
    public InventoryEntity partitalUpdate(Integer id, InventoryEntity inventoryEntity) {
        return inventoryEntity;
    }

    @Override
    public void delete(Integer id) {
        inventoryRepository.deleteById(id);
    }
}
