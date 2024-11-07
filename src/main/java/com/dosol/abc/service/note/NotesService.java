package com.dosol.abc.service.note;

import com.dosol.abc.domain.note.Notes;
import com.dosol.abc.dto.note.NotesDTO;
import com.dosol.abc.dto.note.PageRequestDTO;
import com.dosol.abc.dto.note.PageResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public interface NotesService {

    //노트생성
    Long createNote(NotesDTO notesDTO);

    //노트수정
    void modifyNote(NotesDTO notesDTO);

    //노트제거
    void deleteNote(Long noteId);

    //노트불러오기
    NotesDTO readOne(Long noteId);

    //페이징
    PageResponseDTO<NotesDTO> list(PageRequestDTO pageRequestDTO);

    default Notes dtoToEntity(NotesDTO notesDTO) {
        Notes notes = Notes.builder()
                .noteId(notesDTO.getNoteId())
                .title(notesDTO.getTitle())
                .content(notesDTO.getContent())
                .build();
        //entity 만들고
        if(notesDTO.getFileNames() != null) {
            notesDTO.getFileNames().forEach(fileName -> {
                String[] arr = fileName.split("_");
                notes.addImage(arr[0], arr[1]);
            });
        }
        return notes;
    }
    default NotesDTO entityToDTO(Notes notes) {
        NotesDTO notesDTO = NotesDTO.builder()
                .noteId(notes.getNoteId())
                .title(notes.getTitle())
                .content(notes.getContent())
                .build();

        List<String> fileNames = notes.getImageSet().stream().sorted().map(notesImage ->
                        notesImage.getUuid()+"_"+notesImage.getFileName())
                .collect(Collectors.toList());
        notesDTO.setFileNames(fileNames);
        return notesDTO;
    }
}
