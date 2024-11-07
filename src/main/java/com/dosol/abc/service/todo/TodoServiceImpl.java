package com.dosol.abc.service.todo;

import com.dosol.abc.domain.todo.Todo;
import com.dosol.abc.dto.todo.PageRequestDTO;
import com.dosol.abc.dto.todo.PageResponseDTO;
import com.dosol.abc.dto.todo.TodoDTO;
import com.dosol.abc.repository.todo.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Log4j2
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final ModelMapper modelMapper;

    @Override
    public PageResponseDTO<TodoDTO> getList(PageRequestDTO pageRequestDTO) {
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        String pageType = pageRequestDTO.getPageType();
        Pageable pageable = pageRequestDTO.getPageable(Sort.Direction.ASC, "dueDate");

        //Page<Todo> result = todoRepository.findAll(pageable);
        Page<Todo> result = todoRepository.searchAll(types,keyword,pageable,pageType);

        List<TodoDTO> dtoList = result.getContent().stream()
                .map(todo -> modelMapper.map(todo, TodoDTO.class))
                .collect(Collectors.toList());

        return PageResponseDTO.<TodoDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }

    @Override
    public Todo getTodo(Long todoId) {
        log.info("getTodo"+todoId);
        return todoRepository.findById(todoId).get();
    }

    @Override
    public void saveTodo(Todo todo) {
        log.info("Saving Todo: " + todo);
        todoRepository.save(todo);

    }

    @Override
    public void updateTodo(Todo todo) {
        log.info("Update todo"+todo);
        Todo oldTodo = todoRepository.findById(todo.getTodoId()).get();
        oldTodo.setTitle(todo.getTitle());
        oldTodo.setDescription(todo.getDescription());
        oldTodo.setDueDate(todo.getDueDate());
        oldTodo.setComplete(todo.getComplete() != null ? todo.getComplete() : false);

        todoRepository.save(oldTodo);

    }

    @Override
    public void deleteTodo(Long todoId) {
        log.info("Delete Todo+" + todoId);
        todoRepository.deleteById(todoId);

    }
}
