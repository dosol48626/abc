package com.dosol.abc.domain.board;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "board")
@Table(name = "Reply", indexes = {
        @Index(name = "idx_reply_board_boardId", columnList = "board_boardId")
})
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    private String replyText;

    public void changeText(String text){
        this.replyText = text;
    }
}
