package com.dosol.abc.domain.wise;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Wise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wiseId;

    private String content;
}