package com.bharatkart.userservice.service;

import com.bharatkart.userservice.Repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user=usersRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User not found: "+username));
        return User.builder().username(user.getUsername())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }
}
