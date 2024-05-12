package com.dms.variant.controllers;

import com.dms.variant.domain.ConsignmentsEntity;
import com.dms.variant.domain.dto.ConsignmentsDto;
import com.dms.variant.mappers.Mapper;
import com.dms.variant.services.ConsignmentsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ConsignmentsController {

    private ConsignmentsService consignmentsService;

    private Mapper<ConsignmentsEntity, ConsignmentsDto> consignmentsMapper;

    public ConsignmentsController(ConsignmentsService consignmentsService, Mapper<ConsignmentsEntity, ConsignmentsDto> consignmentsMapper) {
        this.consignmentsService = consignmentsService;
        this.consignmentsMapper = consignmentsMapper;
    }

    @PostMapping("/consignments")
    public ResponseEntity<ConsignmentsDto> createConsignments(@RequestBody ConsignmentsDto consignmentsDto) {
        ConsignmentsEntity consignmentsEntity = consignmentsMapper.mapFrom(consignmentsDto);
        return new ResponseEntity<>(consignmentsMapper.mapTo(consignmentsService.save(consignmentsEntity)), HttpStatus.CREATED);
    }

    @GetMapping("/consignments")
    public Page<ConsignmentsDto> listConsignments(Pageable pageable) {
        Page<ConsignmentsEntity> consignments = consignmentsService.findAll(pageable);
        return consignments.map(consignmentsMapper::mapTo);
    }

    @GetMapping("/consignments/{id}")
    public ResponseEntity<ConsignmentsDto> getConsignments(@PathVariable("id") long id) {
        Optional<ConsignmentsEntity> foundConsignments = consignmentsService.findOne(id);
        return foundConsignments.map(consignmentsEntity -> {
            return new ResponseEntity<>(consignmentsMapper.mapTo(consignmentsEntity), HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/consignments/{id}")
    public ResponseEntity<ConsignmentsDto> updateFullConsignments(@PathVariable("id") long id, @RequestBody ConsignmentsDto consignmentsDto) {
        if(!consignmentsService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        consignmentsDto.setId(id);
        ConsignmentsEntity consignmentsEntity = consignmentsMapper.mapFrom(consignmentsDto);
        return new ResponseEntity<>(consignmentsMapper.mapTo(consignmentsService.save(consignmentsEntity)), HttpStatus.OK);
    }

    @PatchMapping("/consignments/{id}")
    public ResponseEntity<ConsignmentsDto> partialUpdateConsignments(@PathVariable("id") long id, @RequestBody ConsignmentsDto consignmentsDto) {
        if(!consignmentsService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        consignmentsDto.setId(id);
        ConsignmentsEntity consignmentsEntity = consignmentsMapper.mapFrom(consignmentsDto);
        return new ResponseEntity<>(consignmentsMapper.mapTo(consignmentsEntity), HttpStatus.OK);
    }

    @DeleteMapping("/consignments/{id}")
    public ResponseEntity deleteConsignments(@PathVariable("id") long id) {
        consignmentsService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
