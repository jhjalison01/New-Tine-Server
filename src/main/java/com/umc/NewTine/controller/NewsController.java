package com.umc.NewTine.controller;



import com.umc.NewTine.dto.response.NewsRecentResponse;
import com.umc.NewTine.service.NewsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("news/recent")
    public List<NewsRecentResponse> getRecentNews(@RequestParam Long userId) {
        return newsService.getRecentNews(userId);
    }

}