package com.Event.config;

import com.Event.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import java.util.Locale;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Configuration CSRF + Headers (recommandé)
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**")
                )
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))

                // ====================== FORM LOGIN ======================
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")           // Important
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/search", true)     // Après login → /search
                        .failureUrl("/login?error=true")
                        .permitAll()
                )

                // ====================== AUTORISATIONS ======================
                .authorizeHttpRequests(auth -> auth
                        // Pages accessibles sans connexion
                        .requestMatchers("/register",
                                "/login",
                                "/change-lang",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/h2-console/**")
                        .permitAll()

                        // Pages qui nécessitent d'être connecté
                        .requestMatchers("/search", "/")
                        .authenticated()

                        // Toutes les autres URLs doivent être authentifiées
                        .anyRequest().authenticated()
                )

                // Remember Me
                .rememberMe(remember -> remember
                        .key("uniqueAndSecretKey")
                        .tokenValiditySeconds(86400 * 30)
                        .userDetailsService(customUserDetailsService)
                )

                // Logout
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .deleteCookies("JSESSIONID", "remember-me")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver resolver = new CookieLocaleResolver("lang");
        resolver.setDefaultLocale(Locale.FRENCH);
        resolver.setCookieMaxAge(3600);
        return resolver;
    }
}