package com.dosol.abc.dto.wise;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WiseDTO {

    private Long wiseId;

    private String content;

    private int randomNumber;  // 랜덤 숫자 추가
}
