package com.dms.variant.services.Impl;

import com.dms.variant.domain.UserEntity;
import com.dms.variant.repositories.UserRepository;
import com.dms.variant.services.UserService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findById(username);
    }

    @Override
    public boolean isExistsUsername(String username) {
        return userRepository.existsById(username);
    }

    @Transactional
    @Override
    public UserEntity save(UserEntity user) {
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            // Handle the unique constraint violation
            throw new RuntimeException("User with userName " + user.getUsername() + " already exists.", e);
        }
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }
}
