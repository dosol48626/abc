package com.dosol.abc.repository.board;

import com.dosol.abc.domain.board.Board;
import com.dosol.abc.domain.board.Reply;
import com.dosol.abc.domain.user.User;
import com.dosol.abc.repository.user.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class ReplyRepositoryTests {

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;


    @Test
    public void testInsert() {
        // 3번 게시글을 조회
        Board board = boardRepository.findById(3L).orElseThrow(() -> new IllegalArgumentException("Board not found"));
        User user = userRepository.findById(1L).orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 3번 게시글의 작성자 정보
        //String username = board.getUsername();

        // 3번 게시글에 댓글 30개 추가
//        IntStream.rangeClosed(1, 10).forEach(i -> {
            Reply reply = Reply.builder()
                    .board(board)                   // 3번 게시글 설정
                    .user(user)
                    .replyText("쵸단 이쁘다 ")      // 댓글 내용
                    .build();

            Reply result = replyRepository.save(reply);
            log.info("Saved Reply@@@@@@@@##########!!!!: " + result);
//        });
    }

    @Test
    public void testUpdate() {
        Long replyId = 1L;

        Reply reply = replyRepository.findById(replyId).orElseThrow(() -> new IllegalArgumentException("Reply not found"));
        reply.setReplyText("qwer이쁘다");
        replyRepository.save(reply);
    }

    @Test
    public void testDelete() {
        Long replyId = 1L;
        replyRepository.deleteById(replyId);
    }
}
