package com.umc.NewTine.controller;

import com.umc.NewTine.domain.User;
import com.umc.NewTine.dto.response.BaseException;
import com.umc.NewTine.dto.response.BaseResponse;
import com.umc.NewTine.service.PressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class PressController {

    private final PressService pressService;


    @Autowired
    public PressController(PressService pressService) {
        this.pressService = pressService;
    }

    //언론사 구독하기
    @PostMapping("/press/{pressId}")
    public BaseResponse<Void> subscribePress(@AuthenticationPrincipal User user, @PathVariable("pressId") Long pressId){
        try{
            Long userId=user.getId();
            if (pressService.savePressSubscription(userId,pressId)){
                return new BaseResponse<>(true, HttpStatus.OK.value(),"Success");
            } else{
                return new BaseResponse<>(false,HttpStatus.INTERNAL_SERVER_ERROR.value(),"Fail");
            }
        } catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    //언론사 구독 취소하기
    @DeleteMapping("/press/{pressId}")
    public BaseResponse<Void> cancelSubscribePress(@AuthenticationPrincipal User user,@PathVariable("pressId") Long pressId){
        try {
            Long userId=user.getId();
            if (pressService.deletePressSubscription(userId, pressId)) {
                return new BaseResponse<>(true,HttpStatus.OK.value(),"Success");
            } else{
                return new BaseResponse<>(false,HttpStatus.INTERNAL_SERVER_ERROR.value(),"Fail");
            }
        } catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }
}
