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

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    //private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    //private String username;

    //private Long userId;

    private String replyText;

    public void changeText(String text){
        this.replyText = text;
    }
}
