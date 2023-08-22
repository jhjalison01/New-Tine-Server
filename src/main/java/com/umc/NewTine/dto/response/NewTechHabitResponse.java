package com.umc.NewTine.dto.response;

import java.util.List;

public class NewTechHabitResponse {

    private List<Integer> successDate;
    private List<String> successMission;

    public NewTechHabitResponse(List<String>successMission, List<Integer> successDate) {
        this.successDate = successDate;
        this.successMission = successMission;
    }

    public List<Integer> getSuccessDate() {
        return successDate;
    }

    public List<String> getSuccessMission() {
        return successMission;
    }
}
