package com.dosol.abc.controller.board;

import com.dosol.abc.domain.user.User;
import com.dosol.abc.dto.board.BoardDTO;
import com.dosol.abc.service.board.BoardService;
import com.dosol.abc.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping("/list")
    public void list(Model model) {
        List<BoardDTO> boardDTOS = boardService.readAll();
        model.addAttribute("boardDTOS", boardDTOS);
    }

    @GetMapping("/register")
    public void registerGET() {
    }

    @PostMapping("/register")
    public String registerPOST(BoardDTO boardDTO, HttpSession session) {
        User user = (User)session.getAttribute("user");
        boardDTO.setUsername(user.getUsername());
        Long bno = boardService.register(boardDTO);
        return "redirect:/board/list";
    }

    @GetMapping("/read")
    public void readGET(Model model, Long boardId) {
        BoardDTO boardDTO = boardService.readOne(boardId);
        model.addAttribute("boardDTO", boardDTO);
    }
}
