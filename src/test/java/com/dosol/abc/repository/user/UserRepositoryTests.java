package com.dosol.abc.repository.user;

import com.dosol.abc.domain.user.User;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testInsert() {

            User user = User.builder()
                    .username("qwer")
                    .password("1234")
                    .build();

            userRepository.save(user);
            log.info(user);
    }

//    @Test
//    public void findByUsernameAndPasswordTest() {
////        User testUser = new User();
////        testUser.setUsername("testUser");
////        testUser.setPassword("testPassword");
////        userRepository.save(testUser); // 테스트 데이터베이스에 사용자 저장
//
//        // 2. 올바른 사용자명과 비밀번호로 사용자 조회 테스트
//        Optional<User> foundUser = userRepository.findByUsernameAndPassword("zxcv", "1234");
//        log.info("회원가입이 되어 있는 사람입니다!@#!@#!@#!@#"+foundUser);
//        assertTrue(foundUser.isPresent(), "회원가입이 되어 있는 사람입니다!@#!@#!@#!@#");
//
//        // 3. 조회된 사용자 정보가 정확한지 확인
//        assertEquals("zxcv", foundUser.get().getUsername(), "Username should match");
//        assertEquals("1234", foundUser.get().getPassword(), "Password should match");
//
//        // 4. 잘못된 사용자명과 비밀번호로 사용자 조회 테스트
//        Optional<User> notFoundUser = userRepository.findByUsernameAndPassword("wrongUser", "wrongPassword");
//        log.info("누구셈??@@@@@@@@@@@@@@"+notFoundUser);
//        assertFalse(notFoundUser.isPresent(), "누구셈??@@@@@@@@@@@@@@");
//    }
}
