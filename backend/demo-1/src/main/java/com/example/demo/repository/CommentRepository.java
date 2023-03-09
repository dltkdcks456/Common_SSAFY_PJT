package com.example.demo.repository;

import com.example.demo.domain.Board;
import com.example.demo.domain.Comment;
import com.example.demo.mapping.CommentMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<CommentMapping> findByBoardId(Board boardId);

    Comment findByCommentId(Long commentId);

    @Transactional
    void deleteByBoardId(Board boardId);

    List<CommentMapping> findAllByBoardIdOrderByTimeDesc(Board boardId);
}