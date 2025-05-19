package org.example.lessona.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // authentication server - authflow filter
    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http){
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
                OAuth2AuthorizationServerConfigurer.authorizationServer();

        http
                .securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
                .with(authorizationServerConfigurer, (authorizationServer) ->
                        authorizationServer
                                .authorizationEndpoint(authorizationEndpoint ->
                                        authorizationEndpoint
                                                .authorizationRequestConverter(authorizationRequestConverter)
                                                .authorizationRequestConverters(authorizationRequestConvertersConsumer)
                                                .authenticationProvider(authenticationProvider)
                                                .authenticationProviders(authenticationProvidersConsumer)
                                                .authorizationResponseHandler(authorizationResponseHandler)
                                                .errorResponseHandler(errorResponseHandler)
                                                .consentPage("/oauth2/v1/authorize")
                                )
                );

        return http.build();

    }


    // app fiter
    @Bean
    @Order(2)
    public SecurityFilterChain applicationSecurityFilerChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults());

        return http.build();
    }

}
