package com.dosol.abc.domain.board;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardImage implements Comparable<BoardImage>{

    @Id
    private String uuid;

    @ManyToOne(fetch = FetchType.LAZY) // (수정 부분) Lazy Fetch를 사용하여 BoardImage가 직접적으로 필요할 때만 로딩
    @JoinColumn(name = "board_id") // (수정 부분) 명시적 컬럼 매핑 추가
    private Board board;

    private String fileName;
    private int ord;

    @Override
    public int compareTo(BoardImage other) {
        return this.ord - other.ord;
    }

    public void changeBoard(Board board) {
        this.board = board;
    }
}
