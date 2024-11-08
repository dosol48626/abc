package com.dosol.abc.service.notice;

import com.dosol.abc.domain.notice.Notice;
import com.dosol.abc.dto.notice.NoticeDTO;
import com.dosol.abc.repository.notice.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;

    @Autowired
    public NoticeServiceImpl(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    // 공지사항 목록 가져오기
    @Override
    public List<NoticeDTO> getAllNotices() {
        List<Notice> notices = noticeRepository.findAll();
        return notices.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ID로 공지사항 가져오기
    @Override
    public NoticeDTO getNoticeById(Long id) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notice not found with id: " + id));
        return toDTO(notice);
    }

    // 새로운 공지사항 생성
    @Override
    public NoticeDTO createNotice(NoticeDTO noticeDTO) {
        Notice notice = toEntity(noticeDTO);
        Notice savedNotice = noticeRepository.save(notice);
        return toDTO(savedNotice);
    }

    // 공지사항 수정
    @Override
    public NoticeDTO updateNotice(Long id, NoticeDTO noticeDTO) {
        Notice existingNotice = noticeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notice not found with id: " + id));

        existingNotice.setTitle(noticeDTO.getTitle());
        existingNotice.setContent(noticeDTO.getContent());

        Notice updatedNotice = noticeRepository.save(existingNotice);
        return toDTO(updatedNotice);
    }

    // 공지사항 삭제
    @Override
    public void deleteNotice(Long id) {
        if (!noticeRepository.existsById(id)) {
            throw new RuntimeException("Notice not found with id: " + id);
        }
        noticeRepository.deleteById(id);
    }

    // Notice 엔티티를 NoticeDTO로 변환하는 메서드
    private NoticeDTO toDTO(Notice notice) {
        return NoticeDTO.builder()
                .noticeId(notice.getNoticeId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .build();
    }

    // NoticeDTO를 Notice 엔티티로 변환하는 메서드
    private Notice toEntity(NoticeDTO noticeDTO) {
        return Notice.builder()
                .noticeId(noticeDTO.getNoticeId())
                .title(noticeDTO.getTitle())
                .content(noticeDTO.getContent())
                .build();
    }
}
