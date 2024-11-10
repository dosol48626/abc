package com.dosol.abc.dto.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReplyDTO {

    private Long replyId;

    private Long boardId;  // Board 엔티티의 ID를 참조

    private Long userId;   // User 엔티티의 ID를 참조

    private String username; // 댓글 작성자의 사용자 이름

    private String replyText;

    private LocalDateTime regDate;

    private LocalDateTime modDate;
}

