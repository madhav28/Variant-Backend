package com.dms.variant.repositories;

import com.dms.variant.domain.AccountsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsRepository extends CrudRepository<AccountsEntity, Long>,
        PagingAndSortingRepository<AccountsEntity, Long> {
}
