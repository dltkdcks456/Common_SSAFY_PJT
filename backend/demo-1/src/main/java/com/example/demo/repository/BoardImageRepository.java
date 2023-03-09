package com.example.demo.repository;

import com.example.demo.domain.Board;
import com.example.demo.domain.BoardImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardImageRepository extends JpaRepository<BoardImage, Long> {
    List<BoardImage> findByBoardId (Board board);
    void deleteByBoardId(Board board);
}
