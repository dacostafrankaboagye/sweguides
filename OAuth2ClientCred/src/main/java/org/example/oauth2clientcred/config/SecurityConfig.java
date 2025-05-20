package org.example.oauth2clientcred.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import java.util.UUID;

import static org.springframework.security.oauth2.core.AuthorizationGrantType.CLIENT_CREDENTIALS;
import static org.springframework.security.oauth2.core.ClientAuthenticationMethod.CLIENT_SECRET_POST;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception{
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = OAuth2AuthorizationServerConfigurer.authorizationServer(); // This creates a configurer that knows how to configure all the OAuth2 endpoints

        http
            .securityMatcher(authorizationServerConfigurer.getEndpointsMatcher()) // Apply this filter chain only to requests that match the authorization server endpoints // just to the relevant OAuth2 URLs
            .with(authorizationServerConfigurer, Customizer.withDefaults());

        return http.build();

    }

    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("frx290MMt")
                .clientName("frank-app-client")
                .clientSecret("{noop}frank-secret")
                .clientAuthenticationMethod(CLIENT_SECRET_POST)
                .authorizationGrantType(CLIENT_CREDENTIALS)
                .scope("read")
                .build();

        RegisteredClient  registeredClient2 = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("frx290MMt2")
                .clientName("frank-app-client2")
                .clientSecret("{noop}frank-secret2")
                .clientAuthenticationMethod(CLIENT_SECRET_POST)
                .authorizationGrantType(CLIENT_CREDENTIALS)
                .scope("write")
                .build();

        return new InMemoryRegisteredClientRepository(registeredClient, registeredClient2);
    }
}
