package com.umc.NewTine.repository;

import com.umc.NewTine.domain.Press;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PressRepository extends JpaRepository<Press, Long> {

    Optional<Press> findByName(String name);
}
