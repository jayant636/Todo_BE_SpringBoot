package com.example.TodoManagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests((auth)->{
                    auth.requestMatchers(HttpMethod.POST,"/api/**").hasRole("Admin");
                    auth.requestMatchers(HttpMethod.PUT,"/api/**").hasRole("Admin");
                    auth.requestMatchers(HttpMethod.DELETE,"/api/**").hasRole("Admin");
                    auth.requestMatchers(HttpMethod.GET,"/api/**").permitAll();
                    auth.requestMatchers(HttpMethod.PATCH,"/api/**").permitAll();

                    auth.anyRequest().authenticated();
                }).httpBasic(Customizer.withDefaults());
        return httpSecurity.build();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails user  = User.builder()
                .username("user")
                .password(passwordEncoder().encode("1234"))
                .roles("User")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("5678"))
                .roles("Admin")
                .build();

        return new InMemoryUserDetailsManager(user,admin);
    }
}
