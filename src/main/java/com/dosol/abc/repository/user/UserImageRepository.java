package com.dosol.abc.repository.user;

import com.dosol.abc.domain.user.User;
import com.dosol.abc.domain.user.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserImageRepository extends JpaRepository<UserImage, Long> {
    // 특정 유저에 해당하는 모든 이미지를 조회합니다.
    List<UserImage> findByUser_UserId(Long userId);

    // 유저에 대한 첫 번째 프로필 이미지를 가져오기 위한 메서드
    UserImage findTopByUserOrderByOrdAsc(User user);
}
