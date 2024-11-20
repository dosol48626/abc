package com.dosol.abc.controller.board;

import com.dosol.abc.dto.board.PageRequestDTO;
import com.dosol.abc.dto.board.PageResponseDTO;
import com.dosol.abc.dto.board.ReplyDTO;
import com.dosol.abc.service.board.ReplyService;
import com.dosol.abc.domain.user.User;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/replies")
public class ReplyController {
    private final ReplyService replyService;

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Long> register(
            @Valid @RequestBody ReplyDTO replyDTO,
            BindingResult bindingResult,
            HttpSession session) throws BindException {
        log.info(replyDTO);

        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new RuntimeException("로그인한 사용자만 댓글을 작성할 수 있습니다.");
        }
        replyDTO.setUserId(user.getUserId());  // <-- 수정: userId를 세팅

        Long replyId = replyService.register(replyDTO);
        Map<String, Long> resultMap = new HashMap<>();
        resultMap.put("replyId", replyId);
        return resultMap;
    }

    @GetMapping(value = "/list/{boardId}")
    public PageResponseDTO<ReplyDTO> getList(@PathVariable("boardId") Long boardId,
                                             PageRequestDTO pageRequestDTO) {

        PageResponseDTO<ReplyDTO> responseDTO = replyService.getListOfBoard(boardId, pageRequestDTO);
        return responseDTO;
    }

    @GetMapping("/{replyId}")
    public ReplyDTO getReplyDTO(@PathVariable("replyId") Long replyId) {
        ReplyDTO replyDTO = replyService.findById(replyId);
        return replyDTO;
    }

    @DeleteMapping("/{replyId}")
    public Map<String, Long> remove(@PathVariable("replyId") Long replyId, HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new RuntimeException("로그인한 사용자만 댓글을 삭제할 수 있습니다.");
        }

        ReplyDTO replyDTO = replyService.findById(replyId);
        if (!replyDTO.getUserId().equals(user.getUserId())) {
            throw new RuntimeException("작성자만 댓글을 삭제할 수 있습니다.");
        }

        replyService.remove(replyId);

        Map<String, Long> resultMap = new HashMap<>();
        resultMap.put("replyId", replyId);

        return resultMap;
    }

    @PutMapping(value = "/{replyId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Long> modify(
            @PathVariable("replyId") Long replyId,
            @RequestBody ReplyDTO replyDTO,
            HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new RuntimeException("로그인한 사용자만 댓글을 수정할 수 있습니다.");
        }

        ReplyDTO existingReply = replyService.findById(replyId);
        if (!existingReply.getUserId().equals(user.getUserId())) {
            throw new RuntimeException("작성자만 댓글을 수정할 수 있습니다.");
        }

        replyDTO.setReplyId(replyId); // 번호를 일치시킴
        replyService.modify(replyDTO);

        Map<String, Long> resultMap = new HashMap<>();
        resultMap.put("replyId", replyId);

        return resultMap;
    }
}
