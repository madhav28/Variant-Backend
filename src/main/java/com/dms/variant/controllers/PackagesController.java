package com.dms.variant.controllers;

import com.dms.variant.domain.PackagesEntity;
import com.dms.variant.domain.dto.PackagesDto;
import com.dms.variant.mappers.Mapper;
import com.dms.variant.services.PackagesService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class PackagesController {

    private PackagesService packagesService;

    private Mapper<PackagesEntity, PackagesDto> packagesMapper;

    public PackagesController(PackagesService packagesService, Mapper<PackagesEntity, PackagesDto> packagesMapper) {
        this.packagesService = packagesService;
        this.packagesMapper = packagesMapper;
    }

    @PostMapping(path = "/packages")
    public ResponseEntity<PackagesDto> createPackages(@RequestBody PackagesDto packagesDto) {
        PackagesEntity packagesEntity = packagesMapper.mapFrom(packagesDto);
        return new ResponseEntity<>(packagesMapper.mapTo(packagesService.save(packagesEntity)), HttpStatus.CREATED);
    }

    @GetMapping(path = "/packages")
    public Page<PackagesDto> listPackages(Pageable pageable) {
        Page<PackagesEntity> packages = packagesService.findAll(pageable);
        return packages.map(packagesMapper::mapTo);
    }

    @GetMapping(path = "/packages/{id}")
    public ResponseEntity<PackagesDto> getPackages(@PathVariable("id") long id) {
        Optional<PackagesEntity> foundPackages = packagesService.findOne(id);
        return foundPackages.map(packagesEntity -> {
            return new ResponseEntity<>(packagesMapper.mapTo(packagesEntity), HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/packages/{id}")
    public ResponseEntity<PackagesDto> fullUpdatePackages(@PathVariable("id") long id, @RequestBody PackagesDto packagesDto) {
        if(!packagesService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        packagesDto.setId(id);
        PackagesEntity packagesEntity = packagesMapper.mapFrom(packagesDto);
        return new ResponseEntity<>(packagesMapper.mapTo(packagesService.save(packagesEntity)), HttpStatus.OK);
    }

    @PatchMapping(path = "/packages/{id}")
    public ResponseEntity<PackagesDto> partialUpdatePackages(@PathVariable("id") long id, @RequestBody PackagesDto dto) {
        Optional<PackagesEntity> entityOptional = packagesService.findOne(id);
        if(entityOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        PackagesEntity entity = entityOptional.get();

        packagesMapper.updatePartial(entity, dto);
        packagesService.save(entity);
        return new ResponseEntity<>(packagesMapper.mapTo(entity), HttpStatus.OK);
    }

    @DeleteMapping(path = "/packages/{id}")
    public ResponseEntity deletePackages(@PathVariable("id") long id) {
        packagesService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
