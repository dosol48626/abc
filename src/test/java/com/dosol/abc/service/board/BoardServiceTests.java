package com.dosol.abc.service.board;

import com.dosol.abc.dto.board.BoardDTO;
import com.dosol.abc.dto.board.PageRequestDTO;
import com.dosol.abc.dto.board.PageResponseDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class BoardServiceTests {

    @Autowired
    private BoardService boardService;

    @Test
    public void testRegister() {

        BoardDTO boardDTO = BoardDTO.builder()
                .username("qwer1")
                .title("Sample Title")
                .content("Sample Content")
                .build();

        Long boardId = boardService.register(boardDTO);

        log.info("boardId :" + boardId);
    }

    @Test
    public void testModify() {

        BoardDTO boardDTO = BoardDTO.builder()
                .boardId(101L)
                .title("Updated 101")
                .content("Updated 101")
                .build();

        boardService.modify(boardDTO);
    }

    @Test
    public void testDelete() {
        Long boardId = 101L;
        boardService.remove(boardId);
    }

    @Test
    public void testFindById(){
        Long boardId = 102L;
        boardService.readOne(boardId);
        log.info("boardId :" + boardId);
    }

    @Test
    public void testFindById2() {
        Long boardId = 102L;
        BoardDTO boardDTO = boardService.readOne(boardId);

        if (boardDTO != null) {
            log.info("Board Details:");
            log.info("Title: " + boardDTO.getTitle());
            log.info("Content: " + boardDTO.getContent());
            log.info("username: " + boardDTO.getUsername());
        } else {
            log.info("Board with ID " + boardId + " not found.");
        }
    }

    @Test
    public void testList() {

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .type("tcw")
                .keyword("1")
                .page(1)
                .size(10)
                .build();

        PageResponseDTO<BoardDTO> pageResponseDTO = boardService.list(pageRequestDTO);

        log.info("결과!!###!!@@@@@##!!!@!" + pageResponseDTO);
    }
}