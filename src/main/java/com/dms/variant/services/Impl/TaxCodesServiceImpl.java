package com.dms.variant.services.Impl;

import com.dms.variant.domain.TaxCodesEntity;
import com.dms.variant.repositories.TaxCodesRepository;
import com.dms.variant.services.TaxCodesService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaxCodesServiceImpl implements TaxCodesService {

    private TaxCodesRepository taxCodesRepository;

    public TaxCodesServiceImpl(TaxCodesRepository taxCodesRepository) {
        this.taxCodesRepository = taxCodesRepository;
    }

    @Override
    public TaxCodesEntity save(TaxCodesEntity taxCodesEntity) {
        return taxCodesRepository.save(taxCodesEntity);
    }

    @Override
    public Page<TaxCodesEntity> findAll(Pageable pageable) {
        return taxCodesRepository.findAll(pageable);
    }

    @Override
    public Optional<TaxCodesEntity> findOne(long id) {
        return taxCodesRepository.findById(id);
    }

    @Override
    public boolean isExists(long id) {
        return taxCodesRepository.existsById(id);
    }

    @Override
    public void delete(long id) {
        taxCodesRepository.deleteById(id);
    }
}
