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
public class Board extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    //@ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne(cascade = CascadeType.PERSIST)
    private User user;


//    private String username;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @ColumnDefault("0")
    private int visitcount;

    public void updateVisitCount(){
        this.visitcount++;
    }

    public void change(String title, String content){
        this.title = title;
        this.content = content;
    }


    @OneToMany(mappedBy = "board",
            fetch=FetchType.LAZY,
            cascade = {CascadeType.ALL},
            orphanRemoval = true)
    @Builder.Default
    @BatchSize(size = 20)
    private Set<BoardImage> imageSet=new HashSet<>();

    public void addImage(String uuid, String fileName){
        BoardImage image=BoardImage.builder()
                .uuid(uuid)
                .fileName(fileName)
                .board(this)
                .ord(imageSet.size())
                .build();
        imageSet.add(image);
    }
    public void clearImages(){
        imageSet.forEach(boardImage -> boardImage.changeBoard(null));
        this.imageSet.clear();
    }

}