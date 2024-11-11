package com.dosol.abc.dto.note;

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
    private Long user;

    private LocalDateTime regDate;

    private List<String> fileNames;
}
