package com.dosol.abc.service.note;

import com.dosol.abc.domain.note.Notes;
import com.dosol.abc.domain.user.User;
import com.dosol.abc.dto.note.NotesDTO;
import com.dosol.abc.dto.note.PageRequestDTO;
import com.dosol.abc.dto.note.PageResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.stream.Collectors;

public interface NotesService {

    //노트생성
    Long createNote(NotesDTO notesDTO, User user);

    //노트수정
    void modifyNote(NotesDTO notesDTO);

    //노트제거
    void deleteNote(Long noteId);

    //노트불러오기
    NotesDTO readOne(Long noteId);

    //페이징
    PageResponseDTO<NotesDTO> list(PageRequestDTO pageRequestDTO, HttpSession session);

    default Notes dtoToEntity(NotesDTO notesDTO) {

        // User 설정
        User user = new User();
        if (notesDTO.getUserId() != null) {

            user.setUserId(notesDTO.getUserId()); // userId로 User 객체 생성

        }

        Notes notes = Notes.builder()
                .noteId(notesDTO.getNoteId())
                .title(notesDTO.getTitle())
                .user(user)
                .content(notesDTO.getContent())
                .build();



        if (notesDTO.getFileNames() != null) {
            notesDTO.getFileNames().forEach(fileName -> {
                String[] arr = fileName.split("_");
                notes.addImage(arr[0], arr[1]); // UUID와 파일 이름을 각각 분리하여 추가
            });
        }
        return notes;
    }

    default NotesDTO entityToDTO(Notes notes) {
        NotesDTO notesDTO = NotesDTO.builder()
                .noteId(notes.getNoteId())
                .title(notes.getTitle())
                .content(notes.getContent())
                //.username(notes.getUser().getUsername())
                .build();

        List<String> fileNames = notes.getImageSet().stream()
                .sorted() // 필요 시 정렬, 생략 가능
                .map(notesImage -> notesImage.getUuid() + "_" + notesImage.getFileName())
                .collect(Collectors.toList());
        notesDTO.setFileNames(fileNames);
        return notesDTO;
    }
}
