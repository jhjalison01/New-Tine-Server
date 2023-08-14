package com.umc.NewTine.controller;

import com.umc.NewTine.dto.request.NewsRecentRequest;
import com.umc.NewTine.dto.response.*;
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

    @GetMapping("/news/{userId}/recent") //최근 본 뉴스 조회
    public BaseResponse<List<NewsRecentResponse>> getRecentNews(@PathVariable Long userId) {

        try {
            return new BaseResponse<>(newsService.getRecentNews(userId));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/news/ranking")// 인기 뉴스 조회
    public BaseResponse<List<NewsRankingResponse>> getRankingNews() {

        try {
            return new BaseResponse<>(newsService.getRankingNews());
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/search") //검색어를 포함하는 뉴스 기사 조회
    public BaseResponse<List<NewsSearchByWordResponse>> searchNewsByWord(@RequestParam String word) {
        try {
            return new BaseResponse<>(newsService.searchNewsByWord(word));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PostMapping("/news") //사용자-뉴스 기록 저장, viewCount 증가
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