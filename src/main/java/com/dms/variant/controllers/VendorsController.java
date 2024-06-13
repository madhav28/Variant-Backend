package com.dms.variant.controllers;

import com.dms.variant.domain.VendorsEntity;
import com.dms.variant.domain.dto.VendorsDto;
import com.dms.variant.mappers.Mapper;
import com.dms.variant.services.VendorsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class VendorsController {

    private VendorsService vendorsService;

    private Mapper<VendorsEntity, VendorsDto> vendorsMapper;

    public VendorsController(VendorsService vendorsService, Mapper<VendorsEntity, VendorsDto> vendorsMapper) {
        this.vendorsService = vendorsService;
        this.vendorsMapper = vendorsMapper;
    }

    @PostMapping(path = "/vendors")
    public ResponseEntity<VendorsDto> createVendors(@RequestBody VendorsDto vendorsDto) {
        VendorsEntity vendorsEntity = vendorsMapper.mapFrom(vendorsDto);
        return new ResponseEntity<>(vendorsMapper.mapTo(vendorsService.save(vendorsEntity)), HttpStatus.CREATED);
    }

    @GetMapping(path = "/vendors")
    public Page<VendorsDto> listVendors(Pageable pageable) {
        Page<VendorsEntity> vendors = vendorsService.findAll(pageable);
        return vendors.map(vendorsMapper::mapTo);
    }

    @GetMapping(path = "/vendors/{id}")
    public ResponseEntity<VendorsDto> getVendors(@PathVariable("id") long id) {
        Optional<VendorsEntity> foundVendors = vendorsService.findOne(id);
        return foundVendors.map(vendorsEntity -> {
            return new ResponseEntity<>(vendorsMapper.mapTo(vendorsEntity), HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/vendors/{id}")
    public ResponseEntity<VendorsDto> fullUpdateVendors(@PathVariable("id") long id, @RequestBody VendorsDto vendorsDto) {
        if(!vendorsService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        vendorsDto.setId(id);
        VendorsEntity vendorsEntity = vendorsMapper.mapFrom(vendorsDto);
        return new ResponseEntity<>(vendorsMapper.mapTo(vendorsService.save(vendorsEntity)), HttpStatus.OK);
    }

    @PatchMapping(path = "/vendors/{id}")
    public ResponseEntity<VendorsDto> partialUpdateVendors(@PathVariable("id") long id, @RequestBody VendorsDto dto) {
        Optional<VendorsEntity> entityOptional = vendorsService.findOne(id);
        if(entityOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        VendorsEntity entity = entityOptional.get();

        vendorsMapper.updatePartial(entity, dto);
        vendorsService.save(entity);
        return new ResponseEntity<>(vendorsMapper.mapTo(entity), HttpStatus.OK);
    }

    @DeleteMapping(path = "/vendors/{id}")
    public ResponseEntity deleteVendors(@PathVariable("id") long id) {
        vendorsService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
