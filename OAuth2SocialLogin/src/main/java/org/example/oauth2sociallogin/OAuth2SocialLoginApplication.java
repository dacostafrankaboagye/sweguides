package org.example.oauth2sociallogin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class OAuth2SocialLoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(OAuth2SocialLoginApplication.class, args);
    }

}
