package com.dosol.abc.domain.board;

import com.dosol.abc.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"board", "user"})
@Table(name = "Reply", indexes = {
        @Index(name = "idx_reply_board_boardId", columnList = "board_boardId")
})
public class Reply extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long replyId;

    // 수정된 부분: @ManyToOne 설정 검토
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    // 수정된 부분: @ManyToOne 설정 검토
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String replyText;

    public void changeText(String text) {
        this.replyText = text;
    }
}

