package com.example.jkhspring.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * "Фейковая" проверка логина/пароля:
 * - форма входа остаётся
 * - любые введённые данные принимаются
 */
@Component
public class AllowAllAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (authentication.getName() == null || authentication.getName().isBlank())
                ? "user"
                : authentication.getName();

        return new UsernamePasswordAuthenticationToken(
                username,
                authentication.getCredentials(),
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
