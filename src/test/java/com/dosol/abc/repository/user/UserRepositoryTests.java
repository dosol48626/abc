package com.dosol.abc.repository.user;

import com.dosol.abc.domain.user.User;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testInsert() {
        IntStream.rangeClosed(1, 5).forEach(i -> {
            User user = User.builder()
                    .username("qwer" + i)
                    .password("1234" + i)
                    .build();

            userRepository.save(user);
            log.info(user);
        });
    }
}
