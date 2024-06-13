package com.dms.variant.mappers.impl;

import com.dms.variant.domain.CustomersEntity;
import com.dms.variant.domain.dto.CustomersDto;
import com.dms.variant.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CustomersMapperImpl implements Mapper<CustomersEntity, CustomersDto> {

    private final ModelMapper modelMapper;

    public CustomersMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CustomersDto mapTo(CustomersEntity customersEntity) {
        return modelMapper.map(customersEntity, CustomersDto.class);
    }

    @Override
    public CustomersEntity mapFrom(CustomersDto customersDto) {
        return modelMapper.map(customersDto, CustomersEntity.class);
    }

    @Override
    public void updatePartial(CustomersEntity entity, CustomersDto dto) {
        modelMapper.map(dto, entity);
    }
}
