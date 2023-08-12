package com.umc.NewTine.domain;

import com.sun.istack.NotNull;

import javax.persistence.*;

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
    private Integer view;


    protected News() {

    }
    public News(Long id, String title, String content, long category_id, long press_id, Integer view) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category_id = category_id;
        this.press_id = press_id;
        this.view = view;
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
