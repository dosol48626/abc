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
        User user = userRepository.findById(replyDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));  // <-- findById로 수정

        Board board = boardRepository.findById(replyDTO.getBoardId())
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        Reply reply = new Reply();
        reply.setBoard(board);
        reply.setUser(user);
        reply.setReplyText(replyDTO.getReplyText());

        replyRepository.save(reply);
        return reply.getReplyId();
    }

    @Override
    public ReplyDTO findById(Long replyId) {
        Reply reply = replyRepository.findById(replyId).orElseThrow(() ->
                new RuntimeException("댓글을 찾을 수 없습니다."));
        return modelMapper.map(reply, ReplyDTO.class);
    }

    @Override
    public void modify(ReplyDTO replyDTO) {
        Reply reply = replyRepository.findById(replyDTO.getReplyId()).orElseThrow(() ->
                new RuntimeException("수정할 댓글을 찾을 수 없습니다."));
        reply.changeText(replyDTO.getReplyText());
        replyRepository.save(reply);
    }

    @Override
    public void remove(Long replyId) {
        replyRepository.deleteById(replyId);
    }

    @Override
    public PageResponseDTO<ReplyDTO> getListOfBoard(Long boardId, PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() <= 0 ? 0 : pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by("replyId").ascending());

        Page<Reply> result = replyRepository.listOfBoard(boardId, pageable);
        List<ReplyDTO> dtoList = result.getContent().stream()
                .map(reply -> modelMapper.map(reply, ReplyDTO.class))
                .collect(Collectors.toList());

        return PageResponseDTO.<ReplyDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int) result.getTotalElements())
                .build();
    }
}
