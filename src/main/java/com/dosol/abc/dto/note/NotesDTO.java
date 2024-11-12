package com.dosol.abc.dto.note;

import com.dosol.abc.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotesDTO {
    private Long noteId;
    private String title;
    private String content;

    private Long userId;

    private String username;

    private LocalDateTime regDate;

    private List<String> fileNames;
}
