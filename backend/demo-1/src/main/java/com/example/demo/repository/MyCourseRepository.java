package com.example.demo.repository;

import com.example.demo.domain.BoardVisited;
import com.example.demo.domain.MyCourse;
import com.example.demo.domain.MyCoursePK;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface MyCourseRepository extends JpaRepository<MyCourse, Long> {

//    MyCourse findByMyCoursePKCourseIdAndMyCoursePKUserId(Long courseId, Long userId);

    MyCourse findByCourseIdAndUserId(Long courseId, Long userId);
//    @Transactional
//    void deleteByMyCoursePKCourseId(Long courseId);

    @Transactional
    void deleteByCourseId(Long courseId);

//    List<MyCourse> findByMyCoursePKUserId(Long userId);

    List<MyCourse> findByUserId(Long userId);
}