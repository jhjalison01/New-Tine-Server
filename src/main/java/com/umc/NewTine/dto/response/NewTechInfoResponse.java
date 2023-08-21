package com.umc.NewTine.dto.response;

public class NewTechInfoResponse {

    private int article; //기사개수
    private int time; //기사 머문 시간
    private int goal; // 목표 달성 횟수

    public NewTechInfoResponse(int article, int time, int goal) {
        this.article = article;
        this.time = time;
        this.goal = goal;
    }

    public NewTechInfoResponse(int article, int time) {
        this.article = article;
        this.time = time;
    }

    public int getArticle() {
        return article;
    }

    public int getTime() {
        return time;
    }

    public int getGoal() {
        return goal;
    }
}
