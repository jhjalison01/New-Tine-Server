package com.umc.NewTine.domain;

import com.sun.istack.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class UserNewsHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    @NotNull
    @ManyToOne
    private User user;

    @NotNull
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



