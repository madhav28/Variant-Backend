package com.dms.variant.mappers.impl;

import com.dms.variant.domain.AssociatesEntity;
import com.dms.variant.domain.dto.AssociatesDto;
import com.dms.variant.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AssociatesMapperImpl implements Mapper<AssociatesEntity, AssociatesDto> {

    private ModelMapper modelMapper;

    public AssociatesMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public AssociatesDto mapTo(AssociatesEntity associatesEntity) {
        return modelMapper.map(associatesEntity, AssociatesDto.class);
    }

    @Override
    public AssociatesEntity mapFrom(AssociatesDto associatesDto) {
        return modelMapper.map(associatesDto, AssociatesEntity.class);
    }
}
