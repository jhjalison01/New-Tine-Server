package com.umc.NewTine.repository;

import com.umc.NewTine.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
