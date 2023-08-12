package com.umc.NewTine.service;

import com.umc.NewTine.domain.News;
import com.umc.NewTine.domain.User;
import com.umc.NewTine.domain.UserNewsHistory;
import com.umc.NewTine.dto.request.NewsRecentRequest;
import com.umc.NewTine.dto.response.NewsRankingResponse;
import com.umc.NewTine.dto.response.NewsRecentResponse;
import com.umc.NewTine.repository.NewsRepository;
import com.umc.NewTine.repository.UserNewsHistoryRepository;
import com.umc.NewTine.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsService {
    private final NewsRepository newsRepository;
    private final UserRepository userRepository;
    private final UserNewsHistoryRepository userNewsHistoryRepository;

    public NewsService(NewsRepository newsRepository,
                       UserRepository userRepository,
                       UserNewsHistoryRepository userNewsHistoryRepository) {
        this.newsRepository = newsRepository;
        this.userRepository = userRepository;
        this.userNewsHistoryRepository = userNewsHistoryRepository;
    }

    @Transactional
    public List<NewsRecentResponse> getRecentNews(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(IllegalArgumentException::new);
        List<News> newsList = userNewsHistoryRepository.findNewsByUserOrderByRecentViewTimeDesc(user)
                .orElse(List.of());
        return newsList.stream()
                .map(NewsRecentResponse::new)
                .limit(5)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<NewsRankingResponse> getRankingNews() {
        List<News> newsList = newsRepository.findAllByOrderByViewDesc()
                .orElse(List.of());
        return newsList.stream()
                .map(NewsRankingResponse::new)
                .limit(3)
                .collect(Collectors.toList());
    }

    @Transactional
    public void saveRecentViewTime(NewsRecentRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow((IllegalArgumentException::new));
        News news = newsRepository.findById(request.getNewsId())
                .orElseThrow((IllegalArgumentException::new));
        LocalDateTime recentViewTime = LocalDateTime.now();
        boolean isDuplicate = userNewsHistoryRepository.existsByUserAndNewsAndRecentViewTimeBetween(user, news, recentViewTime.minusMinutes(1), recentViewTime);

        if (!isDuplicate) {
            news.setViews(news.getViews() + 1);
        }

        UserNewsHistory userNewsHistory = userNewsHistoryRepository.findByUserAndNews(user, news)
                .orElseGet(() -> new UserNewsHistory(user, news, recentViewTime));
        userNewsHistory.setRecentViewTime(recentViewTime);
        userNewsHistoryRepository.save(userNewsHistory);

    }


}