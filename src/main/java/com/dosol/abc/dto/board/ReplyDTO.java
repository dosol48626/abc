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

    //private Board board;

    //private User user;

    private Long replyId;

    private Long boardId;

    private Long userId;

    private String username;

    //private String username;

    //private Long userId;

    private String replyText;

    private LocalDateTime regDate;

    private LocalDateTime modDate;

}
