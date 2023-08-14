package com.umc.NewTine.controller;

import com.umc.NewTine.dto.response.*;
import com.umc.NewTine.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    //스크랩한 뉴스 조회
    @GetMapping("/scrap")
    public BaseResponse<List<ScrapNewsResponseDto>> getScrappedNews(){
        try{
            return new BaseResponse<>(newsService.getScrapNews());
        } catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    /*
    //뉴스 스크랩하기
    @PostMapping("/scrap/{newsId}")
    public ResponseEntity<BaseResponse<Object>> scrapNews(@PathVariable("newsId") Long newsId){

    }

    //스크랩 취소하기
    @DeleteMapping("/scrap/{newsId}")
    public ResponseEntity<BaseResponse<Object>> cancelScrap(@PathVariable("newsId") Long newsId){

    }

     */


}
