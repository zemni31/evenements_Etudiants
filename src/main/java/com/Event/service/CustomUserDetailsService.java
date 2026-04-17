package com.Event.service;

import com.Event.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Recherche l'utilisateur par email dans la EventDataLoader de données
        com.Event.Model.User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'email : " + email));

        // Retourne un UserDetails avec l'email comme username et le mot de passe hashé
        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword()) // Déjà hashé avec BCrypt
                .roles("USER") // Rôle par défaut
                .build();
    }
}
