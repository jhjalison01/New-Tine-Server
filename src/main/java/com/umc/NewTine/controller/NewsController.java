package com.umc.NewTine.controller;

import com.umc.NewTine.domain.User;
import com.umc.NewTine.dto.request.CommentRequestDto;
import com.umc.NewTine.dto.request.NewsRecentRequest;
import com.umc.NewTine.dto.response.*;
import com.umc.NewTine.service.CommentService;
import com.umc.NewTine.service.NewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class NewsController {

    private final NewsService newsService;

    private final CommentService commentService;


    @GetMapping("/news")  // 홈 화면 뉴스 조회하기
    public BaseResponse<List<NewsDto>> getHomeNews() {

        try {
            return new BaseResponse<>(newsService.getHomeNews());
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }


    //개별 뉴스기사
    @GetMapping("news/{newsId}")
    public BaseResponse<SingleNewsResponseDto> getSingleNews(@AuthenticationPrincipal User user, @PathVariable("newsId") Long newsId) {
        try {
            //userId 기본값(사용자가 로그인을 하지 않은 경우) - null
            Long userId = null;
            //사용자가 로그인을 한 경우 - user에서 userId 가져오기
            if (user!=null) {
                userId = user.getId();
            }
            return new BaseResponse<>(newsService.getSingleNewsById(userId, newsId));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    //카테고리별 뉴스 기사 조회
    @GetMapping("/news/category/{category}")
    public BaseResponse<List<NewsByCategoryResponse>> getNewsByCategory(@AuthenticationPrincipal User user, @PathVariable String category) {
        try {
            return new BaseResponse<>(newsService.getNewsByCategory(user, category));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/news/recent") //최근 본 뉴스 조회
    public BaseResponse<List<NewsRecentResponse>> getRecentNews(@AuthenticationPrincipal User user) {

        try {
            return new BaseResponse<>(newsService.getRecentNews(user.getId()));
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


    @GetMapping("/news/search") //검색어를 포함하는 뉴스 기사 조회
    public BaseResponse<List<NewsSearchByWordResponse>> searchNewsByWord(@RequestParam String word) {
        try {
            return new BaseResponse<>(newsService.searchNewsByWord(word));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/news/recommend") //추천 뉴스 조회
    public BaseResponse<List<NewsRecommendResponse>> getRecommendNews(@AuthenticationPrincipal User user) {
        try {
            return new BaseResponse<>(newsService.getRecommendNews(user.getId()));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }


    @PostMapping("/news") //사용자-뉴스 기록 저장, viewCount 증가
    public BaseResponse<List<String>> saveRecentViewTime(@RequestBody NewsRecentRequest request) {
        try {
            return new BaseResponse<>(newsService.saveRecentViewTime(request));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    //스크랩한 뉴스 조회
    @GetMapping("/news/scrap")
    public BaseResponse<List<ScrapNewsResponseDto>> getScrappedNews(@AuthenticationPrincipal User user) {
        try {
            Long userId=user.getId();
            return new BaseResponse<>(newsService.getScrappedNews(userId));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }


    //뉴스 스크랩하기
    @PostMapping("/news/scrap/{newsId}")
    public BaseResponse<Void> scrapNews(@AuthenticationPrincipal User user,@PathVariable("newsId") Long newsId) {
        try {
            Long userId=user.getId();
            if (newsService.saveNewsScrap(userId, newsId)) {
                return new BaseResponse<>(true, HttpStatus.OK.value(), "Success");
            } else {
                return new BaseResponse<>(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Fail");
            }
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    //뉴스기사 스크랩 취소하기
    @DeleteMapping("/news/scrap/{newsId}")
    public BaseResponse<Void> cancelScrapNews(@AuthenticationPrincipal User user,@PathVariable("newsId") Long newsId) {
        try {
            Long userId=user.getId();
            if (newsService.deleteNewsScrap(userId, newsId)) {
                return new BaseResponse<>(true, HttpStatus.OK.value(), "Success");
            } else {
                return new BaseResponse<>(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Fail");
            }
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/news/{newsId}/comments")
    public ResponseEntity<List<CommentResponseDto>> getCommentsByPostId(
            @PathVariable Long newsId,
            @RequestParam(required = false) String orderBy) {
        List<CommentResponseDto> commentDTOs = commentService.getCommnetsByNewsId(newsId, orderBy);
        return ResponseEntity.ok(commentDTOs);
    }

    @PostMapping("/news/{newsId}/comments")
    public ResponseEntity<CommentResponseDto> addCommentToPost(
            @AuthenticationPrincipal User user,
            @PathVariable Long newsId,
            @RequestBody CommentRequestDto commentRequest) {
        CommentResponseDto addedComment = commentService.addCommentToNews(newsId, commentRequest, user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(addedComment);
    }

    @PatchMapping("news/comments/{commentId}")
    public ResponseEntity<CommentResponseDto> likeComment(@PathVariable Long commentId) {
        CommentResponseDto likedComment = commentService.likeComment(commentId);
        return ResponseEntity.ok(likedComment);
    }


}
