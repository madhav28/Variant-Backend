package com.dms.variant.controllers;

import com.dms.variant.domain.TaxCodesEntity;
import com.dms.variant.domain.dto.TaxCodesDto;
import com.dms.variant.mappers.Mapper;
import com.dms.variant.services.TaxCodesService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class TaxCodesController {

    private TaxCodesService taxCodesService;

    private Mapper<TaxCodesEntity, TaxCodesDto> taxCodesMapper;

    public TaxCodesController(TaxCodesService taxCodesService, Mapper<TaxCodesEntity, TaxCodesDto> taxCodesMapper) {
        this.taxCodesService = taxCodesService;
        this.taxCodesMapper = taxCodesMapper;
    }

    @PostMapping(path = "/taxcodes")
    public ResponseEntity<TaxCodesDto> createTaxCodes(@RequestBody TaxCodesDto taxCodesDto) {
        TaxCodesEntity taxCodesEntity = taxCodesMapper.mapFrom(taxCodesDto);
        return new ResponseEntity<>(taxCodesMapper.mapTo(taxCodesService.save(taxCodesEntity)), HttpStatus.CREATED);
    }

    @GetMapping(path = "/taxcodes")
    public Page<TaxCodesDto> listTaxCodes(Pageable pageable) {
        Page<TaxCodesEntity> taxCodes = taxCodesService.findAll(pageable);
        return taxCodes.map(taxCodesMapper::mapTo);
    }

    @GetMapping(path = "/taxcodes/{id}")
    public ResponseEntity<TaxCodesDto> getTaxCodes(@PathVariable("id") long id) {
        Optional<TaxCodesEntity> foundTaxCodes = taxCodesService.findOne(id);
        return foundTaxCodes.map(taxCodesEntity -> {
            return new ResponseEntity<>(taxCodesMapper.mapTo(taxCodesEntity), HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/taxcodes/{id}")
    public ResponseEntity<TaxCodesDto> fullUpdateTaxCodes(@PathVariable("id") long id, @RequestBody TaxCodesDto taxCodesDto) {
        if(!taxCodesService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        taxCodesDto.setId(id);
        TaxCodesEntity taxCodesEntity = taxCodesMapper.mapFrom(taxCodesDto);
        return new ResponseEntity<>(taxCodesMapper.mapTo(taxCodesService.save(taxCodesEntity)), HttpStatus.OK);
    }

    @PatchMapping(path = "/taxcodes/{id}")
    public ResponseEntity<TaxCodesDto> partialUpdateTaxCodes(@PathVariable("id") long id, @RequestBody TaxCodesDto dto) {
        Optional<TaxCodesEntity> entityOptional = taxCodesService.findOne(id);
        if(entityOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        TaxCodesEntity entity = entityOptional.get();

        taxCodesMapper.updatePartial(entity, dto);
        taxCodesService.save(entity);
        return new ResponseEntity<>(taxCodesMapper.mapTo(entity), HttpStatus.OK);
    }

    @DeleteMapping(path = "/taxcodes/{id}")
    public ResponseEntity deleteTaxCodes(@PathVariable("id") long id) {
        taxCodesService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
