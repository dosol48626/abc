package com.dosol.abc.controller.board;

import com.dosol.abc.domain.user.User;
import com.dosol.abc.dto.board.BoardDTO;
import com.dosol.abc.dto.board.PageRequestDTO;
import com.dosol.abc.dto.board.PageResponseDTO;
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

//    @GetMapping("/list")
//    public void list(Model model) {
//        List<BoardDTO> boardDTOS = boardService.readAll();
//        model.addAttribute("boardDTOS", boardDTOS);
//    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        PageResponseDTO<BoardDTO> responseDTO = boardService.list(pageRequestDTO);
        log.info(responseDTO);

        model.addAttribute("responseDTO", responseDTO);
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
    public void readGET(Model model, Long boardId, HttpSession session) {
        User user = (User)session.getAttribute("user");

        // 세션에 로그인된 사용자의 username을 모델에 추가
        if (user != null) {
            model.addAttribute("username", user.getUsername());
        }

        // 게시글 데이터를 가져와 모델에 추가
        BoardDTO boardDTO = boardService.readOne(boardId);
        model.addAttribute("boardDTO", boardDTO);
    }

    @GetMapping("/modify")
    public void modifyGET(Model model, Long boardId) {
        BoardDTO boardDTO = boardService.readOne(boardId);
        model.addAttribute("boardDTO", boardDTO);
    }

    @PostMapping("/modify")
    public String modifyPOST(BoardDTO boardDTO) {

        boardService.modify(boardDTO);
        return "redirect:/board/read?boardId=" + boardDTO.getBoardId();
    }

    @GetMapping("/remove")
    public String removeGET(Long boardId) {
        boardService.remove(boardId);
        return "redirect:/board/list";
    }
}
