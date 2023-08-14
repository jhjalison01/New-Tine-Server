package com.umc.NewTine.dto.response;

import lombok.*;

import java.util.List;


@Data
@ToString
@Getter
@Setter
@Builder
public class SingleNewsResponseDto {
    private String title;
    private String content;
    private String createdAt;
    private String pressName;
    private String pressImage;
    private int pressSubscriber;
    private boolean isSubscribed;
    private boolean isScrapped;
    private List<String> category;


    public SingleNewsResponseDto(String title, String content, String createdAt, String pressName,
                                 String pressImage, int pressSubscriber, boolean isSubscribed, boolean isScrapped, List<String> category) {
        this.title=title;
        this.content=content;
        this.createdAt=createdAt;
        this.pressName=pressName;
        this.pressImage=pressImage;
        this.pressSubscriber=pressSubscriber;
        this.isSubscribed=isSubscribed;
        this.isScrapped=isScrapped;
        this.category=category;
    }
}
