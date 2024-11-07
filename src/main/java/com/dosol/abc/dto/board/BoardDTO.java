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
public class BoardDTO {

    private Long boardId;

    private String title;

    private String content;

    private int visitCount;

    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
