package com.umc.NewTine.repository;

import com.umc.NewTine.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< HEAD
import org.springframework.stereotype.Repository;
import java.util.Optional;
=======
>>>>>>> origin/feature/#17-set-habit

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
<<<<<<< HEAD

    Boolean existsByEmail(String email);

    Optional<User> findById(Long id);
}
=======
    Boolean existsByEmail(String email);
    Optional<User> findById(Long id);
}
>>>>>>> origin/feature/#17-set-habit
