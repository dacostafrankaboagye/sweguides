package org.example.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;

@Configuration
public class WebSecurityConfig {



    @Bean
    public UserDetailsService userDetailsService(){

        // note that the user extends
        User user1 = new User(
                "user1",
                passwordEncoder().encode("user1password"),
                true,
                true,
                true,
                true,
                Arrays.asList(
                        new SimpleGrantedAuthority("ROLE_MODERATOR"),
                        new SimpleGrantedAuthority("ROLE_AUDITOR")
                )
        );

        UserDetails user2Details = User
                .withUsername("user2")
                .accountExpired(true)
                .accountLocked(true)
                .credentialsExpired(true)
                .disabled(true)
                .password(
                        passwordEncoder().encode("user2password")
                )
                .authorities(
                        "SUPPORT"
                )
                .build();

        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        manager.createUser(user1);
        manager.createUser(user2Details);

        return manager;

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
