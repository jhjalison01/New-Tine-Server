package com.umc.NewTine.dto.response;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {

    SUCCESS(true, 200, "요청에 성공했습니다."),
<<<<<<< HEAD
    NO_NEWS_YET(true, 200, "뉴스가 존재하지 않습니다."),
    NO_USER_ID(true, 200, "사용자가 존재하지 않습니다"),
    SUCCESS_TO_SET_HABIT(true, 200, "습관 설정에 성공했습니다");
=======
    NO_NEWS_YET(true, 200, "뉴스가 없습니다."),
    NO_USER_ID(true, 200, "해당 id의 유저가 없습니다"),
    SUCCESS_TO_SET_HABIT(true, 200, "습관 설정에 성공했습니다"),
    NO_NEWS_YET(true, 200, "뉴스가 존재하지 않습니다.."),
    NO_USER_ID(true, 200, "사용자가 존재하지 않습니다.");
>>>>>>> origin/feature/#17-set-habit


    private final boolean isSuccess;

    private final int code;

    private final String message;

    BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
