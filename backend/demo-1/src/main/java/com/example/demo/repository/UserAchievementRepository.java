package com.example.demo.repository;

import com.example.demo.domain.User;
import com.example.demo.domain.UserAchievement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAchievementRepository extends JpaRepository<UserAchievement, Long> {

    List<UserAchievement> findByUserId(User user);
}
