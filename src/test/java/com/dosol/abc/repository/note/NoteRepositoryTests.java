package com.dosol.abc.repository.note;

import com.dosol.abc.domain.note.Notes;
import com.dosol.abc.domain.user.User;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;
import static org.assertj.core.api.Assertions.assertThat;


import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class NoteRepositoryTests {

    @Autowired
    private NotesRepository notesRepository;

    @Test
    public void testInsert() {
        // 객체 생성
        Notes notes = new Notes();
        notes.setTitle("first title1");
        notes.setContent("content1");
        // 저장
        notesRepository.save(notes);
    }

    @Test
    public void testInsert2() {
        IntStream.rangeClosed(1, 20).forEach(i -> {
            Notes notes = Notes.builder()
                    .user(User.builder().userId(1L).build())
                    .title("third Title" + i)
                    .content("thoo Content" + i)
                    .build();

            Notes result = notesRepository.save(notes);
            log.info("noteeeeeeeeeeeeeeeeeeeeeeee" + result.getNoteId());
        });
    }

    @Test
    public void testGetList() {
        List<Notes> notesList = notesRepository.findAll();
        log.info("=== All Notes ===");
        notesList.forEach(note -> log.info(note));
        log.info("=================");
    }

    @Test
    public void updateTest() {
        Long noteId = 2L; // 수정할 노트의 ID
        Notes notes = notesRepository.findById(noteId)
                .orElseThrow(() -> new RuntimeException("Note not found"));

        // 노트의 제목과 내용을 업데이트
        notes.setTitle("Updated Title22");
        notes.setContent("Updated Conten22t");

        notesRepository.save(notes); // 변경 내용을 저장

        // 업데이트 결과 확인
        Notes updatedNotes = notesRepository.findById(noteId).orElseThrow();
        log.info("Updated Note: " + updatedNotes);
    }

    @Test
    public void deleteBoardTest() {
        Long noteId = 1L; // 삭제할 노트의 ID

        notesRepository.deleteById(noteId);

        // 삭제 후 확인
        boolean exists = notesRepository.existsById(noteId);
        log.info("Deleted Note Exists: " + exists); // false가 출력되어야 정상 삭제된 것
    }

    @Test
    public void testPaging() {
        // 1 page order by note_id desc
        Pageable pageable = PageRequest.of(0, 10, Sort.by("noteId").descending());

        Page<Notes> result = notesRepository.findAll(pageable);

        log.info("total count : " + result.getTotalElements());
        log.info("total pages : " + result.getTotalPages());
        log.info("page number : " + result.getNumber());
        log.info("page size : " + result.getSize());

        List<Notes> notesList = result.getContent();

        // 개별 Notes 객체를 로그로 출력
        notesList.forEach(notes -> log.info(notes.toString())
        );
        // 또는 log.info(todo)도 가능
    }

    @Test
    @Transactional
    public void testSearchAll() {
        // 검색 조건 설정
        String[] types = {"t", "c"};  // 예: "t"는 title, "c"는 content
        String keyword = null;  // 검색할 키워드

        // 페이징 및 정렬 설정
        Pageable pageable = PageRequest.of(0, 10, Sort.by("noteId").descending());

        // userId를 임의로 설정 (테스트용 userId) 임의 설정
        Long userId = 3L;

        // Notes 엔티티에 대한 검색 수행
        Page<Notes> result = notesRepository.searchAll(types, keyword, pageable, userId);

        // 테스트 결과 확인
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isNotEmpty();

        // 각 노트가 해당 userId를 가지고 있는지 확인
        result.getContent().forEach(note -> {
            assertThat(note.getUser().getUserId()).isEqualTo(userId);
            System.out.println(note.toString());
        });
    }
}



