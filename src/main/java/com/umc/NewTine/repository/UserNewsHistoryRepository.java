package com.umc.NewTine.repository;

import com.umc.NewTine.domain.UserNewsHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserNewsHistoryRepository extends JpaRepository<UserNewsHistory, Long> {
    Optional<List<Long>> findNewsIdsByUserIdOrderByViewdAtdesc(Long userId);
}