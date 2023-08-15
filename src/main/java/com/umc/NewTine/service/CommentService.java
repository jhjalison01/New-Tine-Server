package com.umc.NewTine.service;

import com.umc.NewTine.domain.Comment;
import com.umc.NewTine.dto.response.CommentResponseDto;
import com.umc.NewTine.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public List<CommentResponseDto> getCommnetsByNewsId(Long newsId){
        List<Comment> comments = commentRepository.findByNewsId(newsId);

        List<CommentResponseDto> commentDtos = comments.stream()
                .map(comment -> new CommentResponseDto(comment))
                .collect(Collectors.toList());

        return commentDtos;
    }
}
