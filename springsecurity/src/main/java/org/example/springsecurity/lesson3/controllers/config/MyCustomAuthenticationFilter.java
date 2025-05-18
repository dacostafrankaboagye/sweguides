package org.example.springsecurity.lesson3.controllers.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class MyCustomAuthenticationFilter extends OncePerRequestFilter {

    private final MyCustomAuthenticationManager myCustomAuthenticationManager;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String key = String.valueOf(request.getHeader("my-cus-key"));

        // create an authentication obj which is not yet authenticated
        MyCustomAuthentication myCustomAuthentication = new MyCustomAuthentication(
                false,
                key
        );

        // delegate the Authentication object to the manager
        var a = myCustomAuthenticationManager.authenticate(myCustomAuthentication);


        // once the authentication has worked
        if(a.isAuthenticated()){
            SecurityContextHolder.getContext().setAuthentication(a);
            filterChain.doFilter(request, response);
        }

    }
}
