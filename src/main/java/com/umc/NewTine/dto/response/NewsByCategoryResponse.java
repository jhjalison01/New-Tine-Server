package com.umc.NewTine.dto.response;


import lombok.*;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

}
