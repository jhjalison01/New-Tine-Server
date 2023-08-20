package com.umc.NewTine.controller;

import com.umc.NewTine.dto.response.BaseException;
import com.umc.NewTine.dto.response.BaseResponse;
import com.umc.NewTine.dto.response.NewTechInfoResponse;
import com.umc.NewTine.service.NewsService;
import com.umc.NewTine.service.NewtechService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

//    @GetMapping("/{userId}/habit") //뉴테크 페이지 조회
//    public BaseResponse<NewTechInfoResponse> getNewTechHabit(@PathVariable Long userId) {
//        try {
//            return new BaseResponse<>(newtechService.getNewTechInfo(userId));
//        } catch (BaseException e) {
//            return new BaseResponse<>(e.getStatus());
//        }
//    }

}
