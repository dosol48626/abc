package com.dosol.abc.dto.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyPageDTO {
    private Long userId;
    private String username;
    private String profileImage;
    private List<BoardDTO> boards;
}
