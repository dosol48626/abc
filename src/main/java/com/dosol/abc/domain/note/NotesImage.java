package com.dosol.abc.domain.note;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotesImage implements Comparable<NotesImage> {

    @Id
    private String uuid;

    private String fileName;
    
    private int ord;

    @ManyToOne
    private Notes notes;

    @Override
    public int compareTo(NotesImage other) {
        return ord - other.ord;
    }

    public void changeNotes(Notes notes) {
        this.notes = notes;
    }
}
