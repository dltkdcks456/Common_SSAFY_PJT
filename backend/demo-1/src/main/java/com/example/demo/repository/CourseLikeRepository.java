package com.example.demo.repository;

import com.example.demo.domain.BoardGood;
import com.example.demo.domain.CourseBoardLike;
import com.example.demo.domain.CourseBoardPK;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface CourseLikeRepository extends JpaRepository<CourseBoardLike, Long> {

    CourseBoardLike findByCourseBoardPKCourseBoardIdAndCourseBoardPKUserId(Long courseBoardId, Long userId);

    @Transactional
    void deleteByCourseBoardPKCourseBoardId(Long courseBoardId);
}