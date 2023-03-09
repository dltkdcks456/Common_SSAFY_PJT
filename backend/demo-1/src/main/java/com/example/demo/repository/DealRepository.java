package com.example.demo.repository;

import com.example.demo.domain.Board;
import com.example.demo.domain.Deal;
import com.example.demo.domain.User;
import com.example.demo.mapping.BoardListMapping;
import com.example.demo.mapping.DealListMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DealRepository extends JpaRepository<Deal, Long> {


    List<Deal> findByUserId(User userId);

    Deal findByDealId(Long DealId);

    Page<DealListMapping> findAllBy(Pageable pageable);

    Page<DealListMapping> findByTitleContaining(String keyword, Pageable pageable);

    Page<DealListMapping> findAllByKind(String kind, Pageable pageable);
}