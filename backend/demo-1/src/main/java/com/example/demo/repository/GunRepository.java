package com.example.demo.repository;


import com.example.demo.domain.Gun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GunRepository extends JpaRepository<Gun, Long> {

    @Query("SELECT m FROM Gun m where m.si_code = :code")
    List<Gun> findByName(@Param("code") Integer code);

}