package com.example.demo.repository;

import com.example.demo.domain.Deal;
import com.example.demo.domain.SalesRecord;
import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalesRepository extends JpaRepository<SalesRecord, Long> {

    List<SalesRecord> findByUserId(User user);
    SalesRecord findByDealId(Deal dealId);

    boolean existsByDealId (Deal dealId);
}