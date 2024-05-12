package com.dms.variant.services;

import com.dms.variant.domain.AccountsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface AccountsService {
    AccountsEntity save(AccountsEntity accountsEntity);

    Page<AccountsEntity> findAll(Pageable pageable);

    Optional<AccountsEntity> findOne(long id);

    boolean isExists(long id);

    void delete(long id);
}
