package com.example.demo.repository;


import com.example.demo.domain.Dong;
import com.example.demo.domain.Gun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DongRepository extends JpaRepository<Dong, Long> {

    //@Query("SELECT m FROM DONG m" + "where m.si_code = :si_code" + "where m.gun_code = :gun_code")
    //List<Dong> findByIdAndName(@Param("si_code, gun_code") Integer si_code, Integer gun_code);

    @Query("SELECT m FROM Dong m where m.si_code = :si_code AND m.gun_code = :gun_code")
    List<Dong> findByIdAndName(@Param("si_code") Integer si_code, @Param("gun_code") Integer gun_code);

}
