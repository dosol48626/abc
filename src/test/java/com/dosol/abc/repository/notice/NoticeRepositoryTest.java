package com.dosol.abc.repository.notice;

import com.dosol.abc.domain.notice.Notice;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class NoticeRepositoryTest {

    @Autowired
    private NoticeRepository noticeRepository;

    // Notice 데이터를 100개 삽입하는 테스트
    @Test
    public void testInsert() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Notice notice = Notice.builder()
                    .title("Title " + i)
                    .content("Content " + i)
                    .build();

            Notice result = noticeRepository.save(notice);
            log.info("NoticeId: " + result.getNoticeId());
        });
    }

    // 특정 Notice를 ID로 조회하는 테스트
    @Test
    public void testSelect() {
        Long noticeId = 1L;  // 테스트할 공지사항 ID

        Optional<Notice> result = noticeRepository.findById(noticeId);

        Notice notice = result.orElseThrow(() -> new RuntimeException("Notice not found"));

        log.info(notice);
    }

    // Notice 데이터를 삭제하는 테스트
    @Test
    public void testDelete() {
        Long noticeId = 1L;  // 삭제할 공지사항 ID
        noticeRepository.deleteById(noticeId);
        log.info("Notice with ID " + noticeId + " deleted.");

        // 삭제 확인
        boolean exists = noticeRepository.existsById(noticeId);
        log.info("Exists after deletion: " + exists);
    }

    // Notice 데이터를 수정하는 테스트
    @Test
    public void testUpdate() {
        Long noticeId = 1L;  // 수정할 공지사항 ID

        Optional<Notice> result = noticeRepository.findById(noticeId);

        if (result.isPresent()) {
            Notice notice = result.get();
            notice.setTitle("Updated Title");
            notice.setContent("Updated Content");

            Notice updatedNotice = noticeRepository.save(notice);
            log.info("Updated Notice: " + updatedNotice);
        } else {
            log.warn("Notice with ID " + noticeId + " not found for update.");
        }
    }
}
