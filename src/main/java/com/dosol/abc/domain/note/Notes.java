package com.dosol.abc.domain.note;

import com.dosol.abc.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notes extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noteId;
    //private Long nno;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String title;

    private String content;

    public void change(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @OneToMany(mappedBy = "notes",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL},
            orphanRemoval = true)
    @Builder.Default
    @BatchSize(size = 20)
    private Set<NotesImage> imageSet = new HashSet<>();

    //notes 저장하면 notes 안에 있는 이미지도 저장, addimage 호출하면 notes 안에 image 추가
    public void addImage(String uuid, String fileName) {
        NotesImage image = NotesImage.builder()
                .uuid(uuid)
                .fileName(fileName)
                .notes(this)
                .ord(imageSet.size())
                .build();
        imageSet.add(image);
    }
    //notes가 하나 들어오고나면 uuid, filename을 받아서 밑에 builder()로 image들을 imageSet에 추가한다.
    public void clearImages(){
        imageSet.forEach(notesImage -> notesImage.changeNotes(null));
        this.imageSet.clear();
    }


}

