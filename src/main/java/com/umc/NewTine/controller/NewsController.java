package com.umc.NewTine.controller;

import com.umc.NewTine.dto.response.BaseException;
import com.umc.NewTine.dto.response.BaseResponse;
import com.umc.NewTine.dto.response.BaseResponseStatus;
import com.umc.NewTine.dto.response.SingleNewsResponseDto;
import com.umc.NewTine.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class NewsController {

    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("news/{newsId}")
    public BaseResponse<SingleNewsResponseDto>  getSingleNews(@PathVariable("newsId") Long newsId){
        //userId 수정하기
        Long userId=1L;
        try {
            return new BaseResponse<>(newsService.getSingleNewsById(userId,newsId));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
