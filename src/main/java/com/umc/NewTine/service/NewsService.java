package com.umc.NewTine.service;

import com.umc.NewTine.domain.news.NewsRepository;
import com.umc.NewTine.dto.news.response.NewsRecentResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NewsService {
    private final NewsRepository newsRepository;

    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Transactional
    public List<NewsRecentResponse> getRecentNews(Long userId) {
        return newsRepository.findByIdOrderByDateDesc(userId);
    }

}