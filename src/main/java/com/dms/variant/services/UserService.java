package com.dms.variant.services;

import com.dms.variant.domain.UserEntity;

import java.util.Optional;

public interface UserService {

    Optional<UserEntity> findByUsername(String username);

    boolean isExistsUsername(String username);

    UserEntity save(UserEntity user);

    void deleteAll();
}
