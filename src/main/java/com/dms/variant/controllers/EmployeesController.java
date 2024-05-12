package com.dms.variant.controllers;

import com.dms.variant.domain.EmployeesEntity;
import com.dms.variant.domain.dto.EmployeesDto;
import com.dms.variant.mappers.Mapper;
import com.dms.variant.services.EmployessService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class EmployeesController {

    private EmployessService employessService;

    private Mapper<EmployeesEntity, EmployeesDto> employeesMapper;

    public EmployeesController(EmployessService employessService, Mapper<EmployeesEntity, EmployeesDto> employeesMapper) {
        this.employessService = employessService;
        this.employeesMapper = employeesMapper;
    }

    @PostMapping("/employees")
    public ResponseEntity<EmployeesDto> createEmployees(@RequestBody EmployeesDto employeesDto) {
        EmployeesEntity employeesEntity = employeesMapper.mapFrom(employeesDto);
        return new ResponseEntity<>(employeesMapper.mapTo(employessService.save(employeesEntity)), HttpStatus.CREATED);
    }

    @GetMapping("/employees")
    public Page<EmployeesDto> listEmployees(Pageable pageable) {
        Page<EmployeesEntity> employees = employessService.findAll(pageable);
        return employees.map(employeesMapper::mapTo);
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<EmployeesDto> getEmployees(@PathVariable("id") long id) {
        Optional<EmployeesEntity> foundEmployees = employessService.findOne(id);
        return foundEmployees.map(employeesEntity -> {
            return new ResponseEntity<>(employeesMapper.mapTo(employeesEntity), HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<EmployeesDto> fullUpdateEmployees(@PathVariable("id") long id, @RequestBody EmployeesDto employeesDto) {
        if(!employessService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        employeesDto.setId(id);
        EmployeesEntity employeesEntity = employeesMapper.mapFrom(employeesDto);
        return new ResponseEntity<>(employeesMapper.mapTo(employessService.save(employeesEntity)), HttpStatus.OK);
    }

    @PatchMapping("/employees/{id}")
    public ResponseEntity<EmployeesDto> partialUpdateEmployees(@PathVariable("id") long id, @RequestBody EmployeesDto employeesDto) {
        if(!employessService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        employeesDto.setId(id);
        EmployeesEntity employeesEntity = employeesMapper.mapFrom(employeesDto);
        return new ResponseEntity<>(employeesMapper.mapTo(employeesEntity), HttpStatus.OK);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity deleteEmployees(@PathVariable("id") long id) {
        employessService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
