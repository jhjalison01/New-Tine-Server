package com.umc.NewTine.controller;

import com.umc.NewTine.domain.User;
import com.umc.NewTine.dto.*;
import com.umc.NewTine.service.NewtechService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.umc.NewTine.service.NewsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/newtech")
public class NewtechController {

    private final NewsService newsService;
    private final NewtechService newtechService;

    @GetMapping("/{userId}") //뉴테크 페이지 조회
    public BaseResponse<NewTechInfoResponse> getNewTechInfo(@PathVariable Long userId) {
        try {
            return new BaseResponse<>(newsService.getNewTechInfo(userId));

        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
  
    //습관달성 현황달력 조회하기
    @GetMapping("/habit/calendar")
    public BaseResponse<List<Integer>> getAchieveCalendar(@AuthenticationPrincipal User user, @RequestParam("year") int year, @RequestParam("month") int month){
        try {
            Long userId=user.getId();
            //Long userId=1L;
            return new BaseResponse<>(newtechService.getAchieveCalendar(userId,year,month));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

}
