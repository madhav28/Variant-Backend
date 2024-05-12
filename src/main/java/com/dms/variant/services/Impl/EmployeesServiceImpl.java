package com.dms.variant.services.Impl;

import com.dms.variant.domain.EmployeesEntity;
import com.dms.variant.repositories.EmployeesRepository;
import com.dms.variant.services.EmployessService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeesServiceImpl implements EmployessService {

    private EmployeesRepository employeesRepository;

    public EmployeesServiceImpl(EmployeesRepository employeesRepository) {
        this.employeesRepository = employeesRepository;
    }

    @Override
    public EmployeesEntity save(EmployeesEntity employeesEntity) {
        return employeesRepository.save(employeesEntity);
    }

    @Override
    public Page<EmployeesEntity> findAll(Pageable pageable) {
        return employeesRepository.findAll(pageable);
    }

    @Override
    public Optional<EmployeesEntity> findOne(long id) {
        return employeesRepository.findById(id);
    }

    @Override
    public boolean isExists(long id) {
        return employeesRepository.existsById(id);
    }

    @Override
    public void delete(long id) {
        employeesRepository.deleteById(id);
    }
}
