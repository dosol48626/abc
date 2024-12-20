package com.dosol.abc.repository.note.search;

import com.dosol.abc.domain.note.Notes;
import com.dosol.abc.domain.note.QNotes;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class NotesSearchImpl extends QuerydslRepositorySupport implements NotesSearch {

    public NotesSearchImpl() {
        super(Notes.class);
    }

    @Override
    public Page<Notes> searchAll(String[] types, String keyword, Pageable pageable, Long userId) {
        QNotes notes = QNotes.notes;

        JPQLQuery<Notes> query = from(notes);

        // userId가 null이 아니면 사용자별로 필터링
        if (userId != null) {
            query.where(notes.user.userId.eq(userId));  // 사용자 ID로 필터링
        }

        if((types != null && types.length > 0) && (keyword != null)) {
            BooleanBuilder builder = new BooleanBuilder();
            for(String type : types) {
                switch (type) {
                    case "t":
                        builder.or(notes.title.contains(keyword));
                        break;
                    case "c":
                        builder.or(notes.content.contains(keyword));
                        break;
                }
            }
            query.where(builder);
        }
        query.where(notes.noteId.gt(0L));

        this.getQuerydsl().applyPagination(pageable, query);

        List<Notes> list = query.fetch();
        long total = query.fetchCount();
        return new PageImpl<>(list, pageable, total);
    }
}
