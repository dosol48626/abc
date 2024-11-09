package com.dosol.abc.repository.board;

import com.dosol.abc.domain.board.Board;
import com.dosol.abc.domain.note.Notes;
import com.dosol.abc.repository.board.search.BoardSearch;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardSearch {
    @EntityGraph(attributePaths = {"imageSet"})
    @Query("select b from Board b where b.boardId =:boardId")
    Optional<Notes> findByIdWithImages(Long boardId);
}
