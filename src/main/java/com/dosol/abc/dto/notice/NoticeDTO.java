package com.dosol.abc.dto.notice;

import com.dosol.abc.domain.notice.Notice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoticeDTO {

    private Long noticeId;  // 필드 이름을 Notice 엔티티와 일치시킴
    private String title;
    private String content;

    // NoticeDTO에서 Notice 엔티티로 변환하는 메서드
    public Notice toEntity() {
        return Notice.builder()
                .noticeId(this.noticeId)
                .title(this.title)
                .content(this.content)
                .build();
    }

    // Notice 엔티티에서 NoticeDTO로 변환하는 메서드
    public static NoticeDTO fromEntity(Notice notice) {
        return NoticeDTO.builder()
                .noticeId(notice.getNoticeId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .build();
    }
}
