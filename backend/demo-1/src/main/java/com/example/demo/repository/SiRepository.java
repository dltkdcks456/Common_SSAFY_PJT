package com.example.demo.repository;


import com.example.demo.domain.Si;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiRepository extends JpaRepository<Si, Long> {
    //Si findByName (Long si_code);
}