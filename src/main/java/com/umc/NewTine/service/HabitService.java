package com.umc.NewTine.service;

import com.umc.NewTine.domain.Habit;
import com.umc.NewTine.domain.User;
import com.umc.NewTine.dto.response.BaseException;
import com.umc.NewTine.dto.response.BaseResponse;
import com.umc.NewTine.dto.response.HabitDto;
import com.umc.NewTine.repository.HabitRepository;
import com.umc.NewTine.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.umc.NewTine.dto.response.BaseResponseStatus.NO_USER_ID;

@Slf4j
@Service
@RequiredArgsConstructor
public class HabitService {

    private final HabitRepository habitRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public HabitDto getHabit(Long userId) throws BaseException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(NO_USER_ID));

        Optional<Habit> habit = habitRepository.findByUser(user);

        return HabitDto.builder()
                .nums(habit.get().getNums()).build();
    }

    @Transactional
    public void setHabit(Long userId, int num) throws BaseException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(NO_USER_ID));
        Habit habit = habitRepository.findByUser(user).get();
        habit.updateNums(num);
    }

}
