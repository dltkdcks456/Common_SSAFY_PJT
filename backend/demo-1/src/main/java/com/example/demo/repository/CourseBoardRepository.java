package com.example.demo.repository;

import com.example.demo.domain.CourseBoard;
import com.example.demo.domain.CustomCourse;
import com.example.demo.domain.User;
import com.example.demo.mapping.CourseBoardListMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseBoardRepository extends JpaRepository<CourseBoard, Long> {


    Page<CourseBoardListMapping> findByTitleContaining(String keyword, Pageable pageable);

    CourseBoard findByCourseBoardId(Long courseBoardId);

    List<CourseBoard> findByUserId(User user);

    Page<CourseBoardListMapping> findAllBy(Pageable pageable);

    CourseBoard findByCourseId(CustomCourse courseId);
}