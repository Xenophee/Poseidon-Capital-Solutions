package com.nnk.springboot.service;


import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    public final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User getById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Specified user not found : {}", id);
                    return new EntityNotFoundException("Specified user not found");
                });
    }

    public User update(int id, User user) {

        User userToUpdate = getById(id);
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setFullname(user.getFullname());
        userToUpdate.setRole(user.getRole());
        userToUpdate.setPassword(user.getPassword());

        if (!user.equals(userToUpdate)) return userToUpdate;

        return userRepository.save(userToUpdate);
    }

    public void delete(int id) {

        if (!userRepository.existsById(id)) {
            logger.warn("User with id {} not found for deletion", id);
            throw new EntityNotFoundException("Specified user not found");
        }

        userRepository.deleteById(id);
    }
}
