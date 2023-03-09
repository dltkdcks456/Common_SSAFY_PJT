package com.example.demo.repository;

import com.example.demo.domain.BoardGood;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface LikeRepository extends JpaRepository<BoardGood, Long> {

    BoardGood findByBoardGoodPKBoardIdAndBoardGoodPKUserId(Long boardId, Long userId);

    @Transactional
    void deleteByBoardGoodPKUserId(Long userId);

    @Transactional
    void deleteByBoardGoodPKBoardId(Long boardId);
}