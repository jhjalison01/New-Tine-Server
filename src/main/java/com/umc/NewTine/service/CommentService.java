package com.umc.NewTine.service;

import com.umc.NewTine.domain.Comment;
import com.umc.NewTine.domain.MissionRecord;
import com.umc.NewTine.domain.News;
import com.umc.NewTine.domain.User;
import com.umc.NewTine.dto.request.CommentRequestDto;
import com.umc.NewTine.dto.response.CommentResponseDto;
import com.umc.NewTine.repository.CommentRepository;
import com.umc.NewTine.repository.MissionRecordRepository;
import com.umc.NewTine.repository.NewsRepository;
import com.umc.NewTine.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    private final NewsRepository newsRepository;

    private final UserRepository userRepository;

    private final MissionRecordRepository missionRecordRepository;

    public List<CommentResponseDto> getCommnetsByNewsId(Long newsId, String orderBy){
        List<Comment> comments;

        if ("latest".equalsIgnoreCase(orderBy)) {
            comments = commentRepository.findByNewsIdOrderByCreatedAtDesc(newsId);
        } else if ("most-liked".equalsIgnoreCase(orderBy)) {
            comments = commentRepository.findByNewsIdOrderByLikesDesc(newsId);
        } else {
            throw new IllegalArgumentException("Invalid orderBy parameter");
        }


        List<CommentResponseDto> commentDtos = comments.stream()
                .map(comment -> new CommentResponseDto(comment))
                .collect(Collectors.toList());

        return commentDtos;
    }

    public CommentResponseDto addCommentToNews(Long newsId, CommentRequestDto commentRequest, Long userId) {
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new EntityNotFoundException("News not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Comment comment = commentRequest.toEntity(news, user);
        Comment savedComment = commentRepository.save(comment);

        if (!missionRecordRepository.existsByUserAndMissionId(user, 3)) {
            missionRecordRepository.save(new MissionRecord(user, 3));
            missionRecordRepository.findSuccessDailyMissionByUser(user);
            return new CommentResponseDto(savedComment, missionRecordRepository.findSuccessDailyMissionByUser(user));
        }

        return new CommentResponseDto(savedComment, Collections.emptyList());
    }

    public CommentResponseDto likeComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));

        comment.updateLike();
        Comment updatedComment = commentRepository.save(comment);

        return new CommentResponseDto(updatedComment);
    }
}
