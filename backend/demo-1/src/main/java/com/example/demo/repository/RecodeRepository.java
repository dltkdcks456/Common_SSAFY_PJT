package com.example.demo.repository;

import com.example.demo.domain.Recode;
import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecodeRepository extends JpaRepository<Recode, User> {

    Recode findByUserId(Long userId);

    List<Recode> findAll();

    boolean existsByUserId(Long userId);

}
