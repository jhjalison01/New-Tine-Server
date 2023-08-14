package com.umc.NewTine.domain;

import com.sun.istack.NotNull;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Entity
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    @NotNull
    @Column
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @NotNull
    @Column
    private long categoryId;

    @NotNull
    @Column
    private long pressId;

    @NotNull
    @Column
    private int views;

    @OneToMany(mappedBy = "news")
    private List<UserNewsHistory> userNewsHistories;

    public News(Long id, String title, String content, long categoryId, long pressId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.categoryId = categoryId;
        this.pressId = pressId;
        this.views = 0;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getViews() {
        return views;
    }

    public long getPressId() {
        return pressId;
    }

    public void setViews(int views) {
        this.views = views;
    }
}
