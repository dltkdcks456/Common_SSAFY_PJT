package com.example.demo.repository;

import com.example.demo.domain.Board;
import com.example.demo.domain.BoardCode;
import com.example.demo.domain.User;
import com.example.demo.mapping.BoardListMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardCodeRepository extends JpaRepository<BoardCode, Long> {

    BoardCode findByCode(int bc);
}