package com.umc.NewTine.dto.response;

import com.umc.NewTine.domain.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class CommentResponseDto {
    private Long id;
    private Timestamp createdAt;
    private String content;
    private int likes;
    private String nickname;
    private String imageUrl;

    public CommentResponseDto(Comment comment){
        this.createdAt = comment.getCreatedAt();
        this.id = comment.getId();
        this.content = comment.getContent();
        this.likes = comment.getLikes();
        this.nickname = comment.getUser().getNickname();
        this.imageUrl = comment.getUser().getImageUrl();
    }
}
