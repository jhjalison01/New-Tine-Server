package com.umc.NewTine.domain;

import com.sun.istack.NotNull;
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
    private User user;

    @ManyToOne
    private News news;

    @Column
    private LocalDateTime recentViewTime;

    public UserNewsHistory(User user, News news, LocalDateTime recentViewTime) {
        this.user = user;
        this.news = news;
        this.recentViewTime = recentViewTime;
    }

    public void setRecentViewTime(LocalDateTime recentViewTime) {
        this.recentViewTime = recentViewTime;
    }
}



