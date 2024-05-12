package com.dms.variant.services;

import com.dms.variant.domain.InventoryEntity;
import com.dms.variant.domain.ServicesEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ServicesService {

    ServicesEntity save(ServicesEntity services);

    Page<ServicesEntity> findAll(Pageable pageable);

    Optional<ServicesEntity> findOne(long id);

    boolean isExists(long id);

    ServicesEntity partitalUpdate(long id, ServicesEntity servicesEntity);

    void delete(long id);
}
