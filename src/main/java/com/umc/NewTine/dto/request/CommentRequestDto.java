package com.umc.NewTine.dto.request;

import com.umc.NewTine.domain.Comment;
import com.umc.NewTine.domain.News;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
@AllArgsConstructor
public class CommentRequestDto {
    private String content;

    public Comment toEntity(News news){
        return Comment.builder()
                .content(content)
                .news(news)
                .build();
    }
}
