package org.example.springsecurity.lesson2.services;

import lombok.RequiredArgsConstructor;
import org.example.springsecurity.lesson2.entities.MyUser;
import org.example.springsecurity.lesson2.repositories.MyUsersRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MyUsersJpaUserDetailsService implements UserDetailsService {

    private final MyUsersRepository myUsersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MyUser> myUser = myUsersRepository.findMyUserByUsername(username);

        return myUser
                .map(MyWrapperUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + ", not found"));

    }
}
