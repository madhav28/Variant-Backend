package com.dms.variant.mappers.impl;

import com.dms.variant.domain.TaxAuthoritiesEntity;
import com.dms.variant.domain.dto.TaxAuthoritiesDto;
import com.dms.variant.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TaxAuthoritiesMapperImpl implements Mapper<TaxAuthoritiesEntity, TaxAuthoritiesDto> {

    private ModelMapper modelMapper;

    public TaxAuthoritiesMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public TaxAuthoritiesDto mapTo(TaxAuthoritiesEntity taxAuthoritiesEntity) {
        return modelMapper.map(taxAuthoritiesEntity, TaxAuthoritiesDto.class);
    }

    @Override
    public TaxAuthoritiesEntity mapFrom(TaxAuthoritiesDto taxAuthoritiesDto) {
        return modelMapper.map(taxAuthoritiesDto, TaxAuthoritiesEntity.class);
    }
}
