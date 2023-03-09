package com.example.demo.repository;

import com.example.demo.domain.BoardVisited;
import com.example.demo.domain.DealVisited;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface DealVisitedRepository extends JpaRepository<DealVisited, Long> {

    DealVisited findByDealPKDealIdAndDealPKUserId(Long dealId, Long userId);

    @Transactional
    void deleteByDealPKDealId(Long dealId);
}