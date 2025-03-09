package com.example.TodoManagement.service;

import com.example.TodoManagement.entity.User;
import com.example.TodoManagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
       User user = userRepository.findByUsernameOrEmail(usernameOrEmail,usernameOrEmail).orElseThrow(()-> new RuntimeException("User doesn't exist with this email"+usernameOrEmail));
        Set<GrantedAuthority> authorities = user.getRoles().stream()
                .map((role)-> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());


        return new org.springframework.security.core.userdetails.User(
                usernameOrEmail,
                user.getPassword(),
                authorities
        );
    }
}
