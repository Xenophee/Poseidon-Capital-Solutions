package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.UserEntity;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@AutoConfigureMockMvc
@SpringBootTest
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository repository;

    private UserEntity user;


    @BeforeEach
    public void setUp() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user = new UserEntity("Joe", encoder.encode("Abc123@!"), "Joe Doe", "USER");
        when(repository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
    }


    @Test
    void loginWithValidCredentials_ShouldReturn302AndRedirect() throws Exception {

        mockMvc.perform(formLogin("/app/login")
                        .user(user.getUsername())
                        .password("Abc123@!"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"))
                .andExpect(authenticated());
    }


    @Test
    void loginWithInvalidCredentials_ShouldReturnUnauthenticated() throws Exception {
        mockMvc.perform(formLogin("/app/login")
                        .user("invalidUser")
                        .password("invalidPassword"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/app/login?error"))
                .andExpect(unauthenticated());
    }
}
