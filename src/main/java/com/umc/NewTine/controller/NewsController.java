package com.umc.NewTine.controller;


import com.umc.NewTine.dto.response.BaseException;
import com.umc.NewTine.dto.response.NewsDto;
import com.umc.NewTine.dto.response.BaseResponse;
import com.umc.NewTine.service.NewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/news")
@Slf4j
public class NewsController {

    private final NewsService newsService;

    @GetMapping("")  // 홈 화면 뉴스 조회하기
    public BaseResponse<List<NewsDto>> getHomeNews() {

        try {
            return new BaseResponse<>(newsService.getHomeNews());
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
