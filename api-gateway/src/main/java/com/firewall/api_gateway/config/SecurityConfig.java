//package com.firewall.api_gateway.config;
//
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(ServerHttpSecurity serverHttpSecurity) throws Exception {
//        serverHttpSecurity.csrf()
//                .disable()
//                .authorizeExchange(exchange -> exchange
//                        .pathMatchers("/api/**").permitAll()
//                        .anyExchange().authenticated()
//                );
//
//    }
//}
