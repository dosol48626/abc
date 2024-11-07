package com.dosol.abc.service.board;

import com.dosol.abc.dto.board.BoardDTO;
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

        Long bno = boardService.register(boardDTO);

        log.info("bno :" + bno);
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
        Long bno = 101L;
        boardService.remove(bno);
    }

    @Test
    public void testFindById(){
        Long bno = 102L;
        boardService.readOne(bno);
        log.info("bno :" + bno);
    }

    @Test
    public void testFindById2() {
        Long bno = 102L;
        BoardDTO boardDTO = boardService.readOne(bno);

        if (boardDTO != null) {
            log.info("Board Details:");
            log.info("Title: " + boardDTO.getTitle());
            log.info("Content: " + boardDTO.getContent());
            log.info("username: " + boardDTO.getUsername());
        } else {
            log.info("Board with ID " + bno + " not found.");
        }
    }

//    @Test
//    public void testList() {
//
//        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
//                .type("tcw")
//                .keyword("1")
//                .page(1)
//                .size(10)
//                .build();
//
//        PageResponseDTO<BoardDTO> pageResponseDTO = boardService.list(pageRequestDTO);
//
//        log.info(pageResponseDTO);
//    }
}