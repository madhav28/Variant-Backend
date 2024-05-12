package com.dms.variant.services.Impl;

import com.dms.variant.domain.TaxAuthoritiesEntity;
import com.dms.variant.repositories.TaxAuthoritiesRepository;
import com.dms.variant.services.TaxAuthoritiesService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaxAuthoritiesServiceImpl implements TaxAuthoritiesService {

    private TaxAuthoritiesRepository taxAuthoritiesRepository;

    public TaxAuthoritiesServiceImpl(TaxAuthoritiesRepository taxAuthoritiesRepository) {
        this.taxAuthoritiesRepository = taxAuthoritiesRepository;
    }

    @Override
    public TaxAuthoritiesEntity save(TaxAuthoritiesEntity taxAuthoritiesEntity) {
        return taxAuthoritiesRepository.save(taxAuthoritiesEntity);
    }

    @Override
    public Page<TaxAuthoritiesEntity> findAll(Pageable pageable) {
        return taxAuthoritiesRepository.findAll(pageable);
    }

    @Override
    public Optional<TaxAuthoritiesEntity> findOne(long id) {
        return taxAuthoritiesRepository.findById(id);
    }

    @Override
    public boolean isExists(long id) {
        return taxAuthoritiesRepository.existsById(id);
    }

    @Override
    public void delete(long id) {
        taxAuthoritiesRepository.deleteById(id);
    }
}
