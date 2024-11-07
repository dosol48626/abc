package com.dosol.abc.domain.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserImage implements Comparable<UserImage>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String filename;

    private int ord;

    @Override
    public int compareTo(UserImage other) {
        return this.ord - other.ord;
    }

    public void changeUser(User user) {
        this.user = user;
    }
}