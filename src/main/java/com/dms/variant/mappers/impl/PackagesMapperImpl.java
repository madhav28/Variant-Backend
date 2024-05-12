package com.dms.variant.mappers.impl;

import com.dms.variant.domain.PackagesEntity;
import com.dms.variant.domain.dto.PackagesDto;
import com.dms.variant.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PackagesMapperImpl implements Mapper<PackagesEntity, PackagesDto> {

    private ModelMapper modelMapper;

    public PackagesMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public PackagesDto mapTo(PackagesEntity packagesEntity) {
        return modelMapper.map(packagesEntity, PackagesDto.class);
    }

    @Override
    public PackagesEntity mapFrom(PackagesDto packagesDto) {
        return modelMapper.map(packagesDto, PackagesEntity.class);
    }
}
