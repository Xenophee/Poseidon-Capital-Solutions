package com.nnk.springboot.service;


import com.nnk.springboot.domain.UserEntity;
import com.nnk.springboot.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    public final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    public UserEntity save(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    public UserEntity getById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Specified user not found : {}", id);
                    return new EntityNotFoundException("Specified user not found");
                });
    }

    public UserEntity update(int id, UserEntity userEntity) {

        UserEntity userEntityToUpdate = getById(id);
        userEntityToUpdate.setUsername(userEntity.getUsername());
        userEntityToUpdate.setFullname(userEntity.getFullname());
        userEntityToUpdate.setRole(userEntity.getRole());
        userEntityToUpdate.setPassword(userEntity.getPassword());

        if (!userEntity.equals(userEntityToUpdate)) return userEntityToUpdate;

        return userRepository.save(userEntityToUpdate);
    }

    public void delete(int id) {

        if (!userRepository.existsById(id)) {
            logger.warn("UserEntity with id {} not found for deletion", id);
            throw new EntityNotFoundException("Specified user not found");
        }

        userRepository.deleteById(id);
    }
}
