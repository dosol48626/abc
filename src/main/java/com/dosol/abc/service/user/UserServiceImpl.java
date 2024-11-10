package com.dosol.abc.service.user;

import com.dosol.abc.domain.user.User;
import com.dosol.abc.domain.user.UserImage;
import com.dosol.abc.dto.user.UserDTO;
import com.dosol.abc.repository.user.UserImageRepository;
import com.dosol.abc.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserImageRepository userImageRepository;

    private static final String UPLOAD_DIR = "C:/upload/";  // 프로필 이미지를 저장할 경로

    @Override
    public Long register(UserDTO userDTO, MultipartFile profileImage) {
        // 1. User 엔티티 생성 및 저장
        User user = User.builder()
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .build();
        userRepository.save(user);

        // 2. 프로필 이미지가 있는 경우 저장
        if (profileImage != null && !profileImage.isEmpty()) {
            try {
                String originalFilename = profileImage.getOriginalFilename();
                String savedFileName = UUID.randomUUID() + "_" + originalFilename;
                File destFile = new File(UPLOAD_DIR + savedFileName);

                // 파일 저장
                profileImage.transferTo(destFile);

                // UserImage 엔티티 생성 후 저장
                UserImage userImage = UserImage.builder()
                        .user(user)
                        .filename(savedFileName)
                        .ord(0)  // 첫 번째 프로필 이미지이므로 ord는 0으로 설정
                        .build();
                userImageRepository.save(userImage);

            } catch (IOException e) {
                log.error("File save error: " + e.getMessage(), e);
            }
        }

        return user.getUserId();
    }

    @Override
    public Optional<User> login(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    @Override
    public User findByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserImage getProfileImageByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return userImageRepository.findTopByUserOrderByOrdAsc(user);
    }
}
