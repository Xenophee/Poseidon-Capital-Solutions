package com.nnk.springboot.security;


import com.nnk.springboot.domain.UserEntity;
import com.nnk.springboot.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class PoseidonUserDetailsService implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(PoseidonUserDetailsService.class);

    private final UserRepository userRepository;

    @Autowired
    public PoseidonUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findByUsername(username)
                .map(this::createSecurityUser)
                .orElseThrow(() -> {
                    logger.warn("UserEntity not found with username : {}", username);
                    return new UsernameNotFoundException("UserEntity not found with username : " + username);
                });
    }


    private User createSecurityUser(UserEntity userEntity) {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + userEntity.getRole());
        return new User(userEntity.getUsername(), userEntity.getPassword(), List.of(authority));
    }

}
