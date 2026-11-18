
package com.tap;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.tap.repository.UserRepo;

@Configuration
public class SecurityConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of(
            "http://localhost:5173",
            "http://localhost:5174"
        ));

        configuration.setAllowedMethods(List.of(
            "GET", "POST", "PUT", "DELETE", "OPTIONS"
        ));

        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
            new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepo userRepo) {

        return email -> {
            com.tap.model.User user = userRepo.findByEmail(email)
                .orElseThrow(() ->
                    new UsernameNotFoundException("User not found: " + email)
                );

            return User.withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
            .cors(Customizer.withDefaults())
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(auth -> auth

                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // Public restaurant and menu access (temporary testing)
                .requestMatchers(
                    "/restaurants/**",
                    "/menu/**"
                ).permitAll()

                // Temporary access for customer order testing
                .requestMatchers(HttpMethod.POST, "/order/checkout").permitAll()
                .requestMatchers(HttpMethod.GET, "/order/user/**").permitAll()

                // Swagger access
                .requestMatchers(
                    "/swagger-ui/**",
                    "/v3/api-docs/**"
                ).permitAll()

                // User registration and login
                .requestMatchers(HttpMethod.POST, "/user").permitAll()
                .requestMatchers(HttpMethod.POST, "/user/login").permitAll()

                // Admin-only user management
                .requestMatchers("/user/**").hasRole("ADMIN")

                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
