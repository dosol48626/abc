package com.dosol.abc.repository.wise;

import com.dosol.abc.domain.todo.Todo;
import com.dosol.abc.domain.wise.Wise;
import com.dosol.abc.repository.todo.TodoRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class WiseRepositoryTest {
    @Autowired
    private WiseRepository wiseRepository;

    @Test
    public void testInsert() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Wise wise = Wise.builder()
                    .content("Content" + i)
                    .build();

            Todo result = WiseRepository.save(wise);
            log.info("WiseId" + result.getTodoId());
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
