package com.dms.variant.services.Impl;

import com.dms.variant.domain.VendorsEntity;
import com.dms.variant.repositories.VendorsRespository;
import com.dms.variant.services.VendorsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VendorsServiceImpl implements VendorsService {

    private VendorsRespository vendorsRespository;

    public VendorsServiceImpl(VendorsRespository vendorsRespository) {
        this.vendorsRespository = vendorsRespository;
    }

    @Override
    public VendorsEntity save(VendorsEntity vendorsEntity) {
        return vendorsRespository.save(vendorsEntity);
    }

    @Override
    public Page<VendorsEntity> findAll(Pageable pageable) {
        return vendorsRespository.findAll(pageable);
    }

    @Override
    public Optional<VendorsEntity> findOne(long id) {
        return vendorsRespository.findById(id);
    }

    @Override
    public boolean isExists(long id) {
        return vendorsRespository.existsById(id);
    }

    @Override
    public void delete(long id) {
        vendorsRespository.deleteById(id);
    }
}
