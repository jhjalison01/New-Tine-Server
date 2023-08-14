package com.umc.NewTine.domain;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@Entity
public class NewsAndCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    @ManyToOne
    @JoinColumn(name="category_id")
    private NewsCategory newsCategory;

    @ManyToOne
    @JoinColumn(name="news_id")
    private News news;
}