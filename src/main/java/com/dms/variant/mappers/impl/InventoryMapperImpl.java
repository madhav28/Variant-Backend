package com.dms.variant.mappers.impl;

import com.dms.variant.domain.InventoryEntity;
import com.dms.variant.domain.dto.InventoryDto;
import com.dms.variant.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class InventoryMapperImpl implements Mapper<InventoryEntity, InventoryDto>{

    private final ModelMapper modelMapper;

    public InventoryMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public InventoryDto mapTo(InventoryEntity inventoryEntity) {
        return modelMapper.map(inventoryEntity, InventoryDto.class);
    }

    @Override
    public InventoryEntity mapFrom(InventoryDto inventoryDto) {
        return modelMapper.map(inventoryDto, InventoryEntity.class);
    }

    @Override
    public void updatePartial(InventoryEntity entity, InventoryDto dto) {
        modelMapper.map(dto, entity);
    }

}
