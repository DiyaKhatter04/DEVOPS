package com.app.quantitymeasurement.config;

import lombok.RequiredArgsConstructor;
import com.app.quantitymeasurement.security.CustomOAuth2UserService;
import com.app.quantitymeasurement.security.OAuth2LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor

public class SecurityConfig {

    private final OAuth2LoginSuccessHandler successHandler;

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http

                .csrf(csrf -> csrf.disable())

                .cors(cors -> cors.configurationSource(request -> {

                    CorsConfiguration configuration =
                            new CorsConfiguration();

                    configuration.setAllowedOrigins(
                            List.of("*")
                    );

                    configuration.setAllowedMethods(
                            List.of("*")
                    );

                    configuration.setAllowedHeaders(
                            List.of("*")
                    );

                    return configuration;
                }))

                .sessionManagement(session ->

                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(

                                "/",
                                "/index.html",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/favicon.ico",
                                "/api/auth/**",
                                "/oauth2/**",
                                "/login/**"
                        )

                        .permitAll()

                        .anyRequest()

                        .authenticated()
                )

                .oauth2Login(oauth ->

                        oauth

                                .userInfoEndpoint(userInfo ->

                                        userInfo.userService(
                                                customOAuth2UserService
                                        )
                                )

                                .successHandler(
                                        successHandler
                                )
                )

                .httpBasic(httpBasic -> httpBasic.disable());

        return http.build();
    }
}
