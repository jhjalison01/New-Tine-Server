package com.umc.NewTine.repository;

import com.umc.NewTine.domain.MissionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MissionRecordRepository extends JpaRepository<MissionRecord, Long> {
    @Query("SELECT COUNT(m) FROM MissionRecord m WHERE m.user.id=:userId AND YEAR(m.createdAt) = :year AND MONTH(m.createdAt) = :month AND DAY(m.createdAt) = :day")
    int countMissionByUserIdAndDate(@Param("userId") Long userId, @Param("year") int year, @Param("month") int month,@Param("day") int day);
}
