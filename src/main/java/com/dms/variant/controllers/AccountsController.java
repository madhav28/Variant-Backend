package com.dms.variant.controllers;

import com.dms.variant.domain.AccountsEntity;
import com.dms.variant.domain.dto.AccountsDto;
import com.dms.variant.mappers.Mapper;
import com.dms.variant.services.AccountsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class AccountsController {

    private final AccountsService accountsService;

    private final Mapper<AccountsEntity, AccountsDto> accountsMapper;

    public AccountsController(AccountsService accountsService, Mapper<AccountsEntity, AccountsDto> accountsMapper) {
        this.accountsService = accountsService;
        this.accountsMapper = accountsMapper;
    }

    @PostMapping(path = "/accounts")
    public ResponseEntity<AccountsDto> createAccounts(@RequestBody AccountsDto accountsDto) {
        AccountsEntity accountsEntity = accountsMapper.mapFrom(accountsDto);
        return new ResponseEntity<>(accountsMapper.mapTo(accountsService.save(accountsEntity)), HttpStatus.CREATED);
    }

    @GetMapping(path = "/accounts")
    public Page<AccountsDto> listAccounts(Pageable pageable) {
        Page<AccountsEntity> accounts = accountsService.findAll(pageable);
        return accounts.map(accountsMapper::mapTo);
    }

    @GetMapping(path = "/accounts/{id}")
    public ResponseEntity<AccountsDto> getAccounts(@PathVariable("id") long id) {
        Optional<AccountsEntity> foundAccounts = accountsService.findOne(id);
        return foundAccounts.map(accountsEntity -> {
            return new ResponseEntity<>(accountsMapper.mapTo(accountsEntity), HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/accounts/{id}")
    public ResponseEntity<AccountsDto> fullUpdateAccounts(@PathVariable("id") long id, @RequestBody AccountsDto accountsDto) {
        if(!accountsService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        accountsDto.setId(id);
        AccountsEntity accountsEntity = accountsMapper.mapFrom(accountsDto);
        return new ResponseEntity<>(accountsMapper.mapTo(accountsService.save(accountsEntity)), HttpStatus.OK);
    }

    @PatchMapping(path = "/accounts/{id}")
    public ResponseEntity<AccountsDto> partialUpdateAccounts(@PathVariable("id") long id, @RequestBody AccountsDto dto) {
        Optional<AccountsEntity> entityOptional = accountsService.findOne(id);
        if(entityOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        AccountsEntity entity = entityOptional.get();

        accountsMapper.updatePartial(entity, dto);
        accountsService.save(entity);
        return new ResponseEntity<>(accountsMapper.mapTo(entity), HttpStatus.OK);
    }

    @DeleteMapping(path = "/accounts/{id}")
    public ResponseEntity deleteAccounts(@PathVariable("id") long id) {
        accountsService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
