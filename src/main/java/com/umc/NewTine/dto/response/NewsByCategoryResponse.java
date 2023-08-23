package com.umc.NewTine.dto.response;


import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class NewsByCategoryResponse {

    private String title;
    private String content;
    private String createdAt;
    private String pressName;
    private String pressImage;
    private String imgUrl;
    private int pressSubscriber;
    private boolean subscribed;
    private boolean scrapped;
    private List<String> category;
    private List<String> successMission;


    public NewsByCategoryResponse(String title, String content, String createdAt, String pressName,
                String pressImage, int pressSubscriber, boolean subscribed, boolean scrapped, List<String> category,
                List<String> successMission, String imgUrl) {
        this.title=title;
        this.content=content;
        this.createdAt=createdAt;
        this.pressName=pressName;
        this.pressImage=pressImage;
        this.pressSubscriber=pressSubscriber;
        this.subscribed=subscribed;
        this.scrapped=scrapped;
        this.category=category;
        this.successMission = successMission;
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getPressName() {
        return pressName;
    }

    public String getPressImage() {
        return pressImage;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public int getPressSubscriber() {
        return pressSubscriber;
    }

    public boolean isSubscribed() {
        return subscribed;
    }

    public boolean isScrapped() {
        return scrapped;
    }

    public List<String> getCategory() {
        return category;
    }

    public List<String> getSuccessMission() {
        return successMission;
    }
}
