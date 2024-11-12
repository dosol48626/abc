package com.dosol.abc.service.notice;

import com.dosol.abc.dto.notice.NoticeDTO;
import com.dosol.abc.domain.notice.Notice;
import com.dosol.abc.repository.notice.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;

    @Autowired
    public NoticeServiceImpl(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    @Override
    public Page<NoticeDTO> getNotices(Pageable pageable) {
        return noticeRepository.findAll(pageable)
                .map(NoticeDTO::fromEntity);  // Notice 엔티티를 NoticeDTO로 변환
    }

    @Override
    public NoticeDTO getNoticeById(Long noticeId) {
        return noticeRepository.findById(noticeId)
                .map(NoticeDTO::fromEntity)
                .orElseThrow(() -> new IllegalArgumentException("해당 공지사항이 존재하지 않습니다."));
    }

    @Override
    public NoticeDTO createNotice(NoticeDTO noticeDTO) {
        Notice notice = noticeDTO.toEntity();  // NoticeDTO를 Notice 엔티티로 변환
        Notice savedNotice = noticeRepository.save(notice);
        return NoticeDTO.fromEntity(savedNotice);
    }

    @Override
    public NoticeDTO updateNotice(Long noticeId, NoticeDTO noticeDTO) {
        return noticeRepository.findById(noticeId)
                .map(existingNotice -> {
                    existingNotice.setTitle(noticeDTO.getTitle());
                    existingNotice.setContent(noticeDTO.getContent());
                    Notice updatedNotice = noticeRepository.save(existingNotice);
                    return NoticeDTO.fromEntity(updatedNotice);
                })
                .orElseThrow(() -> new IllegalArgumentException("해당 공지사항이 존재하지 않습니다."));
    }

    @Override
    public void deleteNotice(Long noticeId) {
        noticeRepository.deleteById(noticeId);
    }
}
