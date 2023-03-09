package com.example.demo.repository;

import com.example.demo.domain.CertInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertRepository extends JpaRepository<CertInfo, Long> {

    CertInfo findByEmail(String email);

    CertInfo findByCode(String code);

}