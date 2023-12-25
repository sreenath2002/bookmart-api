package com.example.bookmart.project.Configuration;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(session->{session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);})

                .authorizeHttpRequests(authorize ->
                        authorize

//                                .requestMatchers("/auth/signin").authenticated()
                                .requestMatchers("/api/auth/signup").permitAll()
                                .requestMatchers("/api/auth/send-otp").permitAll()
                                .requestMatchers("/api/auth/verify-otp").permitAll()
                                // Allow access to signup for all users
                                .requestMatchers("/api/auth/signin").permitAll()
                                .requestMatchers("/api/auth/emailexists").permitAll()
                                .requestMatchers("/api/auth/changePassword").permitAll()
                                .requestMatchers("/api/products/getProducts").permitAll()
                                .requestMatchers("/api/products/getsciencproducts").permitAll()
                                .requestMatchers("/api/products/getlanguageproducts").permitAll()
                                .requestMatchers("/api/products/getcommerceproducts").permitAll()
                                .requestMatchers("/api/products/getproduct/{id}").permitAll()
                                .requestMatchers("/api/products/categoryfilter").permitAll()
                                .requestMatchers("/api/products/universityfilter").permitAll()
                                .requestMatchers("/api/products/subjectsFilter/{courseName}").permitAll()
                                .requestMatchers("/api/products/coursefilter/{categoryName}").permitAll()
                                .requestMatchers("/api/products/semesterfilter/{categoryName}").permitAll()
                                .requestMatchers("/api/user/productidsfromcart/{userId}").permitAll()
//                                .requestMatchers("/api/auth/semester/{categoryName}").permitAll()
//                                .requestMatchers("/api/auth/subjects/{courseName}").permitAll()
                                .requestMatchers("/api/user/productidsfromwishlist/{userId}").permitAll()

                                .requestMatchers("/api/auth/logout").authenticated()
//                                .requestMatchers("/admin/**").hasRole("ADMIN") // Requires ROLE_ADMIN for /admin/**
//                        .requestMatchers("/user/**").hasRole("USER")   // Requires ROLE_USER for /user/**
                        .anyRequest().authenticated()


                )
                .addFilterBefore(new JwtValidator(), BasicAuthenticationFilter.class)
                .csrf(csrf -> {
                    csrf.disable();
                })
                .cors(cors -> {
                    cors.configurationSource(corsConfigurationSource());
                });
//                .logout(logout ->
//                        logout
//                                .logoutUrl("/auth/logout") // URL where the logout will be performed
//                                .logoutSuccessUrl("/")      // Redirect after successful logout
//                                .clearAuthentication(true)  // Clear the authentication
//
//                                .invalidateHttpSession(true)
//                );

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000/"));
        configuration.setAllowedMethods(Collections.singletonList("*"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setExposedHeaders(Arrays.asList("Authorization"));
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

