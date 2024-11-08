package com.dosol.abc.repository.board.search;

import com.dosol.abc.domain.board.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardSearch {
    Page<Board> searchAll(String[] types, String keyword, Pageable pageable);
}
