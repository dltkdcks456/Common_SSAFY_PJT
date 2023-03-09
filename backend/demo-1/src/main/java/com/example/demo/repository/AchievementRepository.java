package com.example.demo.repository;

import com.example.demo.domain.Achievement;
import com.example.demo.domain.BoardVisited;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface AchievementRepository extends JpaRepository<Achievement, Long> {

    Achievement findByAchievementId(Long achievementId);

}