package com.dms.variant.controllers;

import com.dms.variant.domain.TaxAuthoritiesEntity;
import com.dms.variant.domain.dto.TaxAuthoritiesDto;
import com.dms.variant.mappers.Mapper;
import com.dms.variant.services.TaxAuthoritiesService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class TaxAuthoritiesController {

    private TaxAuthoritiesService taxAuthoritiesService;

    private Mapper<TaxAuthoritiesEntity, TaxAuthoritiesDto> taxAuthoritiesMapper;

    public TaxAuthoritiesController(TaxAuthoritiesService taxAuthoritiesService, Mapper<TaxAuthoritiesEntity, TaxAuthoritiesDto> taxAuthoritiesMapper) {
        this.taxAuthoritiesService = taxAuthoritiesService;
        this.taxAuthoritiesMapper = taxAuthoritiesMapper;
    }

    @PostMapping(path = "/taxauthorities")
    public ResponseEntity<TaxAuthoritiesDto> createTaxAuthorities(@RequestBody TaxAuthoritiesDto taxAuthoritiesDto) {
        TaxAuthoritiesEntity taxAuthoritiesEntity = taxAuthoritiesMapper.mapFrom(taxAuthoritiesDto);
        return new ResponseEntity<>(taxAuthoritiesMapper.mapTo(taxAuthoritiesService.save(taxAuthoritiesEntity)), HttpStatus.CREATED);
    }

    @GetMapping(path = "/taxauthorities")
    public Page<TaxAuthoritiesDto> listTaxAuthorities(Pageable pageable) {
        Page<TaxAuthoritiesEntity> taxAuthorities = taxAuthoritiesService.findAll(pageable);
        return taxAuthorities.map(taxAuthoritiesMapper::mapTo);
    }

    @GetMapping(path = "/taxauthorities/{id}")
    public ResponseEntity<TaxAuthoritiesDto> getTaxAuthorities(@PathVariable("id") long id) {
        Optional<TaxAuthoritiesEntity> foundTaxAuthorities = taxAuthoritiesService.findOne(id);
        return foundTaxAuthorities.map(taxAuthoritiesEntity -> {
            return new ResponseEntity<>(taxAuthoritiesMapper.mapTo(taxAuthoritiesEntity), HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/taxauthorities/{id}")
    public ResponseEntity<TaxAuthoritiesDto> fullUpdateTaxAuthorities(@PathVariable("id") long id, @RequestBody TaxAuthoritiesDto taxAuthoritiesDto) {
        if(!taxAuthoritiesService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        taxAuthoritiesDto.setId(id);
        TaxAuthoritiesEntity taxAuthoritiesEntity = taxAuthoritiesMapper.mapFrom(taxAuthoritiesDto);
        return new ResponseEntity<>(taxAuthoritiesMapper.mapTo(taxAuthoritiesService.save(taxAuthoritiesEntity)), HttpStatus.OK);
    }

    @PatchMapping(path = "/taxauthorities/{id}")
    public ResponseEntity<TaxAuthoritiesDto> partialUpdateTaxAuthorities(@PathVariable("id") long id, @RequestBody TaxAuthoritiesDto taxAuthoritiesDto) {
        if(!taxAuthoritiesService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        taxAuthoritiesDto.setId(id);
        TaxAuthoritiesEntity taxAuthoritiesEntity = taxAuthoritiesMapper.mapFrom(taxAuthoritiesDto);
        return new ResponseEntity<>(taxAuthoritiesMapper.mapTo(taxAuthoritiesEntity), HttpStatus.OK);
    }

    @DeleteMapping(path = "/taxauthorities/{id}")
    public ResponseEntity deleteTaxAuthorities(@PathVariable("id") long id) {
        taxAuthoritiesService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
