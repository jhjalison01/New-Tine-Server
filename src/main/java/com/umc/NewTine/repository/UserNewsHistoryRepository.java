package com.umc.NewTine.repository;

import com.umc.NewTine.domain.UserNewsHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserNewsHistoryRepository extends JpaRepository<UserNewsHistory, Long> {

}