package com.umc.NewTine.controller;

import com.umc.NewTine.dto.response.BaseException;
import com.umc.NewTine.dto.response.BaseResponse;
import com.umc.NewTine.service.PressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class PressController {

    private final PressService pressService;


    @Autowired
    public PressController(PressService pressService) {
        this.pressService = pressService;
    }

    //뉴스 스크랩하기
    @PostMapping("/press{pressId}")
    public BaseResponse<Void> subscribePress(@PathVariable("pressId") Long pressId){
        //userId 수정하기
        Long userId=1L;
        try{
            if (pressService.savePressSubscription(userId,pressId)){
                return new BaseResponse<>(true, HttpStatus.OK.value(),"Success");
            } else{
                return new BaseResponse<>(false,HttpStatus.INTERNAL_SERVER_ERROR.value(),"Fail");
            }
        } catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    //뉴스기사 스크랩 취소하기
    @DeleteMapping("/press{pressId}")
    public BaseResponse<Void> cancelSubscribePress(@PathVariable("pressId") Long pressId){
        Long userId = 1L; // userId 수정하기
        try {
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
