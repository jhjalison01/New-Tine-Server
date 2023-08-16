package com.umc.NewTine.controller;

import com.umc.NewTine.dto.response.BaseException;
import com.umc.NewTine.dto.response.BaseResponse;
import com.umc.NewTine.dto.response.NotificationResponse;
import com.umc.NewTine.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
@Slf4j
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("")
    public BaseResponse<List<NotificationResponse>> getNotifications() {
        /**
         * 토큰에서 userId 정보 가져와서 넣어주는 부분 추가 필요
         */
        Long userId = 1L;

        try {
            return new BaseResponse<>(notificationService.getNotification(userId));
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }
}