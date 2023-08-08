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
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="categoryId")
    private NewsCategory newsCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="newsId")
    private News news;
}
