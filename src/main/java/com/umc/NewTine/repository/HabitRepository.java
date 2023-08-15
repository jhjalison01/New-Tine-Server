package com.umc.NewTine.repository;

import com.umc.NewTine.domain.Habit;
import com.umc.NewTine.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HabitRepository extends JpaRepository<Habit, Long> {

    Optional<Habit> findByUser(User user);

}
