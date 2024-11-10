package com.dosol.abc.domain.board;

import com.dosol.abc.domain.note.NotesImage;
import com.dosol.abc.domain.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.ColumnDefault;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "imageSet")
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    // 수정된 부분: CascadeType.PERSIST 추가
    @ManyToOne(cascade = CascadeType.PERSIST)
    private User user;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @ColumnDefault("0")
    private int visitcount;

    public void updateVisitCount() {
        this.visitcount++;
    }

    public void change(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // 수정된 부분: CascadeType.ALL 추가
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @BatchSize(size = 20)
    private Set<BoardImage> imageSet = new HashSet<>();

    public void addImage(String uuid, String fileName) {
        // 중복 방지: uuid가 중복되는 이미지가 있는지 확인
        boolean isDuplicate = imageSet.stream().anyMatch(image -> image.getUuid().equals(uuid));

        if (!isDuplicate) {  // 중복이 아니면 추가
            BoardImage image = BoardImage.builder()
                    .uuid(uuid)
                    .fileName(fileName)
                    .board(this)
                    .ord(imageSet.size())
                    .build();
            imageSet.add(image);
        }
    }

    public void clearImages() {
        imageSet.forEach(boardImage -> boardImage.changeBoard(null));
        this.imageSet.clear();
    }
}
