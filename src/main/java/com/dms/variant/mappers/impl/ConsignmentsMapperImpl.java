package com.dms.variant.mappers.impl;

import com.dms.variant.domain.ConsignmentsEntity;
import com.dms.variant.domain.dto.ConsignmentsDto;
import com.dms.variant.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ConsignmentsMapperImpl implements Mapper<ConsignmentsEntity, ConsignmentsDto> {

    private ModelMapper modelMapper;

    public ConsignmentsMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ConsignmentsDto mapTo(ConsignmentsEntity consignmentsEntity) {
        return modelMapper.map(consignmentsEntity, ConsignmentsDto.class);
    }

    @Override
    public ConsignmentsEntity mapFrom(ConsignmentsDto consignmentsDto) {
        return modelMapper.map(consignmentsDto, ConsignmentsEntity.class);
    }
}
