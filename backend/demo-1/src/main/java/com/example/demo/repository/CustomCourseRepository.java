package com.example.demo.repository;

import com.example.demo.domain.Board;
import com.example.demo.domain.Comment;
import com.example.demo.domain.CustomCourse;
import com.example.demo.mapping.CommentMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface CustomCourseRepository extends JpaRepository<CustomCourse, Long> {

    CustomCourse findByCourseId(Long courseId);

    @Transactional
    void deleteByCourseId(Long courseId);
}