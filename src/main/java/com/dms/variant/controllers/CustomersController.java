package com.dms.variant.controllers;

import com.dms.variant.domain.CustomersEntity;
import com.dms.variant.domain.dto.CustomersDto;
import com.dms.variant.mappers.Mapper;
import com.dms.variant.services.CustomersService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class CustomersController {

    private CustomersService customersService;

    private Mapper<CustomersEntity, CustomersDto> customersMapper;

    public CustomersController(CustomersService customersService, Mapper<CustomersEntity, CustomersDto> customersMapper) {
        this.customersService = customersService;
        this.customersMapper = customersMapper;
    }

    @PostMapping(path = "/customers")
    public ResponseEntity<CustomersDto> createCustomers(@RequestBody CustomersDto customersDto) {
        CustomersEntity customersEntity = customersMapper.mapFrom(customersDto);
        return new ResponseEntity<>(customersMapper.mapTo(customersService.save(customersEntity)), HttpStatus.CREATED);
    }

    @GetMapping(path = "/customers")
    public Page<CustomersDto> listCustomers(Pageable pageable) {
        Page<CustomersEntity> customers = customersService.findAll(pageable);
        return customers.map(customersMapper::mapTo);
    }

    @GetMapping(path = "/customers/{id}")
    public ResponseEntity<CustomersDto> getCustomers(@PathVariable("id") long id) {
        Optional<CustomersEntity> foundCustomers = customersService.findOne(id);
        return foundCustomers.map(customersEntity -> {
            return new ResponseEntity<>(customersMapper.mapTo(customersEntity), HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/customers/{id}")
    public ResponseEntity<CustomersDto> fullUpdateCustomers(@PathVariable("id") long id, @RequestBody CustomersDto customersDto) {
        if(!customersService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        customersDto.setId(id);
        CustomersEntity customersEntity = customersMapper.mapFrom(customersDto);
        return new ResponseEntity<>(customersMapper.mapTo(customersService.save(customersEntity)), HttpStatus.OK);
    }

    @PatchMapping(path = "/customers/{id}")
    public ResponseEntity<CustomersDto> partialUpdateCustomers(@PathVariable("id") long id, @RequestBody CustomersDto customersDto) {
        if(!customersService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        CustomersEntity customersEntity = customersMapper.mapFrom(customersDto);
        return new ResponseEntity<>(customersMapper.mapTo(customersService.save(customersEntity)), HttpStatus.OK);
    }

    @DeleteMapping(path = "customers/{id}")
    public ResponseEntity deleteCustomers(@PathVariable("id") long id) {
        customersService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
