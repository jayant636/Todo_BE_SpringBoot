package com.example.TodoManagement.service;

import com.example.TodoManagement.dtos.LoginDto;
import com.example.TodoManagement.dtos.RegisterDto;
import com.example.TodoManagement.entity.Role;
import com.example.TodoManagement.entity.User;
import com.example.TodoManagement.repository.RoleRepository;
import com.example.TodoManagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

//    Register API
    public String register(RegisterDto registerDto){
        Boolean user = userRepository.existsByEmail(registerDto.getEmail());
        if(user) throw new RuntimeException("User already exists with this email"+user);
        User user1 = new User();
        user1.setUsername(registerDto.getUsername());
        user1.setEmail(registerDto.getEmail());
        user1.setPassword(passwordEncoder.encode(registerDto.getPassword()));


        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_User");
        roles.add(userRole);

        user1.setRoles(roles);
        userRepository.save(user1);

        return "User Registered Successfully";
    }

//    Login API
    public String login(LoginDto loginDto) {
        Boolean user = userRepository.existsByEmail(loginDto.getEmail());
        if(!user) throw new RuntimeException("User doesn't exists with this email Id"+loginDto.getEmail());

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(),
                loginDto.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "User Logged In Successfully";
    }
}
