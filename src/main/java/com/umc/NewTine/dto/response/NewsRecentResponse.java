package com.umc.NewTine.dto.response;

import com.umc.NewTine.domain.News;

public class NewsRecentResponse {

    private long id;
    private String title;
    private long press_id;


    public NewsRecentResponse(long id, String title, long press_id) {
        this.id = id;
        this.title = title;
        this.press_id = press_id;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public long getPress_id() {
        return press_id;
    }


    public NewsRecentResponse(News news) {
        this.id = news.getId();
        this.title = news.getTitle();
        this.press_id = news.getPress_id();

    }
}