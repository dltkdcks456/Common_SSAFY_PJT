package com.example.demo.repository;

import com.example.demo.domain.Report;
import com.example.demo.domain.User;
import com.example.demo.mapping.UserMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {

    List<Report> findByUserId(User userId);

}