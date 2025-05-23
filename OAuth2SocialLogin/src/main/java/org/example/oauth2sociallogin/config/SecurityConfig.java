package org.example.oauth2sociallogin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean

    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/", "/login").permitAll()
                                .anyRequest().authenticated()
                )
                .oauth2Login(oauth2login -> {
                    oauth2login.loginPage("/login");
                    oauth2login.successHandler((request, response, authentication) -> response.sendRedirect("/profile"));
                });
//                .formLogin(Customizer.withDefaults()); // this has been commented out because, we are using our own custom login at "/login"

        return http.build();
    }
}
