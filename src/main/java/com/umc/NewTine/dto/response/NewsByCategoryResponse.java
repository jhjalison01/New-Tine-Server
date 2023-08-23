package com.umc.NewTine.dto.response;


public class NewsByCategoryResponse {

    private String title;
    private String pressName;
    private String imgUrl;

    public NewsByCategoryResponse(String title, String pressName, String imgUrl) {
        this.title = title;
        this.pressName = pressName;
        this.imgUrl = imgUrl;
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
}
