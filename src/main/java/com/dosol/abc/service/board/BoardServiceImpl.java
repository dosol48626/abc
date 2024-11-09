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
        log.info("아니 왜 user 정보를 다 가지고 오냐고@@@@@@@@"+dtoList);

//        List<BoardDTO> dtoList = result.getContent().stream()
//                .map(board -> entityToDTO(board)) // entityToDTO 메서드를 사용
//                .collect(Collectors.toList());


        return PageResponseDTO.<BoardDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }


//    @Override
//    public List<BoardDTO> readAll() {
//        List<Board> boards = boardRepository.findAll();
//        List<BoardDTO> boardDTOs = boards.stream()
//                .map(board -> modelMapper.map(board, BoardDTO.class))
//                .collect(Collectors.toList());
//        return boardDTOs;
//    }


//    @Override
//    public Long register(BoardDTO boardDTO) {
//        Board board = modelMapper.map(boardDTO, Board.class);
//        User user = userRepository.findByUsername(boardDTO.getUsername());
//        board.setUser(user);
//        Long bno = boardRepository.save(board).getBoardId();
//        return bno;
//    }

    @Override
    public Long register(BoardDTO boardDTO) {
        // BoardDTO를 Board 엔티티로 변환
        //Board board = modelMapper.map(boardDTO, Board.class);

        Board board = dtoToEntity(boardDTO);
        User user = userRepository.findByUsername(boardDTO.getUsername());
        board.setUser(user);

        // fileNames를 Board의 imageSet에 추가
        if (boardDTO.getFileNames() != null) {
            boardDTO.getFileNames().forEach(fileName -> {
                String[] parts = fileName.split("_", 2); // uuid와 원래 파일명을 분리
                String uuid = parts[0];
                String originalFileName = parts[1];
                board.addImage(uuid, originalFileName);

            });
        }

        return boardRepository.save(board).getBoardId();
    }


    @Override
    public BoardDTO readOne(Long bno) {
        Optional<Board> result = boardRepository.findById(bno);
        Board board = result.orElseThrow();
        board.updateVisitCount();
        BoardDTO boardDTO = entityToDTO(board);
        //BoardDTO boardDTO = modelMapper.map(board, BoardDTO.class); 이제 새로 만든거 쓸거다
        return boardDTO;
    }

    @Override
    public void modify(BoardDTO boardDTO) {
        Optional<Board> result = boardRepository.findById(boardDTO.getBoardId());
        Board board = result.orElseThrow();

        board.change(boardDTO.getTitle(), boardDTO.getContent());

        board.clearImages();
        if(boardDTO.getFileNames()!=null) {
            for (String fileName : boardDTO.getFileNames()) {
                String[] arr=fileName.split("_");
                board.addImage(arr[0], arr[1]);
            }
        }

        boardRepository.save(board);
    }

    @Override
    public void remove(Long bno) {
        boardRepository.deleteById(bno);
    }
}
