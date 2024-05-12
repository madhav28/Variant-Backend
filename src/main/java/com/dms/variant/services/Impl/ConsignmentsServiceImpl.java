package com.dms.variant.services.Impl;

import com.dms.variant.domain.ConsignmentsEntity;
import com.dms.variant.repositories.ConsignmentsRepository;
import com.dms.variant.services.ConsignmentsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConsignmentsServiceImpl implements ConsignmentsService {

    private ConsignmentsRepository consignmentsRepository;

    public ConsignmentsServiceImpl(ConsignmentsRepository consignmentsRepository) {
        this.consignmentsRepository = consignmentsRepository;
    }

    @Override
    public ConsignmentsEntity save(ConsignmentsEntity consignmentsEntity) {
        return consignmentsRepository.save(consignmentsEntity);
    }

    @Override
    public Page<ConsignmentsEntity> findAll(Pageable pageable) {
        return consignmentsRepository.findAll(pageable);
    }

    @Override
    public Optional<ConsignmentsEntity> findOne(long id) {
        return consignmentsRepository.findById(id);
    }

    @Override
    public boolean isExists(long id) {
        return consignmentsRepository.existsById(id);
    }

    @Override
    public void delete(long id) {
        consignmentsRepository.deleteById(id);
    }
}
