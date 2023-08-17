package com.umc.NewTine.repository;

import com.umc.NewTine.domain.Image;
import com.umc.NewTine.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

    Image findByUser(User user);
}
