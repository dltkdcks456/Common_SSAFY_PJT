package com.example.demo.repository;

import com.example.demo.domain.Board;
import com.example.demo.domain.Comment;
import com.example.demo.domain.CourseBoard;
import com.example.demo.domain.CourseBoardComment;
import com.example.demo.mapping.CommentMapping;
import com.example.demo.mapping.CourseBoardCommentMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface CourseBoardCommentRepository extends JpaRepository<CourseBoardComment, Long> {

    List<CourseBoardCommentMapping> findByCourseBoardId(CourseBoard CourseBoardId);

    CourseBoardComment findByCourseBoardCommentId(Long CourseBoardCommentId);

    @Transactional
    void deleteByCourseBoardId(CourseBoard courseBoardId);


    List<CourseBoardCommentMapping> findAllByCourseBoardIdOrderByTimeDesc(CourseBoard courseBoardId);
}