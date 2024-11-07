package com.dosol.abc.dto.todo;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoDTO {
    private Long todoId;
    private String memberId;

    @NotEmpty
    private String title;

    @NotEmpty
    private String description;

    private Boolean complete;
    private LocalDate dueDate;
}