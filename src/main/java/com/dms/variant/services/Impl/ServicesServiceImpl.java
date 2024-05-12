package com.dms.variant.services.Impl;

import com.dms.variant.domain.ServicesEntity;
import com.dms.variant.repositories.ServicesRepository;
import com.dms.variant.services.ServicesService;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServicesServiceImpl implements ServicesService {

    private ServicesRepository servicesRepository;

    public ServicesServiceImpl(ServicesRepository servicesRepository) {
        this.servicesRepository = servicesRepository;
    }

    @Override
    public ServicesEntity save(ServicesEntity servicesEntity) {
        return servicesRepository.save(servicesEntity);
    }

    @Override
    public Page<ServicesEntity> findAll(Pageable pageable) { return servicesRepository.findAll(pageable); }

    @Override
    public Optional<ServicesEntity> findOne(long id) { return servicesRepository.findById(id); }

    @Override
    public boolean isExists(long id) { return servicesRepository.existsById(id); }

    @Override
    public ServicesEntity partitalUpdate(long id, ServicesEntity servicesEntity) { return servicesEntity; }

    @Override
    public void delete(long id) { servicesRepository.deleteById(id); }

}
