package com.dosol.abc.domain.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true)
    private String username;

    private String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserImage profileImage;

    // 프로필 이미지 설정 메서드
    public void setProfileImage(UserImage profileImage) {
        this.profileImage = profileImage;
        profileImage.changeUser(this);
    }
}

