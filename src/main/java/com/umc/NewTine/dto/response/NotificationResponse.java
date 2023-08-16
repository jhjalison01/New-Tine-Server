package com.umc.NewTine.dto.response;

import com.umc.NewTine.domain.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {

    private String title;

    private Timestamp createdDate;

    private String name;  // 언론사 이름

    public static NotificationResponse from(Notification notification, String name){

        return NotificationResponse.builder()
                .title(notification.getTitle())
                .name(name)
                .createdDate(notification.getCreatedAt())
                .build();
    }

}