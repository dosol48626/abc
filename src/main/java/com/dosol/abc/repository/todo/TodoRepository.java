package com.dosol.abc.repository.todo;

import com.dosol.abc.domain.todo.Todo;
import com.dosol.abc.repository.todo.search.TodoSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

 public interface TodoRepository extends JpaRepository<Todo,Long>, TodoSearch {
   /* @Query("select b from Todo b where b.title like concat('%',:keyword,'%') order by b.todo_id desc")
    Page<Todo>searchAll(String keyword, Pageable pageable)*/;
        @Query("SELECT t FROM Todo t WHERE t.dueDate = :dueDate")
        Page<Todo> findByDueDate(LocalDate dueDate, Pageable pageable);

    }
