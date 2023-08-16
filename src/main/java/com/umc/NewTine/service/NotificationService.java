package com.umc.NewTine.service;


import com.umc.NewTine.domain.Notification;
import com.umc.NewTine.domain.Press;
import com.umc.NewTine.domain.User;
import com.umc.NewTine.dto.response.BaseException;
import com.umc.NewTine.dto.response.NotificationResponse;
import com.umc.NewTine.repository.NotificationRepository;
import com.umc.NewTine.repository.PressRepository;
import com.umc.NewTine.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.umc.NewTine.dto.response.BaseResponseStatus.NO_USER_ID;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final PressRepository pressRepository;

    @Transactional(readOnly = true)
    public List<NotificationResponse> getNotification(Long userId) throws BaseException {

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new BaseException(NO_USER_ID));

        List<Notification> notificationList = notificationRepository.findAllByUserId(user.getId()).get();
        List<NotificationResponse> nr = new ArrayList<>();
        
        for (Notification n : notificationList) {
            Press press = pressRepository.findById(n.getPress().getId()).get();
            nr.add(NotificationResponse.from(n, press.getName()));
        }
        return nr;
    }
}
