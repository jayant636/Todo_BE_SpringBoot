package com.example.TodoManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TodoDto {

    private Long id;
    private String title;
    private String description;
    private Boolean completed;
}
