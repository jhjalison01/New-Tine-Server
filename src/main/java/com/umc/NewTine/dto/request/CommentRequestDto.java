package com.umc.NewTine.dto.request;

import com.umc.NewTine.domain.Comment;
import com.umc.NewTine.domain.News;
import com.umc.NewTine.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequestDto {
    private String content;


    @Builder
    public CommentRequestDto(Comment comment) {
        this.content = comment.getContent();
    }

    public Comment toEntity(News news, User user, CommentRequestDto commentRequestDto){
        return Comment.builder()
                .content(commentRequestDto.getContent())
                .news(news)
                .user(user)
                .build();
    }
}
