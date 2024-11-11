package com.dosol.abc.repository.note;

import com.dosol.abc.domain.note.Notes;
import com.dosol.abc.repository.note.search.NotesSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NotesRepository extends JpaRepository<Notes, Long>, NotesSearch {
    @EntityGraph(attributePaths = {"imageSet"})
    @Query("select n from Notes n where n.user.userId =:userId")
    Page<Notes> findAllByUserIdWithImages(Long userId, Pageable pageable);
}
