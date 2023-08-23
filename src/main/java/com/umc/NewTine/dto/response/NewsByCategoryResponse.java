package com.umc.NewTine.dto.response;


import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class NewsByCategoryResponse {

    private String title;
    private String pressName;
    private String imgUrl;
    private String content;
    private Long newsId;
    private String pressImg;


    public NewsByCategoryResponse(String title, String pressName, String imgUrl, String content, Long newsId, String pressImg) {
        this.title = title;
        this.pressName = pressName;
        this.imgUrl = imgUrl;
        this.content = content;
        this.newsId = newsId;
        this.pressImg = pressImg;
    }

    public String getTitle() {
        return title;
    }

    public String getPressName() {
        return pressName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getContent() {
        return content;
    }

    public Long getNewsId() {
        return newsId;
    }
}
