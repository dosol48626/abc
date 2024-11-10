package com.dosol.abc.service.board;

import com.dosol.abc.domain.user.User;
import com.dosol.abc.dto.board.PageRequestDTO;
import com.dosol.abc.dto.board.PageResponseDTO;
import com.dosol.abc.repository.board.BoardRepository;
import com.dosol.abc.domain.board.Board;
import com.dosol.abc.dto.board.BoardDTO;
import com.dosol.abc.repository.user.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO) {
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("boardId");

        Page<Board> result = boardRepository.searchAll(types, keyword, pageable);

        List<BoardDTO> dtoList = result.getContent().stream()
                .map(board -> modelMapper.map(board, BoardDTO.class))
                .collect(Collectors.toList());

        log.info("Board List DTOs: " + dtoList);

        return PageResponseDTO.<BoardDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }

    @Override
    public Long register(BoardDTO boardDTO) {
        Board board = dtoToEntity(boardDTO);
        User user = userRepository.findByUsername(boardDTO.getUsername());
        if (user == null) {
            throw new RuntimeException("유저를 찾을 수 없습니다.");
        }
        board.setUser(user);

        if (boardDTO.getFileNames() != null) {
            boardDTO.getFileNames().forEach(fileName -> {
                String[] parts = fileName.split("_", 2);
                String uuid = parts[0];
                String originalFileName = parts[1];
                board.addImage(uuid, originalFileName);
            });
        }
        return boardRepository.save(board).getBoardId();
    }

    @Override
    public BoardDTO readOne(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() ->
                new RuntimeException("해당 게시글이 존재하지 않습니다."));
        board.updateVisitCount();
        return entityToDTO(board);
    }

    @Override
    public void modify(BoardDTO boardDTO) {
        Board board = boardRepository.findById(boardDTO.getBoardId()).orElseThrow(() ->
                new RuntimeException("수정할 게시글을 찾을 수 없습니다."));
        board.change(boardDTO.getTitle(), boardDTO.getContent());

        board.clearImages();
        if (boardDTO.getFileNames() != null) {
            boardDTO.getFileNames().forEach(fileName -> {
                String[] parts = fileName.split("_", 2);
                board.addImage(parts[0], parts[1]);
            });
        }
        boardRepository.save(board);
    }

    @Override
    public void remove(Long boardId) {
        boardRepository.deleteById(boardId);
    }
}
