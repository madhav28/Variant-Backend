package com.dms.variant.mappers.impl;

import com.dms.variant.domain.TaxCodesEntity;
import com.dms.variant.domain.dto.TaxCodesDto;
import com.dms.variant.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TaxCodesMapperImpl implements Mapper<TaxCodesEntity, TaxCodesDto> {

    private ModelMapper modelMapper;

    public TaxCodesMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public TaxCodesDto mapTo(TaxCodesEntity taxCodesEntity) {
        return modelMapper.map(taxCodesEntity, TaxCodesDto.class);
    }

    @Override
    public TaxCodesEntity mapFrom(TaxCodesDto taxCodesDto) {
        return modelMapper.map(taxCodesDto, TaxCodesEntity.class);
    }
}
