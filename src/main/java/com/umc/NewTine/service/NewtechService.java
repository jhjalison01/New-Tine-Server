package com.umc.NewTine.service;

import com.umc.NewTine.domain.User;
import com.umc.NewTine.dto.response.BaseException;
import com.umc.NewTine.dto.response.NewTechHabitResponse;
import com.umc.NewTine.dto.response.NewTechInfoResponse;
import com.umc.NewTine.repository.MissionRecordRepository;
import com.umc.NewTine.repository.UserNewsHistoryRepository;
import com.umc.NewTine.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.umc.NewTine.dto.response.BaseResponseStatus.NO_USER_ID;

@Slf4j
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

    @Transactional
    public NewTechHabitResponse getNewTechHabit(User user, int year, int month, int day) throws BaseException {

        List<Integer> dayOfResult = new ArrayList<>();
        LocalDate dateTime = LocalDate.of(year,month,day).minusDays(3);
        for (int i = 0; i <= 7; i++) {
            LocalDate newDateTime = dateTime.plusDays(i);
            if (missionRecordRepository.countMissionByUserIdAndDate(user.getId(), newDateTime.getYear(), newDateTime.getMonthValue(), newDateTime.getDayOfMonth()) >= 3) {
                dayOfResult.add(newDateTime.getDayOfMonth());
            }
        }
        return new NewTechHabitResponse( missionRecordRepository.findSuccessDailyMissionByUserAndDate(user, year, month, day), dayOfResult);

    }

    @Transactional(readOnly = true)
    public List<Integer> getAchieveCalendar(Long userId, int year, int month) throws BaseException {
        List<Integer> response = new ArrayList<>();

        for (int i = 1; i <= 31; i++) {
            int num= missionRecordRepository.countMissionByUserIdAndDate(userId,year,month,i);
            if (num>=3){
                response.add(i);
            }
        }
        return response;
    }

}
