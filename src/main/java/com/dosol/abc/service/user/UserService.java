package com.dosol.abc.service.user;

import com.dosol.abc.domain.user.User;
import com.dosol.abc.domain.user.UserImage;
import com.dosol.abc.dto.user.UserDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
//
public interface UserService {
    Long register(UserDTO userDTO, MultipartFile profileImage);  // 회원가입 시 프로필 이미지도 함께 등록

    Optional<User> login(String username, String password);

    User findByUserName(String username);

    // 유저의 프로필 이미지를 가져오는 메서드
    UserImage getProfileImageByUsername(String username);
}
