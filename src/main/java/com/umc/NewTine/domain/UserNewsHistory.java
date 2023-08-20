package com.umc.NewTine.domain;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
public class UserNewsHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "news_id")
    private News news;

    @Column
    private LocalDateTime recentViewTime;

    @Column
    private LocalDateTime recentViewExitTime;

    public UserNewsHistory(User user, News news, LocalDateTime recentViewTime, LocalDateTime recentViewExitTime) {
        this.user = user;
        this.news = news;
        this.recentViewTime = recentViewTime;
        this.recentViewExitTime = recentViewExitTime;
    }

    public void setRecentViewTime(LocalDateTime recentViewTime) {
        this.recentViewTime = recentViewTime;
    }
}
