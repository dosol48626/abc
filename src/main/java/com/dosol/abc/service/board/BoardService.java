package com.dosol.abc.service.board;

import com.dosol.abc.domain.user.User;
import com.dosol.abc.dto.board.BoardDTO;
import com.dosol.abc.dto.board.PageRequestDTO;
import com.dosol.abc.dto.board.PageResponseDTO;

import java.util.List;

public interface BoardService {
    //List<BoardDTO> readAll();

    PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO);

    Long register(BoardDTO boardDTO);

    BoardDTO readOne(Long bno);

    void modify(BoardDTO boardDTO);

    void remove(Long bno);
}
