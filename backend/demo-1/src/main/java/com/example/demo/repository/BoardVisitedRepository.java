package com.example.demo.repository;

import com.example.demo.domain.BoardGood;
import com.example.demo.domain.BoardVisited;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface BoardVisitedRepository extends JpaRepository<BoardVisited, Long> {

    BoardVisited findByBoardGoodPKBoardIdAndBoardGoodPKUserId(Long boardId, Long userId);

    @Transactional
    void deleteByBoardGoodPKBoardId(Long boardId);
}