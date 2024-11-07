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
                .title("Sample Title")
                .content("Sample Content")
                .build();

        Long bno = boardService.register(boardDTO);

        log.info("bno :" + bno);
    }

    @Test
    public void testModify() {

        BoardDTO boardDTO = BoardDTO.builder()
                .boardId(2L)
                .title("Updated 2")
                .content("Updated 2")
                .build();

        boardService.modify(boardDTO);
    }

    @Test
    public void testDelete() {
        Long bno = 2L;
        boardService.remove(bno);
    }

    @Test
    public void testFindById(){
        Long bno = 3L;
        boardService.readOne(bno);
        log.info("bno :" + bno);
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