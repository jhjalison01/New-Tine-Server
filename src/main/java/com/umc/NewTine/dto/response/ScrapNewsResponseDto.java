package com.umc.NewTine.dto.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ScrapNewsResponseDto {
    private String title;
    private String createdAt;
    private String name;

    public ScrapNewsResponseDto(String title, String createdAt, String name){
        this.title=title;
        this.createdAt=createdAt;
        this.name=name;
    }
}