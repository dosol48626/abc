package com.dosol.abc.service.user;

import com.dosol.abc.domain.user.User;
import com.dosol.abc.dto.user.UserDTO;
import com.dosol.abc.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public Optional<User> login(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    @Override
    public User findByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    public User findUserName(String username) {
        return userRepository.findByUsername(username);
    }
}
