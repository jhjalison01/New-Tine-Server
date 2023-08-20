package com.umc.NewTine.controller;

import com.umc.NewTine.dto.request.NewtechHabitRequest;
import com.umc.NewTine.dto.response.BaseException;
import com.umc.NewTine.dto.response.BaseResponse;
import com.umc.NewTine.dto.response.NewTechInfoResponse;
import com.umc.NewTine.service.NewsService;
import com.umc.NewTine.service.NewtechService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/newtech")
@Slf4j
public class NewtechController {

    private final NewtechService newtechService;

    @GetMapping("/{userId}") //뉴테크 페이지 조회
    public BaseResponse<NewTechInfoResponse> getNewTechInfo(@PathVariable Long userId) {
        try {
            return new BaseResponse<>(newtechService.getNewTechInfo(userId));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }


    @PostMapping("/habit")
    public BaseResponse<NewTechHabitResponse> getNewTechHabit(NewtechHabitRequest habitRequest) {
        try {
            return new BaseResponse<>(newtechService.getNewTechHabit(habitRequest));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

}
