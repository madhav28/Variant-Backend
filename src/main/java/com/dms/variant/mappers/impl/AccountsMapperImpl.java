package com.dms.variant.mappers.impl;

import com.dms.variant.domain.AccountsEntity;
import com.dms.variant.domain.dto.AccountsDto;
import com.dms.variant.mappers.Mapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AccountsMapperImpl implements Mapper<AccountsEntity, AccountsDto> {

    private ModelMapper modelMapper;

    public AccountsMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public AccountsDto mapTo(AccountsEntity accountsEntity) {
        return modelMapper.map(accountsEntity, AccountsDto.class);
    }

    @Override
    public AccountsEntity mapFrom(AccountsDto accountsDto) {
        return modelMapper.map(accountsDto, AccountsEntity.class);
    }
}
