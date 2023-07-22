package com.umc.NewTine.repository;

import com.umc.NewTine.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Boolean existsByUserId(String userId);
    Boolean existsByEmail(String email);
}
