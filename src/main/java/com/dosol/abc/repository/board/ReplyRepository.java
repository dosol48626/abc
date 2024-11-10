package com.dosol.abc.repository.board;

import com.dosol.abc.domain.board.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Query("select r from Reply r where r.board.boardId = :boardId")
    Page<Reply> listOfBoard(@Param("boardId") Long boardId, Pageable pageable);
    // 수정 사항: @Param("boardId")를 추가하여 JPQL 변수 이름과 메서드 파라미터 이름을 맞췄습니다.
}
