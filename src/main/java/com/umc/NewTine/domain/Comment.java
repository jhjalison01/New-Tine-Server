package com.umc.NewTine.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="comment")
public class Comment extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;

    @Column
    private Long like;

    @JoinColumn(name="news_id")
    @ManyToOne
    private News news;

    @Builder
    public Comment(String content, Long like, News news) {
        this.content = content;
        this.like = like;
        this.news = news;
    }
}
