package com.dosol.abc.repository.board;

import com.dosol.abc.domain.board.Board;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void testInsert() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Board board = Board.builder()
                    .title("Title" + i)
                    .content("Content" + i)
                    .build();

            Board result = boardRepository.save(board);
            log.info("bno" + result.getBoardId());
        });
    }

    @Test
    public void testFindByBno() {
        Long bno = 1L;
        Optional<Board> result = boardRepository.findById(bno);
        log.info("bno" + bno);
    }

    @Test
    public void testUpdate() {
        Long bno = 1L;
        Optional<Board> result = boardRepository.findById(bno);
        log.info("bno" + bno);
        Board board = result.get();
        board.setTitle("update" + bno);
        board.setContent("update" + bno);
        boardRepository.save(board);
    }

    @Test
    public void testDelete() {
        Long bno = 1L;
        boardRepository.deleteById(bno);
    }

    @Test
    public void testFindAll() {
        List<Board> result = boardRepository.findAll();
        log.info("result" + result);
    }
}