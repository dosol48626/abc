package com.dosol.abc.repository.user;

import com.dosol.abc.domain.user.User;
import com.dosol.abc.domain.user.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserImageRepository extends JpaRepository<UserImage, Long> {
    List<UserImage> findByUser_UserId(Long userId);

    UserImage findTopByUserOrderByOrdAsc(User user);
}
