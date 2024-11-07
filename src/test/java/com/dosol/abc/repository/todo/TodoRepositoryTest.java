package com.dosol.abc.repository.todo;

import com.dosol.abc.domain.todo.Todo;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class TodoRepositoryTest {
    @Autowired
    private TodoRepository todoRepository;
    @Test
    public void testInsert() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Todo todo = Todo.builder()
                    .title("Title" + i)
                    .memberId("sample member")
                    .description("Description" + i)
                    .complete(false)
                    .dueDate(LocalDate.now())
                    .build();

            Todo result = todoRepository.save(todo);
            log.info("TodoId" + result.getTodoId());
        });


    }

    @Test
    public void testSelect() {
        Long todoId = 100L;

        Optional<Todo> result = todoRepository.findById(todoId);

        Todo todo = result.orElseThrow();

        log.info(todo);
    }
}