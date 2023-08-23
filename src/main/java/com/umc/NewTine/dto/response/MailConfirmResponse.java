package com.umc.NewTine.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MailConfirmResponse {
    private String mailConfirmNum;

    public MailConfirmResponse(String mailConfirmNum) {
        this.mailConfirmNum = mailConfirmNum;
    }
}
