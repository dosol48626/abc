package com.dosol.abc.service.user;

import com.dosol.abc.domain.user.User;
import com.dosol.abc.dto.user.UserDTO;
import com.dosol.abc.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public Long register(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        Long userId = userRepository.save(user).getUserId();
        return userId;
    }
}
