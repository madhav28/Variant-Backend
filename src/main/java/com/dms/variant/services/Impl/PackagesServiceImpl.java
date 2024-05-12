package com.dms.variant.services.Impl;

import com.dms.variant.domain.PackagesEntity;
import com.dms.variant.repositories.PackagesRepository;
import com.dms.variant.services.PackagesService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PackagesServiceImpl implements PackagesService {

    private PackagesRepository packagesRepository;

    public PackagesServiceImpl(PackagesRepository packagesRepository) {
        this.packagesRepository = packagesRepository;
    }

    @Override
    public PackagesEntity save(PackagesEntity packagesEntity) {
        return packagesRepository.save(packagesEntity);
    }

    @Override
    public Page<PackagesEntity> findAll(Pageable pageable) {
        return packagesRepository.findAll(pageable);
    }

    @Override
    public Optional<PackagesEntity> findOne(long id) {
        return packagesRepository.findById(id);
    }

    @Override
    public boolean isExists(long id) {
        return packagesRepository.existsById(id);
    }

    @Override
    public void delete(long id) {
        packagesRepository.deleteById(id);
    }
}
