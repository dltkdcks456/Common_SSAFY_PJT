package com.example.demo.repository;

import com.example.demo.domain.Board;

import com.example.demo.domain.User;
import com.example.demo.mapping.BoardListMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {


    List<Board> findByUserId(User userId);

    Board findByBoardId(Long BoardId);




//    Page<BoardListMapping> findByTitleContaining(String keyword, Pageable pageable);


    Page<BoardListMapping> findAllByBoardCode(Pageable pageable, int boardCode);

    Page<BoardListMapping> findByBoardCodeAndTitleContaining(int boardCode, String keyword, Pageable pageable);
}