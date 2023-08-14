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
    private long category_id;

    @NotNull
    @Column
    private long press_id;

    @NotNull
    @Column
    private int views;

    @OneToMany(mappedBy = "news")
    private List<UserNewsHistory> userNewsHistories;
    public News(Long id, String title, String content, long category_id, long press_id) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category_id = category_id;
        this.press_id = press_id;
        this.views = 0;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public long getPress_id() {
        return press_id;
    }
}
