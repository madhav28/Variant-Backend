package com.dms.variant.mappers.impl;

import com.dms.variant.domain.EmployeesEntity;
import com.dms.variant.domain.dto.EmployeesDto;
import com.dms.variant.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class EmployeesMapperImpl implements Mapper<EmployeesEntity, EmployeesDto> {

    private ModelMapper modelMapper;

    public EmployeesMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public EmployeesDto mapTo(EmployeesEntity employeesEntity) {
        return modelMapper.map(employeesEntity, EmployeesDto.class);
    }

    @Override
    public EmployeesEntity mapFrom(EmployeesDto employeesDto) {
        return modelMapper.map(employeesDto, EmployeesEntity.class);
    }
}
