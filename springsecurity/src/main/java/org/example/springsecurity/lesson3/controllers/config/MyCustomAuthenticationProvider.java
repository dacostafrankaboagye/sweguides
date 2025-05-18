package org.example.springsecurity.lesson3.controllers.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class MyCustomAuthenticationProvider implements AuthenticationProvider {

    @Value("${thekey}")
    private String key;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        MyCustomAuthentication myCustomAuthentication = (MyCustomAuthentication) authentication;

        String headerKey = myCustomAuthentication.getKey();

        if(key.equals(headerKey)){
            return new MyCustomAuthentication(true, null);
        }


        // we can use any of the AuthenticationException
        throw new BadCredentialsException("no no no -- !!");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return MyCustomAuthentication.class.equals(authentication);
    }
}
