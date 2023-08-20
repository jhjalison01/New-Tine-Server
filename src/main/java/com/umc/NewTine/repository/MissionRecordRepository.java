package com.umc.NewTine.repository;


import com.umc.NewTine.domain.MissionRecord;
import com.umc.NewTine.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
public interface MissionRecordRepository extends JpaRepository<MissionRecord, Long> {

    @Query("SELECT DISTINCT m.name FROM MissionRecord r INNER JOIN Mission m ON r.mission_id = m.id WHERE r.user = :user AND DATE(r.createdAt) = CURRENT_DATE ")
    List<String> findSuccessDailyMissionByUser(@Param("user") User user);
}

