package com.example.demo.repository;

import com.example.demo.domain.Board;
import com.example.demo.domain.UnofficialPoint;
import com.example.demo.domain.User;
import com.example.demo.mapping.BoardListMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface UnofficialPointRepository extends JpaRepository<UnofficialPoint, Long> {

    UnofficialPoint findByUnofficialPointId(Long unofficialPointId);

    List<UnofficialPoint> findByLatBetweenAndLonBetween(BigDecimal slat, BigDecimal elat, BigDecimal slon, BigDecimal elon);
}