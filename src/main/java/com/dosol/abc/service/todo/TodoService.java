package com.dosol.abc.service.todo;

import com.dosol.abc.domain.todo.Todo;
import com.dosol.abc.dto.todo.PageRequestDTO;
import com.dosol.abc.dto.todo.PageResponseDTO;
import com.dosol.abc.dto.todo.TodoDTO;

public interface TodoService {
    PageResponseDTO<TodoDTO> getList(PageRequestDTO pageRequestDTO);

    Todo getTodo(Long todoId);
    void saveTodo(Todo todo);
    void updateTodo(Todo todo);
    void deleteTodo(Long todoId);
}

