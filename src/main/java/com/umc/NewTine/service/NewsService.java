package com.umc.NewTine.service;

import com.umc.NewTine.domain.News;
import com.umc.NewTine.domain.NewsAndCategory;
import com.umc.NewTine.domain.Press;
import com.umc.NewTine.dto.response.BaseException;
import com.umc.NewTine.dto.response.SingleNewsResponseDto;
import com.umc.NewTine.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsService {
    private final NewsRepository newsRepository;
    private final NewsScrapRepository newsScrapRepository;
    private final PressSubscriptionRepository pressSubscriptionRepository;
    private final NewsCategoryRepository newsCategoryRepository;
    private final NewsAndCategoryRepository newsAndCategoryRepository;

    @Autowired
    public NewsService(NewsRepository newsRepository,PressSubscriptionRepository pressSubscriptionRepository,
                       NewsScrapRepository newsScrapRepository, NewsCategoryRepository newsCategoryRepository,
                       NewsAndCategoryRepository newsAndCategoryRepository) {
        this.newsRepository=newsRepository;
        this.pressSubscriptionRepository=pressSubscriptionRepository;
        this.newsScrapRepository=newsScrapRepository;
        this.newsCategoryRepository=newsCategoryRepository;
        this.newsAndCategoryRepository=newsAndCategoryRepository;
    }

    @Transactional
    public SingleNewsResponseDto getSingleNewsById(Long userId,Long newsId) throws BaseException {
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new EntityNotFoundException("News not found with ID: " + newsId));

        Press press = news.getPress();
        boolean scrapped = newsScrapRepository.existsByNewsIdAndUserId(newsId, userId);
        boolean subscribed = pressSubscriptionRepository.existsByPressIdAndUserId(press.getId(), userId);

        List<NewsAndCategory> newsAndCategoryList = newsAndCategoryRepository.findByNewsId(newsId);
        List<String> category = newsAndCategoryList.stream()
                .map(data -> newsCategoryRepository.getById(data.getNewsCategory().getId()).getName())
                .collect(Collectors.toList());

        return SingleNewsResponseDto.builder()
                .title(news.getTitle())
                .content(news.getContent())
                .createdAt(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm").format(news.getCreatedAt()))
                .pressName(press.getName())
                .pressImage(press.getImage())
                .pressSubscriber(press.getSubscriber())
                .subscribed(subscribed)
                .scrapped(scrapped)
                .category(category)
                .build();
    }

}
