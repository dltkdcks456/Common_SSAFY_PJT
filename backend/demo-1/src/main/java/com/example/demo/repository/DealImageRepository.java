package com.example.demo.repository;

import com.example.demo.domain.Deal;
import com.example.demo.domain.DealImage;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface DealImageRepository extends JpaRepository<DealImage, Long> {
    List<DealImage> findByDealId (Deal deal);

    DealImage findOneByDealId (Deal deal);

    @Transactional
    void deleteByDealId(Deal deal);
}
