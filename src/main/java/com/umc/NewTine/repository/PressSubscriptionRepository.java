package com.umc.NewTine.repository;

import com.umc.NewTine.domain.PressSubscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PressSubscriptionRepository extends JpaRepository<PressSubscription,Long> {
    boolean existsByPressIdAndUserId(Long pressId, Long userId);
}
