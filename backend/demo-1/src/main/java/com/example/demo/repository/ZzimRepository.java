package com.example.demo.repository;

import com.example.demo.domain.SalesRecord;
import com.example.demo.domain.ZzimList;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface ZzimRepository extends JpaRepository<ZzimList, Long> {


    @Transactional
    void deleteByDealPKDealId(Long dealId);

    @Transactional
    void deleteByDealPKDealIdAndDealPKUserId(Long dealId, Long userId);

    List<ZzimList> findByDealPKUserId(Long userId);

    ZzimList findByDealPKUserIdAndDealPKDealId(Long userId, Long dealId);
}