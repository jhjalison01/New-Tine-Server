package com.umc.NewTine.dto.response;

import com.umc.NewTine.domain.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentResponseDto {
    private Long id;
    private String content;
    private int likes;

    private Long user_id;

    public CommentResponseDto(Comment comment){
        this.id = comment.getId();
        this.content = comment.getContent();
        this.likes = comment.getLikes();
        this.user_id = comment.getUser().getId();
    }
}
