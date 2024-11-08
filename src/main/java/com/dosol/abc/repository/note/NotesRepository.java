package com.dosol.abc.repository.note;

import com.dosol.abc.domain.note.Notes;
import com.dosol.abc.repository.note.search.NotesSearch;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface NotesRepository extends JpaRepository<Notes, Long>, NotesSearch {
    @EntityGraph(attributePaths = {"imageSet"})
    @Query("select n from Notes n where n.noteId =:noteId")
    Optional<Notes> findByIdWithImages(Long noteId);
}
