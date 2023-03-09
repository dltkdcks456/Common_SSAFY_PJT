package com.example.demo.repository;

import com.example.demo.domain.BoardVisited;
import com.example.demo.domain.CourseBoard;
import com.example.demo.domain.CourseBoardVisited;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface CourseBoardVisitedRepository extends JpaRepository<CourseBoardVisited, Long> {

    CourseBoardVisited findByCourseBoardPKCourseBoardIdAndCourseBoardPKUserId(Long courseBoardId, Long userId);

    @Transactional
    void deleteByCourseBoardPKCourseBoardId(Long courseBoardId);
}