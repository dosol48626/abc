package com.dosol.abc.service.user;

import com.dosol.abc.domain.user.User;
import com.dosol.abc.dto.board.BoardDTO;
import com.dosol.abc.dto.user.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {


    Long register(UserDTO userDTO);

    Optional<User> login(String username, String password);

    User findByUserName(String username);
}
