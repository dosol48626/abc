package com.dosol.abc.domain.todo;

import com.dosol.abc.domain.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long todoId;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String memberId;

    private String title;

    private String description;

    private Boolean complete;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime dueDate;

    public void change(@NotEmpty String title, @NotEmpty String description) {
        this.title = title;
        this.description = description;
    }
    @PrePersist
    public void prePersist(){
        this.complete = Boolean.FALSE;
    }
}
