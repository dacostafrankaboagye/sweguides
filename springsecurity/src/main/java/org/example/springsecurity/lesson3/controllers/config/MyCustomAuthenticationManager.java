//package org.example.springsecurity.lesson3.controllers.config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class MyCustomAuthenticationManager implements AuthenticationManager {
//
//    private final MyCustomAuthenticationProvider myCustomAuthenticationProvider;
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        if(myCustomAuthenticationProvider.supports(authentication.getClass())){
//            return myCustomAuthenticationProvider.authenticate(authentication);
//        }
//        throw  new BadCredentialsException("no no no ---!");
//    }
//}
