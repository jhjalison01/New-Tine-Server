package com.umc.NewTine.controller;

import com.umc.NewTine.domain.User;
import com.umc.NewTine.dto.response.BaseException;
import com.umc.NewTine.dto.response.BaseResponse;
import com.umc.NewTine.dto.response.NewTechHabitResponse;
import com.umc.NewTine.dto.response.NewTechInfoResponse;
import com.umc.NewTine.service.NewtechService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/newtech")
@Slf4j
public class NewtechController {

    private final NewtechService newtechService;

    @GetMapping("") //뉴테크 페이지 조회
    public BaseResponse<NewTechInfoResponse> getNewTechInfo(@AuthenticationPrincipal User user) {
        try {
            return new BaseResponse<>(newtechService.getNewTechInfo(user.getId()));
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

    @GetMapping("/habit")
    public BaseResponse<NewTechHabitResponse> getNewTechHabit(@AuthenticationPrincipal User user, @RequestParam int year, @RequestParam int month, @RequestParam int day) {
        try {
            return new BaseResponse<>(newtechService.getNewTechHabit(user, year, month, day));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

}
