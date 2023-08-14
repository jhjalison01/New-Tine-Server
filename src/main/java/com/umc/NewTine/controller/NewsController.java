package com.umc.NewTine.controller;

import com.umc.NewTine.dto.request.NewsRecentRequest;
import com.umc.NewTine.dto.response.BaseException;
import com.umc.NewTine.dto.response.BaseResponse;
import com.umc.NewTine.dto.response.NewsRankingResponse;
import com.umc.NewTine.dto.response.NewsRecentResponse;
import com.umc.NewTine.service.NewsService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/news/{userId}/recent")
    public BaseResponse<List<NewsRecentResponse>> getRecentNews(@PathVariable Long userId) {

        try {
            return new BaseResponse<>(newsService.getRecentNews(userId));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/news/ranking")
    public BaseResponse<List<NewsRankingResponse>> getRankingNews() {

        try {
            return new BaseResponse<>(newsService.getRankingNews());
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PostMapping("/news")
    public BaseResponse<Void> saveRecentViewTime(@RequestBody NewsRecentRequest request) {
        try {
            if (newsService.saveRecentViewTime(request)) {
                return new BaseResponse<>(true, HttpStatus.OK.value(), "Success");
            } else {
                return new BaseResponse<>(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Fail");
            }
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }


}