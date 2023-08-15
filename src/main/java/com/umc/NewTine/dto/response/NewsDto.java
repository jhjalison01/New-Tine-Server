package com.umc.NewTine.dto.response;

import com.umc.NewTine.domain.News;
import com.umc.NewTine.domain.Press;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsDto {

    private String title;
    private String image;
    private String summary;
    private String press_name;
    private Timestamp createdDate;

    public static NewsDto from(News news, Press press) {
        return NewsDto.builder()
                .title(news.getTitle())
                .image(news.getImage())
                .summary(news.getSummary())
                .press_name(press.getName())
                .createdDate(news.getCreatedAt())
                .build();
    }


}
