package com.umc.NewTine.controller;

import com.umc.NewTine.dto.response.BaseException;
import com.umc.NewTine.dto.response.BaseResponse;
import com.umc.NewTine.dto.response.BaseResponseStatus;
import com.umc.NewTine.dto.response.SingleNewsResponseDto;
import com.umc.NewTine.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/news")
public class NewsController {

    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/{newsId}")
    public BaseResponse<SingleNewsResponseDto>  getSingleNews(@PathVariable("newsId") Long newsId){
        //userId 수정하기
        Long userId=1L;
        try {
            return new BaseResponse<>(newsService.getSingleNewsById(userId,newsId));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
    /*
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

     */



}
