package com.nnk.springboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final PoseidonUserDetailsService poseidonUserDetailsService;


    @Autowired
    public SecurityConfiguration(PoseidonUserDetailsService poseidonUserDetailsService) {
        this.poseidonUserDetailsService = poseidonUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorization -> authorization
                        .requestMatchers("/css/**").permitAll()
                        .requestMatchers("/", "/app/login").permitAll()
                        .requestMatchers("/user/**").hasRole("ADMIN")
                        .anyRequest().authenticated() // Toutes les autres requêtes doivent être authentifiées
                )
                .formLogin(form -> form
                        .loginPage("/app/login")
                        .defaultSuccessUrl("/bidList/list", true) // Page de connexion personnalisée avec redirection forcée
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/app-logout") // URL de déconnexion personnalisée
                        .logoutSuccessUrl("/") // Redirection après déconnexion
                        .invalidateHttpSession(true) // Supprime la session HTTP
                        .deleteCookies("JSESSIONID") // Supprime les cookies de session
                        .clearAuthentication(true) // Supprime les informations d'authentification côté serveur
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/app/error") // Redirection vers la page 403 pour accès refusé
                );
        return http.build();
    }


    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {

        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder.userDetailsService(poseidonUserDetailsService)
                .passwordEncoder(passwordEncoder());

        return authenticationManagerBuilder.build();
    }
}
