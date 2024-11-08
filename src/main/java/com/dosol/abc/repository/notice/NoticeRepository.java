package com.dosol.abc.repository.notice;

import com.dosol.abc.domain.notice.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    // 추가적인 쿼리 메서드가 필요하다면 여기에 정의할 수 있습니다.
}
