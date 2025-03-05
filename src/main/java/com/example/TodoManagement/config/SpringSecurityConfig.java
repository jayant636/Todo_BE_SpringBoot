package com.example.TodoManagement.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SpringSecurityConfig {

    private final UserDetailsService userDetailsService;

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests((auth)->{
                    auth.requestMatchers(HttpMethod.POST,"/api/**").permitAll();
                    auth.requestMatchers(HttpMethod.PUT,"/api/**").hasRole("Admin");
                    auth.requestMatchers(HttpMethod.DELETE,"/api/**").hasRole("Admin");
                    auth.requestMatchers(HttpMethod.GET,"/api/**").permitAll();
                    auth.requestMatchers(HttpMethod.PATCH,"/api/**").permitAll();

                    auth.anyRequest().authenticated();
                }).httpBasic(Customizer.withDefaults());
        return httpSecurity.build();
    }



//    InMemory authentication

//    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails user  = User.builder()
//                .username("user")
//                .password(passwordEncoder().encode("1234"))
//                .roles("User")
//                .build();
//
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(passwordEncoder().encode("5678"))
//                .roles("Admin")
//                .build();
//
//        return new InMemoryUserDetailsManager(user,admin);
//    }
}
