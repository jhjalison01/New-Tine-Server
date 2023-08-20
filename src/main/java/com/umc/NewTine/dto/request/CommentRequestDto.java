package com.umc.NewTine.dto.request;

import com.umc.NewTine.domain.Comment;
import com.umc.NewTine.domain.News;
import com.umc.NewTine.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequestDto {
    private String content;

    public Comment toEntity(News news, User user){
        return Comment.builder()
                .content(content)
                .news(news)
                .user(user)
                .build();
    }
}
