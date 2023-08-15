package com.umc.NewTine.repository;

import com.umc.NewTine.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Optional<List<Notification>> findAllByUserId(Long userId);
}
