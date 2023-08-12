package com.umc.NewTine.controller;

import com.umc.NewTine.dto.request.NewsRecentRequest;
import com.umc.NewTine.dto.response.NewsRankingResponse;
import com.umc.NewTine.dto.response.NewsRecentResponse;
import com.umc.NewTine.service.NewsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/news/{userId}/recent")
    public List<NewsRecentResponse> getRecentNews(@PathVariable Long userId) {
        return newsService.getRecentNews(userId);
    }

    @GetMapping("/news/ranking")
    public List<NewsRankingResponse> getRankingNews() {
        return newsService.getRankingNews();
    }

    @PostMapping("/news")
    public void saveRecentViewTime(@RequestBody NewsRecentRequest request) {
        newsService.saveRecentViewTime(request);
    }

}