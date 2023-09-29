package com.umc.NewTine.service;

import com.umc.NewTine.domain.Habit;
import com.umc.NewTine.domain.User;
import com.umc.NewTine.dto.request.HabitRequest;
import com.umc.NewTine.dto.response.BaseException;
import com.umc.NewTine.dto.response.HabitDto;
import com.umc.NewTine.repository.HabitRepository;
import com.umc.NewTine.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
                .nums(habit.get().getNums())
                .days(habit.get().getDays())
                .hour(habit.get().getHour())
                .minute(habit.get().getMinute())
                .build();
    }

    @Transactional
    public void setHabit(Long userId, HabitRequest request) throws BaseException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(NO_USER_ID));

        Optional<Habit> habitOptional = habitRepository.findByUser(user);
        if (habitOptional.isPresent()) {
            Habit habit = habitRepository.findByUser(user).get();
            int prev_nums = habit.getNums();
            habit.updateHabitInfo(request.getNums(),request.getDays(),request.getHour(),request.getMinute());
            log.info("습관 업데이트 {} -> {}", prev_nums, request.getNums());
        } else {
            habitRepository.save(Habit.builder()
                    .user(user)
                    .nums(request.getNums())
                    .days(request.getDays())
                    .hour(request.getHour())
                    .minute(request.getMinute())
                    .build());
        }
    }

}