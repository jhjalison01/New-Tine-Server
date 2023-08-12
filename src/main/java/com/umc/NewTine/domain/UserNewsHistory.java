package com.umc.NewTine.domain;

import com.sun.istack.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
public class UserNewsHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    @Column
    @NotNull
    private long user_id;

    @Column
    @NotNull
    private long news_id;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date viewed_at;

    public Long getId() {
        return id;
    }

    public long getUser_id() {
        return user_id;
    }

    public long getNews_id() {
        return news_id;
    }

    public Date getViewed_at() {
        return viewed_at;
    }
}



