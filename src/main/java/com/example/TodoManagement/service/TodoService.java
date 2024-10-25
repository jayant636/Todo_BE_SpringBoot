package com.example.TodoManagement.service;

import com.example.TodoManagement.dtos.TodoDto;
import com.example.TodoManagement.entity.TodoEntity;
import com.example.TodoManagement.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final ModelMapper modelMapper;

    public TodoDto createNewTask(TodoDto todoDto) {
        TodoEntity todoEntity = modelMapper.map(todoDto,TodoEntity.class);
        TodoEntity todoEntity1 = todoRepository.save(todoEntity);
        return modelMapper.map(todoEntity1, TodoDto.class);
    }

    public List<TodoDto> getAllTodos() {
        List<TodoEntity> todoEntities = todoRepository.findAll();
        return todoEntities.stream().map(todoEntity -> modelMapper.map(todoEntity, TodoDto.class)).collect(Collectors.toList());
    }

    public Optional<TodoDto> getTodoById(Long id) {
        Optional<TodoEntity> todoEntity = todoRepository.findById(id);
        return todoEntity.map(todoEntity1 -> modelMapper.map(todoEntity1, TodoDto.class));
    }

    public TodoDto updateTodo(Long id, TodoDto todoDto) {
        boolean userExist = todoRepository.existsById(id);
        if(!userExist) throw new RuntimeException("Todo doesn't exist by id:"+id);

        TodoEntity todoEntity = modelMapper.map(todoDto, TodoEntity.class);
        todoEntity.setId(id);
        TodoEntity todoEntity1 = todoRepository.save(todoEntity);
        return modelMapper.map(todoEntity1, TodoDto.class);
    }

    public void deleteTodoById(Long id) {
        boolean userExist = todoRepository.existsById(id);
        if(!userExist) throw new RuntimeException("Todo doesn't exist by id:"+id);
        todoRepository.deleteById(id);
    }

    public TodoDto updatestatusCompleted(Long id) {

        TodoEntity todoEntity = todoRepository.findById(id).orElseThrow(()-> new RuntimeException("Todo doesn't exist"));
        todoEntity.setCompleted(Boolean.TRUE);
        TodoEntity todoEntity1 = todoRepository.save(todoEntity);
        return modelMapper.map(todoEntity1, TodoDto.class);

    }
}
