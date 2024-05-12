package com.spring.sales.Sales.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // authentication
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withUsername("mathew").password(passwordEncoder().encode("mathew123")).roles("ADMIN").build();
        UserDetails admin = User.withUsername("jovit").password(passwordEncoder().encode("jovit123")).roles("USER").build();
        return new InMemoryUserDetailsManager(user, admin);
    }
    // authorization
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        String requestMatcher = "/products/**";
        http.httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests((authorize) -> authorize.requestMatchers(HttpMethod.GET, requestMatcher)
                        .permitAll().requestMatchers(HttpMethod.PUT, requestMatcher).hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, requestMatcher).hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, requestMatcher).hasRole("ADMIN").anyRequest().authenticated());
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(cors -> cors.configurationSource(request -> {
            CorsConfiguration config = new CorsConfiguration().applyPermitDefaultValues();
            config.addAllowedMethod(HttpMethod.PUT);
            config.addAllowedMethod(HttpMethod.DELETE);
            return config;
        }));
        return http.build();
    }
}