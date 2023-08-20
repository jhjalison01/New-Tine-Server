package com.umc.NewTine.repository;

import com.umc.NewTine.domain.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MissionRepository extends JpaRepository<Mission, Long> {
    @Query("SELECT COUNT(m) FROM Mission m WHERE m.user.id=:userId AND YEAR(m.successAt) = :year AND MONTH(m.successAt) = :month AND DAY(m.successAt) = :day")
    int countMissionByUserIdAndDate(@Param("userId") Long userId, @Param("year") int year, @Param("month") int month,@Param("day") int day);
}
