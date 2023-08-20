package com.umc.NewTine.service;

import com.umc.NewTine.domain.User;
import com.umc.NewTine.domain.UserNewsHistory;
import com.umc.NewTine.dto.response.BaseException;
import com.umc.NewTine.dto.response.NewTechInfoResponse;
import com.umc.NewTine.repository.MissionRecordRepository;
import com.umc.NewTine.repository.UserNewsHistoryRepository;
import com.umc.NewTine.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.umc.NewTine.dto.response.BaseResponseStatus.NO_USER_ID;

@Service
@RequiredArgsConstructor
public class NewtechService {

    private final UserNewsHistoryRepository userNewsHistoryRepository;
    private final UserRepository userRepository;
    private final MissionRecordRepository missionRecordRepository;
    @Transactional
    public NewTechInfoResponse getNewTechInfo(Long userId) throws BaseException {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new BaseException(NO_USER_ID));

        return new NewTechInfoResponse(userNewsHistoryRepository.countTodayNewsViews(user),
                userNewsHistoryRepository.timeSpentByUserToday(user),
                missionRecordRepository.findSuccessDailyMissionByUser(user).size()
        );
    }
}
