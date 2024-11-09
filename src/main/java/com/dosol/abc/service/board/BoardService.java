package com.dosol.abc.service.board;

import com.dosol.abc.domain.board.Board;
import com.dosol.abc.domain.note.Notes;
import com.dosol.abc.domain.user.User;
import com.dosol.abc.dto.board.BoardDTO;
import com.dosol.abc.dto.board.PageRequestDTO;
import com.dosol.abc.dto.board.PageResponseDTO;
import com.dosol.abc.dto.note.NotesDTO;

import java.util.List;
import java.util.stream.Collectors;

public interface BoardService {
    //List<BoardDTO> readAll();

    PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO);

    Long register(BoardDTO boardDTO);

    BoardDTO readOne(Long boardId);

    void modify(BoardDTO boardDTO);

    void remove(Long boardId);



    default Board dtoToEntity(BoardDTO boardDTO){

//        User user = new User();
//        user.setUsername(boardDTO.getUsername());

        Board board = Board.builder()
                .boardId(boardDTO.getBoardId())
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())

                .build();
        if(boardDTO.getFileNames() != null){
            boardDTO.getFileNames().forEach(fileName -> {
                String[] arr = fileName.split("_");
                board.addImage(arr[0], arr[1]);
            });
        }
        return board;
    }
    default BoardDTO entityToDTO(Board board) {
        BoardDTO boardDTO = BoardDTO.builder()
                .boardId(board.getBoardId())
                .title(board.getTitle())
                .content(board.getContent())
                .username(board.getUser().getUsername())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .build();

        List<String> fileNames =
                board.getImageSet().stream().sorted().map(boardImage ->
                                boardImage.getUuid()+"_"+boardImage.getFileName())
                        .collect(Collectors.toList());
        boardDTO.setFileNames(fileNames);
        return boardDTO;
    }

}
