package com.umc.NewTine.domain;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@Entity
public class NewsCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    @Column
    private String name;

}
