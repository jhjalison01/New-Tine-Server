package com.umc.NewTine.controller;

import com.umc.NewTine.domain.User;
import com.umc.NewTine.dto.response.BaseException;
import com.umc.NewTine.dto.response.BaseResponse;
import com.umc.NewTine.dto.response.BaseResponseStatus;
import com.umc.NewTine.dto.response.HabitDto;
import com.umc.NewTine.service.HabitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.umc.NewTine.dto.response.BaseResponseStatus.SUCCESS_TO_SET_HABIT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/home/habit")
@Slf4j
public class HabitController {

    private final HabitService habitservice;

    @GetMapping("")
    public BaseResponse<HabitDto> getHabit(@AuthenticationPrincipal User user) {
        /**
         * 토큰에서 userId 정보 가져와서 넣어주는 부분 추가 필요
         */
        Long userId = user.getId();

        try {
            return new BaseResponse<>(habitservice.getHabit(userId));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PostMapping("")
    public BaseResponse<BaseResponseStatus> setHabit(@AuthenticationPrincipal User user, @RequestBody int num) {

        /**
         * 토큰에서 userId 정보 가져와서 넣어주는 부분 추가 필요
         */
        Long userId = user.getId();

        try {
            habitservice.setHabit(userId, num);
            return new BaseResponse<>(SUCCESS_TO_SET_HABIT);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

}