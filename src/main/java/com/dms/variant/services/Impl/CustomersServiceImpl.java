package com.dms.variant.services.Impl;

import com.dms.variant.domain.CustomersEntity;
import com.dms.variant.repositories.CustomersRepository;
import com.dms.variant.services.CustomersService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomersServiceImpl implements CustomersService {

    private CustomersRepository customersRepository;

    public CustomersServiceImpl(CustomersRepository customersRepository) {
        this.customersRepository = customersRepository;
    }

    @Override
    public CustomersEntity save(CustomersEntity customersEntity) {
        return customersRepository.save(customersEntity);
    }

    @Override
    public Page<CustomersEntity> findAll(Pageable pageable) {
        return customersRepository.findAll(pageable);
    }

    @Override
    public Optional<CustomersEntity> findOne(long id) {
        return customersRepository.findById(id);
    }

    @Override
    public boolean isExists(long id) {
        return customersRepository.existsById(id);
    }

    @Override
    public void delete(long id) {
        customersRepository.deleteById(id);
    }

}
