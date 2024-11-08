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


    /**
     * Configure et initialise le bean {@link SecurityFilterChain} pour gérer la sécurité HTTP dans l'application.
     * Cette méthode définit les règles d'autorisation pour différents endpoints, la configuration de la page
     * de connexion et de déconnexion, ainsi que le traitement des exceptions.
     *
     * <p>La configuration spécifie des autorisations pour les ressources statiques et pour certains rôles.
     * Elle définit également une page de connexion personnalisée, une redirection après succès, et une
     * URL de déconnexion personnalisée qui invalide la session et supprime les cookies.</p>
     *
     * @param http l'instance de {@link HttpSecurity} pour la configuration de la sécurité HTTP
     * @return une instance de {@link SecurityFilterChain} configurée pour gérer la sécurité HTTP
     * @throws Exception si une erreur de configuration survient lors de la création de la chaîne de filtres
     */
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


    /**
     * Configure et initialise le bean {@link AuthenticationManager} utilisé pour l'authentification des utilisateurs
     * dans l'application. Cette méthode définit le service de gestion des détails d'utilisateur et le
     * codeur de mot de passe.
     *
     * <p>Cette configuration utilise un {@link HttpSecurity} pour obtenir un {@link AuthenticationManagerBuilder}
     * partagé, puis y enregistre le service d'authentification {@code poseidonUserDetailsService}
     * ainsi que le codeur de mot de passe {@code passwordEncoder} pour les vérifications de mot de passe.</p>
     *
     * @param http l'instance de {@link HttpSecurity} partagée permettant d'accéder au gestionnaire d'authentification
     * @return un {@link AuthenticationManager} configuré avec le service de détails utilisateur et le codeur de mot de passe
     * @throws Exception si une erreur de configuration survient lors de la création du {@link AuthenticationManager}
     */
    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {

        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder.userDetailsService(poseidonUserDetailsService)
                .passwordEncoder(passwordEncoder());

        return authenticationManagerBuilder.build();
    }
}
