package com.umc.NewTine.domain;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "news_and_category")
public class NewsAndCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id")
    private NewsCategory newsCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="news_id")
    private News news;
}
