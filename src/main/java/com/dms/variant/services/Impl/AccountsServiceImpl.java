package com.dms.variant.services.Impl;

import com.dms.variant.domain.AccountsEntity;
import com.dms.variant.repositories.AccountsRepository;
import com.dms.variant.services.AccountsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountsServiceImpl implements AccountsService {

    private AccountsRepository accountsRepository;

    public AccountsServiceImpl(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    @Override
    public AccountsEntity save(AccountsEntity accountsEntity) {
        return accountsRepository.save(accountsEntity);
    }

    @Override
    public Page<AccountsEntity> findAll(Pageable pageable) {
        return accountsRepository.findAll(pageable);
    }

    @Override
    public Optional<AccountsEntity> findOne(long id) {
        return accountsRepository.findById(id);
    }

    @Override
    public boolean isExists(long id) {
        return accountsRepository.existsById(id);
    }

    @Override
    public void delete(long id) {
        accountsRepository.deleteById(id);
    }
}
