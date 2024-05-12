package com.dms.variant.controllers;

import com.dms.variant.domain.InventoryEntity;
import com.dms.variant.domain.dto.InventoryDto;
import com.dms.variant.mappers.Mapper;
import com.dms.variant.services.InventoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class InventoryController {

    private InventoryService inventoryService;

    private Mapper<InventoryEntity, InventoryDto> inventoryMapper;

    public InventoryController(InventoryService inventoryService, Mapper<InventoryEntity, InventoryDto> inventoryMapper) {
        this.inventoryService = inventoryService;
        this.inventoryMapper = inventoryMapper;
    }

    @PostMapping(path="/inventory")
    public ResponseEntity<InventoryDto> createInventory(@RequestBody InventoryDto inventory) {
        InventoryEntity inventoryEntity = inventoryMapper.mapFrom(inventory);
        InventoryEntity savedInventoryEntity = inventoryService.save(inventoryEntity);
        return new ResponseEntity<>(inventoryMapper.mapTo(savedInventoryEntity), HttpStatus.CREATED);
    }

    @GetMapping(path="/inventories")
    public Page<InventoryDto> listInventories(Pageable pageable) {
        Page<InventoryEntity> inventories = inventoryService.findAll(pageable);
        return inventories.map(inventoryMapper::mapTo);
    }

    @GetMapping(path="/inventory/{id}")
    public ResponseEntity<InventoryDto> getInventory(@PathVariable("id") Integer id) {
        Optional<InventoryEntity> foundInventory = inventoryService.findOne(id);
        return foundInventory.map(inventoryEntity -> {
            InventoryDto inventoryDto = inventoryMapper.mapTo(inventoryEntity);
            return new ResponseEntity<>(inventoryDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path="/inventory/{id}")
    public ResponseEntity<InventoryDto> fullUpdateInventory(@PathVariable("id") Integer id, @RequestBody InventoryDto inventoryDto) {
        if(!inventoryService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        inventoryDto.setId(id);
        InventoryEntity inventoryEntity = inventoryMapper.mapFrom(inventoryDto);
        InventoryEntity savedInventoryEntity = inventoryService.save(inventoryEntity);

        return new ResponseEntity<>(inventoryMapper.mapTo(savedInventoryEntity), HttpStatus.OK);
    }

    @PatchMapping(path="/inventory/{id}")
    public ResponseEntity<InventoryDto> partialUpdateInventory(@PathVariable("id") Integer id, @RequestBody InventoryDto inventoryDto) {
        if(!inventoryService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        InventoryEntity inventoryEntity = inventoryMapper.mapFrom(inventoryDto);
        InventoryEntity updatedInventoryEntity = inventoryService.partitalUpdate(id, inventoryEntity);

        return new ResponseEntity<>(inventoryMapper.mapTo(inventoryEntity), HttpStatus.OK);
    }

    @DeleteMapping(path="/inventory/{id}")
    public ResponseEntity deleteInventory(@PathVariable("id") Integer id) {
        inventoryService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
