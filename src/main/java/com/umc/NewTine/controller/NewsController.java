package com.umc.NewTine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import com.umc.NewTine.dto.request.NewsRecentRequest;
import com.umc.NewTine.dto.response.*;
import com.umc.NewTine.service.NewsService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NewsController {

    private final NewsService newsService;


    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    //개별 뉴스기사 
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

    @GetMapping("/news/{userId}/recommend") //추천 뉴스 조회
    public BaseResponse<List<NewsRecommendResponse>> getRecommendNews(@PathVariable Long userId) {
        try {
            return new BaseResponse<>(newsService.getRecommendNews(userId));
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

