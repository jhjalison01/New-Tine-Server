package com.umc.NewTine.dto.request;

import java.time.LocalDateTime;

public class NewsRecentRequest {

    private Long newsId;

    private LocalDateTime recentViewTime;

    private LocalDateTime recentViewExitTime;

    public Long getNewsId() {
        return newsId;
    }

    public LocalDateTime getRecentViewTime() {
        return recentViewTime;
    }

    public LocalDateTime getRecentViewExitTime() {
        return recentViewExitTime;
    }
}
