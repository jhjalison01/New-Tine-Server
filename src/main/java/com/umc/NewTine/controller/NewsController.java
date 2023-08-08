package com.umc.NewTine.controller;

import com.umc.NewTine.dto.response.SingleNewsResponseDto;
import com.umc.NewTine.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/news")
public class NewsController {

    private NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/{newsId}")
    public SingleNewsResponseDto getSingleNews(@PathVariable("newsId") Long newsId) {
        return newsService.getSingleNewsById(newsId);
    }


}
