package com.example.TodoManagement.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class TodoApiException extends RuntimeException{

    private HttpStatus status;
    private String message;
}
