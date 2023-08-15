package com.umc.NewTine.dto.response;

import com.umc.NewTine.domain.News;

public class NewsRecentResponse {

    private long id;
    private String title;
    private long pressId;

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public long getPressId() {
        return pressId;
    }


    public NewsRecentResponse(News news) {
        this.id = news.getId();
        this.title = news.getTitle();
        this.pressId = news.getPress().getId();

    }

    public NewsRecentResponse(long id, String title, long pressId) {
        this.id = id;
        this.title = title;
        this.pressId = pressId;
    }
}