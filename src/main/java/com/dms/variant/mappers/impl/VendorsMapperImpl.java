package com.dms.variant.mappers.impl;

import com.dms.variant.domain.VendorsEntity;
import com.dms.variant.domain.dto.VendorsDto;
import com.dms.variant.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class VendorsMapperImpl implements Mapper<VendorsEntity, VendorsDto> {

    private final ModelMapper modelMapper;

    public VendorsMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public VendorsDto mapTo(VendorsEntity vendorsEntity) {
        return modelMapper.map(vendorsEntity, VendorsDto.class);
    }

    @Override
    public VendorsEntity mapFrom(VendorsDto vendorsDto) {
        return modelMapper.map(vendorsDto, VendorsEntity.class);
    }

    @Override
    public void updatePartial(VendorsEntity entity, VendorsDto dto) {
        modelMapper.map(dto, entity);
    }
}
