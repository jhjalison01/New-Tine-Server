package com.umc.NewTine.service;

import com.umc.NewTine.domain.News;
import com.umc.NewTine.domain.NewsAndCategory;
import com.umc.NewTine.domain.User;
import com.umc.NewTine.domain.UserNewsHistory;
import com.umc.NewTine.dto.request.NewsRecentRequest;
import com.umc.NewTine.dto.response.*;
import com.umc.NewTine.repository.NewsRepository;
import com.umc.NewTine.repository.UserNewsHistoryRepository;
import com.umc.NewTine.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.umc.NewTine.dto.response.BaseResponseStatus.NO_NEWS_YET;
import static com.umc.NewTine.dto.response.BaseResponseStatus.NO_USER_ID;

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

    @Transactional //최근 본 뉴스 조회
    public List<NewsRecentResponse> getRecentNews(Long userId) throws BaseException {

        User user = userRepository.findById(userId)
                .orElseThrow(()->new BaseException(NO_USER_ID));
        List<News> newsList = userNewsHistoryRepository.findNewsByUserOrderByRecentViewTimeDesc(user)
                .orElse(List.of());
        return newsList.stream()
                .map(NewsRecentResponse::new)
                .limit(10)
                .collect(Collectors.toList());
    }

    @Transactional// 인기 뉴스 조회
    public List<NewsRankingResponse> getRankingNews() throws BaseException{
        List<News> newsList = newsRepository.findAllByOrderByViewsDesc()
                .orElse(List.of());
        return newsList.stream()
                .map(NewsRankingResponse::new)
                .limit(3)
                .collect(Collectors.toList());
    }

    @Transactional //검색어를 포함하는 뉴스 기사 조회
    public List<NewsSearchByWordResponse> searchNewsByWord(String word) throws BaseException {
        List<News> newsList = newsRepository.findNewsByTitleContaining(word)
                .orElse(List.of());
        return newsList.stream()
                .map(NewsSearchByWordResponse::new)
                .limit(5)
                .collect(Collectors.toList());
    }

    @Transactional //추천 뉴스 조회
    public List<NewsRecommendResponse> getRecommendNews(Long userId) throws BaseException {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new BaseException(NO_USER_ID));
        List<News> newsList = NewsAndCategoryRepository.findNewsByUserInterest(user)
                .orElse(List.of());
        return newsList.stream()
                .map(NewsRecommendResponse::new)
                .limit(5)
                .collect(Collectors.toList());
    }

    @Transactional //사용자-뉴스 기록 저장, viewCount 증가
    public boolean saveRecentViewTime(NewsRecentRequest request) throws BaseException{

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(()->new BaseException(NO_USER_ID));
        News news = newsRepository.findById(request.getNewsId())
                .orElseThrow(()->new BaseException(NO_NEWS_YET));
        LocalDateTime recentViewTime = LocalDateTime.now();
        boolean isDuplicate = userNewsHistoryRepository.existsByUserAndNewsAndRecentViewTimeBetween(user, news, recentViewTime.minusMinutes(1), recentViewTime);

        if (!isDuplicate) {
            news.setViews(news.getViews() + 1);
        }

        UserNewsHistory userNewsHistory = userNewsHistoryRepository.findByUserAndNews(user, news)
                .orElseGet(() -> new UserNewsHistory(user, news, recentViewTime));
        userNewsHistory.setRecentViewTime(recentViewTime);
        userNewsHistoryRepository.save(userNewsHistory);

        return true;
    }


}