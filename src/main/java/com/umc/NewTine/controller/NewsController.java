package com.umc.NewTine.controller;

import com.umc.NewTine.dto.response.BaseResponse;
import com.umc.NewTine.dto.response.BaseResponseStatus;
import com.umc.NewTine.dto.response.SingleNewsResponseDto;
import com.umc.NewTine.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<BaseResponse<Object>> getSingleNews(@PathVariable("newsId") Long newsId) {
        try{
            SingleNewsResponseDto result=newsService.getSingleNewsById(newsId);
            BaseResponse<Object> successResponse = new BaseResponse<>(result);
            return ResponseEntity.ok(successResponse);
        }
        catch(Exception e){
            BaseResponse<Object> errorResponse = new BaseResponse<>(BaseResponseStatus.NO_NEWS_YET);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


}
