package com.umc.NewTine.service;

import com.umc.NewTine.domain.User;
import com.umc.NewTine.dto.response.NewsRecentResponse;
import com.umc.NewTine.repository.NewsRepository;
import com.umc.NewTine.repository.UserNewsHistoryRepository;
import com.umc.NewTine.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsService {
    private final NewsRepository newsRepository;
    private final UserRepository userRepository;
    private final UserNewsHistoryRepository userNewsHistoryRepository;

    public NewsService(NewsRepository newsRepository, UserRepository userRepository,UserNewsHistoryRepository userNewsHistoryRepository) {
        this.newsRepository = newsRepository;
        this.userRepository = userRepository;
        this.userNewsHistoryRepository = userNewsHistoryRepository;
    }

    @Transactional
    public List<NewsRecentResponse> getRecentNews(Long userId) {
        //유저정보
        User user = userRepository.findById(userId)
                .orElseThrow(IllegalArgumentException::new);
        //유저가 읽은 newsid
        List<Long> newsIds = userNewsHistoryRepository.findNewsIdsByUserIdOrderByViewdAtdesc(user.getId())
                .orElse(List.of());

        return newsRepository.findAllById(newsIds).stream()
                .map(NewsRecentResponse::new)
                .collect(Collectors.toList());

    }


}