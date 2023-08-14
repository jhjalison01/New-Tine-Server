package com.umc.NewTine.dto.response;

import com.umc.NewTine.domain.News;

public class NewsRecommendResponse {

    private long id;
    private String title;
    private long pressId;

    public NewsRecommendResponse(News news) {
        this.id = news.getId();
        this.title = news.getTitle();
        this.pressId = news.getPressId();
    }
}
