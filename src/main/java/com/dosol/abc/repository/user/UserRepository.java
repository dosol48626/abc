package com.dosol.abc.repository.user;


import com.dosol.abc.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username); //글 쓸때 이름 가져올려고 하는 메서드

    Optional<User> findByUsernameAndPassword(String username, String password); //로그인할때 맞는지 확인할려고 가져오는 메서드
}
