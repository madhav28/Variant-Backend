package com.dms.variant.services.Impl;

import com.dms.variant.domain.AssociatesEntity;
import com.dms.variant.repositories.AssociatesRepository;
import com.dms.variant.services.AssociatesService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AssociatesServiceImpl implements AssociatesService {

    private AssociatesRepository associatesRepository;

    public AssociatesServiceImpl(AssociatesRepository associatesRepository) {
        this.associatesRepository = associatesRepository;
    }

    @Override
    public AssociatesEntity save(AssociatesEntity associatesEntity) {
        return associatesRepository.save(associatesEntity);
    }

    @Override
    public Page<AssociatesEntity> findAll(Pageable pageable) {
        return associatesRepository.findAll(pageable);
    }

    @Override
    public Optional<AssociatesEntity> findOne(long id) {
        return associatesRepository.findById(id);
    }

    @Override
    public boolean isExists(long id) {
        return associatesRepository.existsById(id);
    }

    @Override
    public void delete(long id) {
        associatesRepository.deleteById(id);
    }
}
