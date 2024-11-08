package com.dosol.abc.service.notice;

import com.dosol.abc.dto.notice.NoticeDTO;
import java.util.List;

public interface NoticeService {

    // 모든 공지사항 목록을 가져오기
    List<NoticeDTO> getAllNotices();

    // ID로 특정 공지사항 가져오기
    NoticeDTO getNoticeById(Long id);

    // 새로운 공지사항 생성
    NoticeDTO createNotice(NoticeDTO noticeDTO);

    // 기존 공지사항 수정
    NoticeDTO updateNotice(Long id, NoticeDTO noticeDTO);

    // 공지사항 삭제
    void deleteNotice(Long id);
}
