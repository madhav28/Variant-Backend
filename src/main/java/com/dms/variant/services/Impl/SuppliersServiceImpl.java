package com.dms.variant.services.Impl;

import com.dms.variant.domain.SuppliersEntity;
import com.dms.variant.domain.dto.SuppliersDto;
import com.dms.variant.repositories.SuppliersRepository;
import com.dms.variant.services.SuppliersService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SuppliersServiceImpl implements SuppliersService {

    private SuppliersRepository suppliersRepository;

    public SuppliersServiceImpl(SuppliersRepository suppliersRepository) {
        this.suppliersRepository = suppliersRepository;
    }

    @Override
    public SuppliersEntity save(SuppliersEntity suppliersEntity) {
        return suppliersRepository.save(suppliersEntity);
    }

    @Override
    public Optional<SuppliersEntity> findOne(long id) {
        return suppliersRepository.findById(id);
    }

    @Override
    public Page<SuppliersEntity> findAll(Pageable pageable) {
        return suppliersRepository.findAll(pageable);
    }

    @Override
    public boolean isExists(long id) {
        return suppliersRepository.existsById(id);
    }

    @Override
    public void delete(long id) {
        suppliersRepository.deleteById(id);
    }
}
