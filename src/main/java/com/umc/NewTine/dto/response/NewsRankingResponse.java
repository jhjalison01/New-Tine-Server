package com.umc.NewTine.dto.response;

import com.umc.NewTine.domain.News;

public class NewsRankingResponse {

    private long id;
    private String title;

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public NewsRankingResponse(News news) {
        this.id = news.getId();
        this.title = news.getTitle();
    }

    public NewsRankingResponse(long id, String title) {
        this.id = id;
        this.title = title;
    }
}