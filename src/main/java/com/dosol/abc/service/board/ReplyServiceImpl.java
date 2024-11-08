package com.dosol.abc.service.board;

import com.dosol.abc.domain.board.Board;
import com.dosol.abc.domain.board.Reply;
import com.dosol.abc.domain.user.User;
import com.dosol.abc.dto.board.PageRequestDTO;
import com.dosol.abc.dto.board.PageResponseDTO;
import com.dosol.abc.dto.board.ReplyDTO;
import com.dosol.abc.repository.board.BoardRepository;
import com.dosol.abc.repository.board.ReplyRepository;
import com.dosol.abc.repository.user.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {
    private final ReplyRepository replyRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final ModelMapper modelMapper;


    @Override
    public Long register(ReplyDTO replyDTO) {
        // Reply 객체 생성
        Reply reply = new Reply();
        reply.setReplyText(replyDTO.getReplyText());

        // Board와 User 객체를 명확히 설정
        Board board = new Board();
        board.setBoardId(replyDTO.getBoardId());
        reply.setBoard(board);

        User user = new User();
        user.setUserId(replyDTO.getUserId());
        reply.setUser(user);

        // Repository에 저장하고 Reply ID 반환
        Long replyId = replyRepository.save(reply).getReplyId();
        return replyId;
    }



//    @Override
//    public Long register(ReplyDTO replyDTO) {
//        Reply reply = modelMapper.map(replyDTO, Reply.class);
//        Long replyId = replyRepository.save(reply).getReplyId();
//        return replyId;
//    }

    @Override
    public ReplyDTO findById(Long replyId) {
        Optional<Reply> replyOptional = replyRepository.findById(replyId);
        Reply reply = replyOptional.orElseThrow();
        //정상적으로 못꺼냈을때, 예외처리를 해주는거임.
        return modelMapper.map(reply, ReplyDTO.class);
    }

    @Override
    public void modify(ReplyDTO replyDTO) {
        Optional<Reply> replyOptional = replyRepository.findById(replyDTO.getReplyId());
        Reply reply = replyOptional.orElseThrow();
        reply.changeText(replyDTO.getReplyText());
        replyRepository.save(reply);
    }

    @Override
    public void remove(Long replyId) {
        replyRepository.deleteById(replyId);
    }

    @Override
    public PageResponseDTO<ReplyDTO> getListOfBoard(Long boardId, PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(pageRequestDTO
                        .getPage() <=0? 0: pageRequestDTO.getPage() -1,
                pageRequestDTO.getSize(),
                Sort.by("replyId").ascending());

        Page<Reply> result = replyRepository.listOfBoard(boardId, pageable);

        List<ReplyDTO> dtoList =
                result.getContent().stream().map(reply -> modelMapper.map(reply, ReplyDTO.class))
                        .collect(Collectors.toList());

        return PageResponseDTO.<ReplyDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }
}
