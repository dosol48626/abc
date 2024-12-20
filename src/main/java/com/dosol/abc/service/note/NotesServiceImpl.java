package com.dosol.abc.service.note;

import com.dosol.abc.domain.note.Notes;
import com.dosol.abc.domain.user.User;
import com.dosol.abc.dto.note.NotesDTO;
import com.dosol.abc.dto.note.PageRequestDTO;
import com.dosol.abc.dto.note.PageResponseDTO;
import com.dosol.abc.repository.note.NotesRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class NotesServiceImpl implements NotesService {

    private final NotesRepository notesRepository;
    private final ModelMapper modelMapper;
    @Override
    public Long createNote(NotesDTO notesDTO, User user) {
        Notes notes = dtoToEntity(notesDTO);
        notes.setUser(user);
        Long noteId = notesRepository.save(notes).getNoteId();
        return noteId;
    }

    @Override
    public void modifyNote(NotesDTO notesDTO) {
        Optional<Notes> result = notesRepository.findById(notesDTO.getNoteId());
        Notes notes = result.orElseThrow();
        // 노트의 속성을 업데이트
        //notes.setTitle(notesDTO.getTitle());
        //notes.setContent(notesDTO.getContent());
        notes.change(notesDTO.getTitle(), notesDTO.getContent());
        //수정시 이미지파일도 변경되게?
        // 엔티티 저장
        notes.clearImages();
        if(notesDTO.getFileNames() != null) {
            for (String fileName : notesDTO.getFileNames()) {
                String[] arr = fileName.split("_");
                notes.addImage(arr[0], arr[1]);
            }
        }
        notesRepository.save(notes);
    }

    @Override
    public void deleteNote(Long noteId) {
        notesRepository.deleteById(noteId);
    }

    @Override
    public NotesDTO readOne(Long noteId) {
        Optional<Notes> result = notesRepository.findByIdWithImages(noteId);
        Notes notes = result.orElseThrow();
        NotesDTO notesDTO = entityToDTO(notes);
        //NotesDTO notesDTO = modelMapper.map(result.orElseThrow(), NotesDTO.class);
        return notesDTO;
    }

    @Override
    public PageResponseDTO<NotesDTO> list(PageRequestDTO pageRequestDTO, HttpSession session) {
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("noteId");

        User user = (User) session.getAttribute("user");
        Long userId = (user != null) ? user.getUserId() : null;

        /*if (user != null) {
            // user가 있으면 사용자별 필터링된 노트를 가져옴
            Long userId = user.getUserId();
            result = notesRepository.findAllByUserIdWithImages(userId, pageable);
        } else {
            // user가 없으면 모든 노트를 검색
            result = notesRepository.searchAll(types, keyword, pageable, user.getUserId());
        }*/

        Page<Notes> result = notesRepository.searchAll(types, keyword, pageable, userId);

        List<NotesDTO> dtoList = result.getContent().stream()
                .map(notes -> {
                    NotesDTO notesDTO = modelMapper.map(notes, NotesDTO.class);

                    // fileNames가 null이거나 비어 있으면 기본값 설정
                    if (notesDTO.getFileNames() == null || notesDTO.getFileNames().isEmpty()) {
                        notesDTO.setFileNames(List.of("default-thumbnail.png")); // 기본 썸네일 설정
                    }

                    return notesDTO;
                })
                .collect(Collectors.toList());

        // 페이지 응답 DTO 반환
        return PageResponseDTO.<NotesDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int) result.getTotalElements())
                .build();
    }
}
