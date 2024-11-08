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


    /**
     * Charge les détails d'un utilisateur pour l'authentification en se basant sur son nom d'utilisateur.
     * Cette méthode recherche l'utilisateur dans le référentiel {@link UserRepository} et,
     * s'il est trouvé, convertit l'entité {@link UserEntity} en un utilisateur de sécurité
     * compatible avec Spring Security via {@link UserDetails}.
     *
     * <p>Si aucun utilisateur correspondant n'est trouvé, une exception {@link UsernameNotFoundException}
     * est levée, et un avertissement est enregistré dans les logs.</p>
     *
     * @param username le nom d'utilisateur de l'utilisateur à authentifier
     * @return une instance de {@link UserDetails} contenant les informations de l'utilisateur pour l'authentification
     * @throws UsernameNotFoundException si aucun utilisateur correspondant n'est trouvé dans le référentiel
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findByUsername(username)
                .map(this::createSecurityUser)
                .orElseThrow(() -> {
                    logger.warn("UserEntity not found with username : {}", username);
                    return new UsernameNotFoundException("UserEntity not found with username : " + username);
                });
    }


    /**
     * Crée et retourne un utilisateur de sécurité Spring {@link User} basé sur un utilisateur de l'application
     * représenté par un {@link UserEntity}. Cette méthode extrait le rôle de l'utilisateur, le convertit en
     * autorité de sécurité, puis crée une instance de {@link User} pour les contrôles d'authentification et d'autorisation.
     *
     * <p>Le rôle de l'utilisateur est préfixé avec "ROLE_" pour se conformer aux conventions de Spring Security.
     * Cette instance de {@link User} est ensuite utilisée par le framework pour gérer les sessions et les contrôles d'accès.</p>
     *
     * @param userEntity l'entité {@link UserEntity} représentant un utilisateur dans l'application
     * @return une instance de {@link User} avec le nom d'utilisateur, le mot de passe et les autorités de sécurité
     */
    private User createSecurityUser(UserEntity userEntity) {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + userEntity.getRole());
        return new User(userEntity.getUsername(), userEntity.getPassword(), List.of(authority));
    }

}
