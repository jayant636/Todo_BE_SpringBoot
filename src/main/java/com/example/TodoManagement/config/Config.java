package com.example.TodoManagement.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

//    ModelMapper -> simplify the process of mapping objects from one type to another
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
