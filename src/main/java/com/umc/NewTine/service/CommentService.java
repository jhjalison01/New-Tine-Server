package com.umc.NewTine.service;

import com.umc.NewTine.domain.Comment;
import com.umc.NewTine.domain.News;
import com.umc.NewTine.dto.request.CommentRequestDto;
import com.umc.NewTine.dto.response.CommentResponseDto;
import com.umc.NewTine.repository.CommentRepository;
import com.umc.NewTine.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    private final NewsRepository newsRepository;

    public List<CommentResponseDto> getCommnetsByNewsId(Long newsId, String orderBy){
        List<Comment> comments;

        if ("latest".equalsIgnoreCase(orderBy)) {
            comments = commentRepository.findByNewsIdOrderByCreatedAtDesc(newsId);
        } else if ("most-liked".equalsIgnoreCase(orderBy)) {
            comments = commentRepository.findByNewsIdOrderByLikeDesc(newsId);
        } else {
            throw new IllegalArgumentException("Invalid orderBy parameter");
        }


        List<CommentResponseDto> commentDtos = comments.stream()
                .map(comment -> new CommentResponseDto(comment))
                .collect(Collectors.toList());

        return commentDtos;
    }

    public CommentResponseDto addCommentToNews(Long newsId, CommentRequestDto commentRequest) {
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new EntityNotFoundException("News not found"));

        Comment comment = commentRequest.toEntity(news);
        comment.setLike();
        Comment savedComment = commentRepository.save(comment);

        return new CommentResponseDto(savedComment);
    }

    public CommentResponseDto likeComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));

        comment.updateLike();
        Comment updatedComment = commentRepository.save(comment);

        return new CommentResponseDto(updatedComment);
    }
}
