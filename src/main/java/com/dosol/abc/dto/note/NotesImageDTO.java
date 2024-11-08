package com.dosol.abc.dto.note;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotesImageDTO {
    private String uuid;
    private String fileName;
    private int ord;
}
