package com.dosol.abc.service.todo;

import com.dosol.abc.dto.todo.PageRequestDTO;
import com.dosol.abc.dto.todo.PageResponseDTO;
import com.dosol.abc.dto.todo.TodoDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@Log4j2
public class TodoServiceTest {
    @Autowired
    private TodoService todoService;


    @Test
    public void testList(){
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .type("td")
                .keyword("1")
                .page(1)
                .size(7)
                .build();
        PageResponseDTO<TodoDTO> responseDTO = todoService.getList(pageRequestDTO);

    }
}
