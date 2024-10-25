package com.example.TodoManagement.controller;

import com.example.TodoManagement.dtos.TodoDto;
import com.example.TodoManagement.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "/api/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<TodoDto> createTask(@RequestBody TodoDto todoDto){
        TodoDto todoDto1 = todoService.createNewTask(todoDto);
        return new ResponseEntity<>(todoDto1, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TodoDto>> getAllTodos(){
        List<TodoDto> todoDtos = todoService.getAllTodos();
        return ResponseEntity.ok(todoDtos);
     }

     @GetMapping(path = "/{id}")
    public ResponseEntity<TodoDto> getTodoById(@PathVariable Long id){
         Optional<TodoDto> todoDto = todoService.getTodoById(id);
         return todoDto.map(todoDto1 -> ResponseEntity.ok(todoDto1)).orElseThrow(()-> new RuntimeException("Todo not found with id :"+id));
     }

     @PutMapping(path = "/update/{id}")
     public ResponseEntity<TodoDto> updateDto(@PathVariable Long id, @RequestBody TodoDto todoDto){
        TodoDto todoDto1 = todoService.updateTodo(id,todoDto);
        return ResponseEntity.ok(todoDto1);
     }

     @DeleteMapping("/{id}")
    public String deleteTodo(@PathVariable Long id){
        todoService.deleteTodoById(id);
        return "Todo deleted Successfully";
     }

     @PatchMapping("/{id}")
        public ResponseEntity<TodoDto> updatestatusCompleted(@PathVariable Long id){
        TodoDto todoDto1 = todoService.updatestatusCompleted(id);
        return ResponseEntity.ok(todoDto1);
     }

}
