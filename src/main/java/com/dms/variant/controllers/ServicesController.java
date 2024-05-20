package com.dms.variant.controllers;

import com.dms.variant.domain.ServicesEntity;
import com.dms.variant.domain.dto.ServicesDto;
import com.dms.variant.mappers.Mapper;
import com.dms.variant.services.ServicesService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ServicesController {

    private ServicesService servicesService;

    private Mapper<ServicesEntity, ServicesDto> servicesMapper;

    public ServicesController(ServicesService servicesService, Mapper<ServicesEntity, ServicesDto> servicesMapper) {
        this.servicesService = servicesService;
        this.servicesMapper = servicesMapper;
    }

    @PostMapping(path = "/services")
    public ResponseEntity<ServicesDto> createServices(@RequestBody ServicesDto servicesDto) {
        ServicesEntity servicesEntity = servicesMapper.mapFrom(servicesDto);
        ServicesEntity savedServicesEntity = servicesService.save(servicesEntity);
        return new ResponseEntity<>(servicesMapper.mapTo(savedServicesEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/services")
    public Page<ServicesDto> listServices(Pageable pageable) {
        Page<ServicesEntity> services = servicesService.findAll(pageable);
        return services.map(servicesMapper::mapTo);
    }

    @GetMapping(path = "/services/{id}")
    public ResponseEntity<ServicesDto> getServices(@PathVariable("id") long id) {
        Optional<ServicesEntity> foundServices = servicesService.findOne(id);
        return foundServices.map(servicesEntity -> {
            return new ResponseEntity<>(servicesMapper.mapTo(servicesEntity), HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/services/{id}")
    public ResponseEntity<ServicesDto> fullUpdateServices(@PathVariable("id") long id, @RequestBody ServicesDto servicesDto) {
        if(!servicesService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        servicesDto.setId(id);
        ServicesEntity servicesEntity = servicesMapper.mapFrom(servicesDto);
        ServicesEntity savedServicesEntity = servicesService.save(servicesEntity);

        return new ResponseEntity<>(servicesMapper.mapTo(savedServicesEntity), HttpStatus.OK);
    }

    @PatchMapping(path = "/services/{id}")
    public ResponseEntity<ServicesDto> partialUpdateServices(@PathVariable("id") long id, @RequestBody ServicesDto servicesDto) {
        if(!servicesService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ServicesEntity servicesEntity = servicesMapper.mapFrom(servicesDto);
        ServicesEntity updatedServicesEntity = servicesService.partitalUpdate(id, servicesEntity);

        return new ResponseEntity<>(servicesMapper.mapTo(servicesEntity), HttpStatus.OK);
    }

    @DeleteMapping(path = "/services/{id}")
    public ResponseEntity deleteServices(@PathVariable("id") long id) {
        servicesService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
