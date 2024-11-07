package com.dosol.abc.repository.todo.search;

import com.dosol.abc.domain.todo.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TodoSearch {
    Page<Todo> searchAll(String[] types, String keyword, Pageable pageable, String pageType);
}

