package com.umc.NewTine.dto.response;

import com.umc.NewTine.domain.News;

public class NewsSearchByWordResponse {

    private long id;
    private String title;
//    private long press_id;

    public NewsSearchByWordResponse(News news) {
        this.id = news.getId();
        this.title = news.getTitle();
    }

    public NewsSearchByWordResponse(long id, String title) {
        this.id = id;
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
