package com.example.demo.repository;

import com.example.demo.domain.Community;
import com.example.demo.mapping.CommunityListMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityRepository extends JpaRepository<Community, Long> {

    Page<CommunityListMapping> findAllBy(Pageable pageable);
    Community findById(long id);

    Page<CommunityListMapping> findBySi(Pageable pageable, String si);  //  시로 찾기

    Page<CommunityListMapping> findBySiAndGun(Pageable pageable, String si, String gun);    // 시, 군으로 찾기

    Page<CommunityListMapping> findBySiAndGunAndDong(Pageable pageable, String si, String gun, String dong);    // 시, 군, 동으로 찾기

    Page<CommunityListMapping> findByTitleContaining(Pageable pageable, String title); // 제목만으로 찾기

    Page<CommunityListMapping> findBySiAndTitleContaining(Pageable pageable, String si, String title);  // 시, 제목 포함으로 찾기

    Page<CommunityListMapping> findBySiAndGunAndTitleContaining(Pageable pageable, String si, String gun, String title);    // 시, 군 제목으로 찾기

    Page<CommunityListMapping> findBySiAndGunAndDongAndTitleContaining(Pageable pageable, String si, String gun, String Dong, String title);    // 시, 군, 동 제목으로 찾기


}