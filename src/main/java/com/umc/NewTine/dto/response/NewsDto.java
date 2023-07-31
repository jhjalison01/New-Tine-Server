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

    private Long news_id;
    private String title;
    private String image;
    private String summary;
    private Press press;
    private Timestamp createdDate;

    public static NewsDto from(News news) {
        return NewsDto.builder()
                .news_id(news.getId())
                .title(news.getTitle())
                .image(news.getImage())
                .summary(news.getSummary())
                .press(news.getPress())
                .createdDate(news.getCreatedAt())
                .build();
    }


}
