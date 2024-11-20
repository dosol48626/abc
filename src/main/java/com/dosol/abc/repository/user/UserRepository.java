package com.dosol.abc.repository.user;


import com.dosol.abc.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    Optional<User> findByUsernameAndPassword(String username, String password);
}
