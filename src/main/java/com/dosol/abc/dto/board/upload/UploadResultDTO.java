package com.dosol.abc.dto.board.upload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadResultDTO {
    private String uuid;
    private String fileName;
    private boolean image;

    public String getLink() {
        return image ? "s_" + uuid + "_" + fileName : uuid + "_" + fileName;
    }
}