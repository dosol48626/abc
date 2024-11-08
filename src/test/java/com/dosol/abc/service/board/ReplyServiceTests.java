package com.dosol.abc.service.board;

import com.dosol.abc.domain.board.Board;
import com.dosol.abc.domain.board.Reply;
import com.dosol.abc.domain.user.User;
import com.dosol.abc.dto.board.BoardDTO;
import com.dosol.abc.dto.board.PageRequestDTO;
import com.dosol.abc.dto.board.PageResponseDTO;
import com.dosol.abc.dto.board.ReplyDTO;
import com.dosol.abc.repository.board.BoardRepository;
import com.dosol.abc.repository.board.ReplyRepository;
import com.dosol.abc.repository.user.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2

public class ReplyServiceTests {

    @Autowired
    private ReplyService replyService;

    @Test
    public void testRegister() {
        // Given
        Long boardId = 3L;
        Long userId = 1L;

        // 댓글 내용과 게시글 ID, 유저 ID를 이용하여 ReplyDTO 생성
        ReplyDTO replyDTO = ReplyDTO.builder()
                .userId(userId)
                .boardId(boardId)
                .replyText("Ttes 제발 좀 들어가라")
                .build();

        // When
        Long replyId = replyService.register(replyDTO);

        log.info("@@@@@@@@@@"+replyId);
    }

    @Test
    public void testFindById() {
        // Assuming a reply with ID 1 exists for testing
        Long replyId = 1L;

        // When
        ReplyDTO replyDTO = replyService.findById(replyId);

        // Then
        Assertions.assertNotNull(replyDTO);
        Assertions.assertEquals(replyId, replyDTO.getReplyId());
        log.info("Found reply: " + replyDTO);
    }

    @Test
    public void testModify() {
        // Assuming a reply with ID 1 exists
        Long replyId = 1L;
        String updatedText = "히나 이쁘다";

        // Find the reply, update its text, and save
        ReplyDTO replyDTO = replyService.findById(replyId);
        replyDTO.setReplyText(updatedText);

        // When
        replyService.modify(replyDTO);

        // Then
        ReplyDTO modifiedReply = replyService.findById(replyId);
        Assertions.assertEquals(updatedText, modifiedReply.getReplyText());
        log.info("Modified reply: " + modifiedReply);
    }

    @Test
    public void testRemove() {

        Long replyId = 1L;

        replyService.remove(replyId);
    }


    @Test
    public void testGetListOfBoard() {
        // Given
        Long boardId = 3L;  // Assuming a board with ID 1 exists
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).build();

        // When
        var response = replyService.getListOfBoard(boardId, pageRequestDTO);

        // Then
        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.getDtoList().size() > 0);
        log.info("Replies for board ID " + boardId + ": " + response.getDtoList());
    }

    @Test
    public void testGetListOfBoard2() {

        Long boardId = 1L;
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).build();
        var response = replyService.getListOfBoard(boardId, pageRequestDTO);
        log.info("Replies for board ID " + boardId + ": " + response.getDtoList());


    }
}
