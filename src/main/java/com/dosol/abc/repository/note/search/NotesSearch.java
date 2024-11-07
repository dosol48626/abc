package com.dosol.abc.repository.note.search;

import com.dosol.abc.domain.note.Notes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotesSearch {
    Page<Notes> searchAll(String[] types, String keyword, Pageable pageable);
}
