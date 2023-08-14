package com.umc.NewTine.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.umc.NewTine.dto.response.BaseResponseStatus.SUCCESS;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "Code", "message", "result"})
public class BaseResponse<T> {

    @JsonProperty("isSuccess")  // 속성명 지정
    private final Boolean isSuccess;

    private final int code;

    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)  // null이 아니면 포함시켜 응답으로 전달
    private T result;

    /**
     * 요청 성공
     */
    public BaseResponse(T result) {
        this.isSuccess = SUCCESS.isSuccess();
        this.code = SUCCESS.getCode();
        this.message = SUCCESS.getMessage();
        this.result = result;
    }

    public BaseResponse(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }

    /**
     * 요청 실패
     */
    public BaseResponse(BaseResponseStatus status) {
        this.isSuccess = status.isSuccess();
        this.code = status.getCode();
        this.message = status.getMessage();
    }

}
