package com.umc.NewTine.domain;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@Entity
@Getter
@NoArgsConstructor
@Table(name="comment")
public class Comment extends BaseTimeEntity{
    @Id@Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;

    @Column
    private int likes;

    @JoinColumn(name="news_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private News news;

    @JoinColumn(name="user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;


    @Builder
    public Comment(String content, News news, User user) {
        this.content = content;
        this.likes = 0;
        this.news = news;
        this.user = user;
    }


    public void setLike(){
        this.likes = 0;
    }

    public void updateLike(){
        this.likes++;
    }
}
