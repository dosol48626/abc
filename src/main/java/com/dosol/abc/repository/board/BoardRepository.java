package com.dosol.abc.repository.board;

import com.dosol.abc.domain.board.Board;
import com.dosol.abc.domain.note.Notes;
import com.dosol.abc.repository.board.search.BoardSearch;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardSearch {

    @EntityGraph(attributePaths = {"imageSet"})
    @Query("select b from Board b where b.boardId = :boardId")
    Optional<Board> findByIdWithImages(@Param("boardId") Long boardId);
    // 수정 사항: 반환 타입을 Board로 변경했습니다. Notes가 아니라 Board 객체를 반환해야 합니다.
    // 그리고 @Param 어노테이션을 사용하여 변수 이름을 맞추었습니다.

    @Query("SELECT b FROM Board b WHERE b.boardId = :boardId")
    Optional<Board> findBoardById(@Param("boardId") Long boardId);
    // 수정 사항: 반환 타입을 Optional<Board>로 변경했습니다.
    // findByIdWithImages와의 일관성을 위해 Optional<Board>로 정의하고 있습니다.
}


