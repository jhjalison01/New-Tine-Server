package com.umc.NewTine.domain;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "newsAndCategory")
public class NewsAndCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id")
    private NewsCategory newsCategory;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="news_id")
    private News news;
}
