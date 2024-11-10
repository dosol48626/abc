package com.dosol.abc.repository.user;


import com.dosol.abc.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    // 이 메서드는 username으로 User 객체를 단일 조회할 때 사용합니다.

    Optional<User> findByUsernameAndPassword(String username, String password);
    // 로그인 시 username과 password로 User를 찾기 위한 메서드입니다.
}
