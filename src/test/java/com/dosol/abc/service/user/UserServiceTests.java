package com.dosol.abc.service.user;

import com.dosol.abc.domain.user.User;
import com.dosol.abc.dto.board.BoardDTO;
import com.dosol.abc.dto.user.UserDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class UserServiceTests {

    @Autowired
    private UserService userService;

    @Test
    public void testRegister() {

        UserDTO userDTO = UserDTO.builder()
                .username("cho")
                .password("1234")
                .build();

        Long userId = userService.register(userDTO);

        log.info("userId" + userId);
    }
}
