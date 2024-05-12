package com.dms.variant.mappers.impl;

import com.dms.variant.domain.ServicesEntity;
import com.dms.variant.domain.dto.ServicesDto;
import com.dms.variant.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ServicesMapperImpl implements Mapper<ServicesEntity, ServicesDto> {

    private ModelMapper modelMapper;

    public ServicesMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ServicesDto mapTo(ServicesEntity servicesEntity) {
        return modelMapper.map(servicesEntity, ServicesDto.class);
    }

    @Override
    public ServicesEntity mapFrom(ServicesDto servicesDto) {
        return modelMapper.map(servicesDto, ServicesEntity.class);
    }
}
