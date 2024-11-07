package com.dosol.abc.repository.note;

import com.dosol.abc.domain.note.Notes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotesRepository extends JpaRepository<Notes, Long> {
}
