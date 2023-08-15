package com.umc.NewTine.repository;

import com.umc.NewTine.domain.PressSubscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PressSubscriptionRepository extends JpaRepository<PressSubscription,Long> {
    boolean existsByPressIdAndUserId(Long pressId, Long userId);
    Optional<PressSubscription> findByUserIdAndPressId(Long userId, Long pressId);
}
