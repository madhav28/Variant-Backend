package com.dms.variant.controllers;

import com.dms.variant.domain.SuppliersEntity;
import com.dms.variant.domain.dto.SuppliersDto;
import com.dms.variant.mappers.Mapper;
import com.dms.variant.services.SuppliersService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class SuppliersController {

    private SuppliersService suppliersService;

    private Mapper<SuppliersEntity, SuppliersDto> suppliersMapper;

    public SuppliersController(SuppliersService suppliersService, Mapper<SuppliersEntity, SuppliersDto> suppliersMapper) {
        this.suppliersService = suppliersService;
        this.suppliersMapper = suppliersMapper;
    }

    @PostMapping(path = "/suppliers")
    public ResponseEntity<SuppliersDto> createSuppliers(@RequestBody SuppliersDto suppliersDto) {
        SuppliersEntity suppliersEntity = suppliersMapper.mapFrom(suppliersDto);
        return new ResponseEntity<>(suppliersMapper.mapTo(suppliersService.save(suppliersEntity)), HttpStatus.CREATED);
    }

    @GetMapping(path = "/suppliers")
    public Page<SuppliersDto> listSuppliers(Pageable pageable) {
        Page<SuppliersEntity> suppliers = suppliersService.findAll(pageable);
        return suppliers.map(suppliersMapper::mapTo);
    }

    @GetMapping(path = "/suppliers/{id}")
    public ResponseEntity<SuppliersDto> getSuppliers(@PathVariable("id") long id) {
        Optional<SuppliersEntity> foundSuppliers = suppliersService.findOne(id);
        return foundSuppliers.map(suppliersEntity -> {
            return new ResponseEntity<>(suppliersMapper.mapTo(suppliersEntity), HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/suppliers/{id}")
    public ResponseEntity<SuppliersDto> fullUpdateSuppliers(@PathVariable("id") long id, @RequestBody SuppliersDto suppliersDto) {
        if(!suppliersService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        suppliersDto.setId(id);
        SuppliersEntity suppliersEntity = suppliersMapper.mapFrom(suppliersDto);
        return new ResponseEntity<>(suppliersMapper.mapTo(suppliersService.save(suppliersEntity)), HttpStatus.OK);
    }

    @PatchMapping(path = "/suppliers/{id}")
    public ResponseEntity<SuppliersDto> partialUpdateSuppliers(@PathVariable("id") long id, @RequestBody SuppliersDto suppliersDto) {
        if(!suppliersService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        SuppliersEntity suppliersEntity = suppliersMapper.mapFrom(suppliersDto);
        return new ResponseEntity<>(suppliersMapper.mapTo(suppliersEntity), HttpStatus.OK);
    }

    @DeleteMapping(path = "/suppliers/{id}")
    public ResponseEntity deleteSuppliers(@PathVariable("id") long id) {
        suppliersService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
