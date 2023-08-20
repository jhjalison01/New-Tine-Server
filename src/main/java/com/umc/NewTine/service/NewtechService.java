package com.umc.NewTine.service;

import com.umc.NewTine.dto.response.BaseException;
import com.umc.NewTine.repository.MissionRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewtechService {
    private final MissionRecordRepository missionRecordRepository;

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
