package org.example.securingapiwithoauth2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain  securityFilterChain(HttpSecurity  http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/public/**").permitAll() // Allow public access to /public
                        .anyRequest().authenticated() // All other requests require authentication
                )
                .oauth2Login(oauth2 -> oauth2
                        //.loginPage("/public/login") // Custom login page
                        .defaultSuccessUrl("/secure/user", true) // Redirect to /private after successful login
                        .permitAll() // Allow access to the login page
                )
                .oauth2ResourceServer(
                        (resourceServer) -> resourceServer
                        .jwt(Customizer.withDefaults())
                )
                .logout(logout ->
                        logout
                                .logoutSuccessUrl("/public/logout-success") // Redirect to /public after logout
                                .permitAll()
                );

        return http.build();
    }
}
