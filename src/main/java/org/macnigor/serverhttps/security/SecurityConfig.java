package org.macnigor.serverhttps.security;

import org.macnigor.serverhttps.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class SecurityConfig {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,UserRepository userRepository) throws Exception {
        http.csrf(csrf->csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().permitAll()
                )
                .addFilterBefore(new ApiKeyAuthFilter(userRepository), org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
