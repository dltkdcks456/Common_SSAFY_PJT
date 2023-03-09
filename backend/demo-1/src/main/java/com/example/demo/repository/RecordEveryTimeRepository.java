package com.example.demo.repository;

import com.example.demo.domain.RecordEveryTime;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordEveryTimeRepository extends JpaRepository<RecordEveryTime, Long> {

    List<RecordEveryTime> findByUserId(Long userId, Sort sort);
}
