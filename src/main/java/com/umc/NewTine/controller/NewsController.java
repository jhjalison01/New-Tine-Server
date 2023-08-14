package com.umc.NewTine.controller;

import com.umc.NewTine.dto.response.*;
import com.umc.NewTine.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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
    @GetMapping("/news/scrap")
    public BaseResponse<List<ScrapNewsResponseDto>> getScrappedNews(){
        //userId 수정하기
        Long userId=1L;
        try{
            return new BaseResponse<>(newsService.getScrappedNews(userId));
        } catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }


    //뉴스 스크랩하기
    @PostMapping("/news/scrap/{newsId}")
    public BaseResponse<Void> scrapNews(@PathVariable("newsId") Long newsId){
        //userId 수정하기
        Long userId=1L;
        try{
            if (newsService.saveNewsScrap(userId,newsId)){
                return new BaseResponse<>(true,HttpStatus.OK.value(),"Success");
            } else{
                return new BaseResponse<>(false,HttpStatus.INTERNAL_SERVER_ERROR.value(),"Fail");
            }
        } catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    //뉴스기사 스크랩 취소하기
    @DeleteMapping("/news/scrap/{newsId}")
    public BaseResponse<Void> cancelScrapNews(@PathVariable("newsId") Long newsId){
        Long userId = 1L; // 사용자 ID 수정하기
        try {
            if (newsService.deleteNewsScrap(userId, newsId)) {
                return new BaseResponse<>(true,HttpStatus.OK.value(),"Success");
            } else{
                return new BaseResponse<>(false,HttpStatus.INTERNAL_SERVER_ERROR.value(),"Fail");
            }
        } catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }


}
