package com.dosol.abc.service.user;

import com.dosol.abc.domain.user.User;
import com.dosol.abc.dto.board.BoardDTO;
import com.dosol.abc.dto.user.UserDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;



@SpringBootTest
@Log4j2
public class UserServiceTests {

    @Autowired
    private UserService userService;
//
//    @Test
//    public void testRegister() {
//
//        UserDTO userDTO = UserDTO.builder()
//                .username("cho")
//                .password("1234")
//                .build();
//
//        Long userId = userService.register(userDTO);
//
//        log.info("userId" + userId);
//    }

    @Test
    public void testLogin() {
        // 올바른 자격 증명을 사용하여 로그인 시도
        Optional<User> result = userService.login("zxcv", "1234");
        if (result.isPresent()) {
            log.info("Login succeeded with correct credentials@@@@@@@@@@@: {}", result.get());
        } else {
            log.info("Login failed with correct credentials#################.");
        }

        // 잘못된 자격 증명을 사용하여 로그인 시도
        Optional<User> wrongResult = userService.login("dddd", "dddd");
        if (wrongResult.isPresent()) {
            log.info("누구세요~??: {}", wrongResult.get());
        } else {
            log.info("누구세요");
        }
    }
}
