package com.dms.variant.mappers.impl;

import com.dms.variant.domain.SuppliersEntity;
import com.dms.variant.domain.dto.SuppliersDto;
import com.dms.variant.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class SuppliersMapperImpl implements Mapper<SuppliersEntity, SuppliersDto> {

    private ModelMapper modelMapper;

    public SuppliersMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public SuppliersDto mapTo(SuppliersEntity suppliersEntity) {
        return modelMapper.map(suppliersEntity, SuppliersDto.class);
    }

    @Override
    public SuppliersEntity mapFrom(SuppliersDto suppliersDto) {
        return modelMapper.map(suppliersDto, SuppliersEntity.class);
    }
}
