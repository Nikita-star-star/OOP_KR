package com.example.jkhspring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, AllowAllAuthenticationProvider allowAllProvider) throws Exception {
        http.authenticationProvider(allowAllProvider);

        http
                // чтобы формы DELETE/SAVE работали без CSRF токена
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/css/**", "/js/**", "/images/**").permitAll()
                        .anyRequest().authenticated()
                )
                // оставляем стандартную форму логина, но после входа ВСЕГДА на главную
                .formLogin(form -> form.defaultSuccessUrl("/", true))
                .logout(logout -> logout.permitAll());

        return http.build();
    }
}
