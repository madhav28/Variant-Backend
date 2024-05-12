package com.dms.variant.controllers;

import com.dms.variant.domain.AssociatesEntity;
import com.dms.variant.domain.dto.AssociatesDto;
import com.dms.variant.mappers.Mapper;
import com.dms.variant.services.AssociatesService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class AssociatesController {

    private AssociatesService associatesService;

    private Mapper<AssociatesEntity, AssociatesDto> associatesMapper;

    public AssociatesController(AssociatesService associatesService, Mapper<AssociatesEntity, AssociatesDto> associatesMapper) {
        this.associatesService = associatesService;
        this.associatesMapper = associatesMapper;
    }

    @PostMapping("/associates")
    public ResponseEntity<AssociatesDto> createAssociates(@RequestBody AssociatesDto associatesDto) {
        AssociatesEntity associatesEntity = associatesMapper.mapFrom(associatesDto);
        return new ResponseEntity<>(associatesMapper.mapTo(associatesService.save(associatesEntity)), HttpStatus.CREATED);
    }

    @GetMapping("/associates")
    public Page<AssociatesDto> listAssociates(Pageable pageable) {
        Page<AssociatesEntity> associates = associatesService.findAll(pageable);
        return associates.map(associatesMapper::mapTo);
    }

    @GetMapping("/associates/{id}")
    public ResponseEntity<AssociatesDto> getAssociates(@PathVariable("id") long id) {
        Optional<AssociatesEntity> foundAssociates = associatesService.findOne(id);
        return foundAssociates.map(associatesEntity -> {
            return new ResponseEntity<>(associatesMapper.mapTo(associatesEntity), HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/associates/{id}")
    public ResponseEntity<AssociatesDto> fullUpdateAssociates(@PathVariable("id") long id, @RequestBody AssociatesDto associatesDto) {
        if(!associatesService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        associatesDto.setId(id);
        AssociatesEntity associatesEntity = associatesMapper.mapFrom(associatesDto);
        return new ResponseEntity<>(associatesMapper.mapTo(associatesService.save(associatesEntity)), HttpStatus.OK);
    }

    @PatchMapping("/associates/{id}")
    public ResponseEntity<AssociatesDto> partialUpdateAssociates(@PathVariable("id") long id, @RequestBody AssociatesDto associatesDto) {
        if(!associatesService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        associatesDto.setId(id);
        AssociatesEntity associatesEntity = associatesMapper.mapFrom(associatesDto);
        return new ResponseEntity<>(associatesMapper.mapTo(associatesEntity),HttpStatus.OK);
    }

    @DeleteMapping("/associates/{id}")
    public ResponseEntity deleteAssociates(@PathVariable("id") long id) {
        associatesService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
