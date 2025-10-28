package com.fidelin.clicker.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    private final JwtFilter jwtFilter;
    public SecurityConfig(JwtFilter jwtFilter) { this.jwtFilter = jwtFilter; }

    @Bean BCryptPasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }


    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // preflight CORS
                        .requestMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll()
                        // PÚBLICO: página y estáticos
                        .requestMatchers("/", "/index.html",
                                "/css/**", "/js/**", "/images/**", "/webjars/**", "/static/**").permitAll()
                        // PÚBLICO: endpoints abiertos
                        .requestMatchers("/auth/**", "/h2-console/**", "/leaderboard/top").permitAll()
                        // resto requiere login
                        .anyRequest().authenticated()
                );
        http.headers(h -> h.frameOptions(f -> f.disable())); // H2 console
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
