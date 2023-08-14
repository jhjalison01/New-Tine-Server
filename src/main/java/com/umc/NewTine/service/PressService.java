package com.umc.NewTine.service;

import com.umc.NewTine.domain.*;
import com.umc.NewTine.dto.response.BaseException;
import com.umc.NewTine.repository.PressRepository;
import com.umc.NewTine.repository.PressSubscriptionRepository;
import com.umc.NewTine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class PressService {

    private final PressSubscriptionRepository pressSubscriptionRepository;
    private final UserRepository userRepository;
    private final PressRepository pressRepository;

    @Autowired
    public PressService(PressSubscriptionRepository pressSubscriptionRepository, UserRepository userRepository, PressRepository pressRepository) {
        this.pressSubscriptionRepository = pressSubscriptionRepository;
        this.userRepository = userRepository;
        this.pressRepository = pressRepository;
    }


    //pressSubscription 저장
    @Transactional
    public boolean savePressSubscription(Long userId,Long pressId) throws BaseException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));

        Press press = pressRepository.findById(pressId)
                .orElseThrow(() -> new EntityNotFoundException("Press not found with ID: " + pressId));

        PressSubscription pressSubscription = new PressSubscription();
        pressSubscription.setUser(user);
        pressSubscription.setPress(press);

        pressSubscriptionRepository.save(pressSubscription);

        return true;
    }

    //pressSubscription 삭제
    @Transactional
    public boolean deletePressSubscription(Long userId, Long pressId) throws BaseException{
        PressSubscription pressSubscription = pressSubscriptionRepository.findByUserIdAndPressId(userId, pressId)
                .orElseThrow(() -> new EntityNotFoundException("PressSubscription not found"));

        pressSubscriptionRepository.delete(pressSubscription);
        return true;
    }
}
