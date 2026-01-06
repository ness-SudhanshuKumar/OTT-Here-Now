package com.sudhanshu.gatewayservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class GatewaySecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
            .authorizeExchange(exchanges -> exchanges
                // Public endpoints
                .pathMatchers("/actuator/health").permitAll()
                
                // Client requests with Basic Auth
                .pathMatchers("/api/public/**").hasRole("USER")
                
                // Internal service endpoints with JWT
                .pathMatchers("/api/service1/**", "/api/service2/**")
                    .hasAuthority("SCOPE_read")
                
                // Admin endpoints
                .pathMatchers("/api/admin/**").hasRole("ADMIN")
                
                .anyExchange().authenticated()
            )
            .httpBasic(basic -> {})  // Enable Basic Auth for client requests
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> {})  // Enable JWT for internal services
            )
            .csrf(csrf -> csrf.disable());
        
        return http.build();
    }

    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        UserDetails user = User.builder()
            .username("devUser")
            .password(passwordEncoder().encode("password"))
            .roles("USER")
            .build();

        UserDetails admin = User.builder()
            .username("admin")
            .password(passwordEncoder().encode("admin"))
            .roles("ADMIN")
            .build();

        return new MapReactiveUserDetailsService(user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

