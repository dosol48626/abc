package com.dosol.abc.repository.board;

import com.dosol.abc.domain.board.Board;
import com.dosol.abc.domain.user.User;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
        IntStream.rangeClosed(1, 30).forEach(i -> {
            Board board = Board.builder()
                    .user(User.builder().userId(1L).build())
                    .title("Title" + i)
                    .content("Content" + i)
                    .build();

            Board result = boardRepository.save(board);
            log.info("bno" + result.getBoardId());
        });
    }

    @Test
    public void testFindByBno() {
        Long boardID = 1L;
        Optional<Board> result = boardRepository.findById(boardID);
        log.info("bno" + boardID);
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

    @Test
    public void testSearchAll() {
        // Given: 테스트 데이터를 준비합니다.
        Board board1 = new Board();
        board1.setTitle("Spring Boot Guide");
        board1.setContent("This is content about Spring Boot");
        board1.setUsername("user1");
        boardRepository.save(board1);

        Board board2 = new Board();
        board2.setTitle("Java Programming");
        board2.setContent("This is content about Java");
        board2.setUsername("user2");
        boardRepository.save(board2);

        // When: 검색 조건을 설정합니다.
        String[] types = {"t", "c", "w"};
        String keyword = "Spring";
        Pageable pageable = PageRequest.of(0, 10); // 첫 번째 페이지, 10개씩 표시

        // 검색 메서드 호출
        Page<Board> result = boardRepository.searchAll(types, keyword, pageable);

        // Then: 결과 검증을 로그로 출력
        if (!result.isEmpty()) {
            log.info("검색 결과가 존재합니다. 총 {} 개의 항목이 검색되었습니다.", result.getTotalElements());

            for (Board board : result.getContent()) {
                log.info("검색된 게시글 - Title: {}, Username: {}", board.getTitle(), board.getUsername());
            }
        } else {
            log.info("검색 결과가 없습니다.");
        }
    }
}