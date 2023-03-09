package com.example.demo.controller;

import com.example.demo.domain.Board;
import com.example.demo.domain.BoardCode;
import com.example.demo.domain.Comment;
import com.example.demo.domain.User;
import com.example.demo.mapping.BoardListMapping;
import com.example.demo.mapping.CommentMapping;
import com.example.demo.repository.BoardCodeRepository;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Log4j2
@RequestMapping(value = "/api/board/comment")
@CrossOrigin(
        "*"
        // localhost:5500 과 127.0.0.1 구분

//
//        allowCredentials = "true",
//        allowedHeaders = "*",
//        methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT,RequestMethod.HEAD,RequestMethod.OPTIONS}

)
public class CommentController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    CommentRepository commentRepository;

    // 댓글 작성
    @PostMapping("/")
    public ResponseEntity InsertComment(@RequestBody HashMap<String, Object> param) {
        // 최신순?? -> findAllBy...                1(Sort.by(Sort.Direction.DESC, "regTime"));
//        System.out.println(param);
        Long userId = Long.valueOf(String.valueOf(param.get("user_id")));
        Long boardId = Long.valueOf(String.valueOf(param.get("board_id")));
        String content = (String) param.get("content");

        User user = userRepository.findByUserId(userId);
        Board board = boardRepository.findByBoardId(boardId);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String nowDateTime = now.format(dateTimeFormatter);
        LocalDateTime nowTime = LocalDateTime.parse(nowDateTime, dateTimeFormatter);
//        System.out.println(nowTime);

        Comment comment = Comment.builder()
                .userId(user)
                .boardId(board)
                .content(content)
                .time(nowTime)
                .build();
        try {
            commentRepository.save(comment);
            return new ResponseEntity(HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    // 댓글 수정
    @PutMapping("/")
    public ResponseEntity UpdateComment(@RequestBody HashMap<String, Object> param) {
        // 기존의 데이터 불러오기

        Long commentId = Long.valueOf(String.valueOf(param.get("comment_id")));
//        Long boardId = Long.valueOf(String.valueOf(param.get("board_id")));
//        Long userId = Long.valueOf(String.valueOf(param.get("user_id")));
        String content = (String) param.get("content");

        Comment originalComment = commentRepository.findByCommentId(commentId);
        originalComment.setContent(content);

        // 수정한 데이터 업데이트
        try {
            commentRepository.save(originalComment);
            return new ResponseEntity(HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity DeleteComment(@PathVariable Long commentId) {
        Comment comment = commentRepository.findByCommentId(commentId);
        try {
            commentRepository.delete(comment);
            return new ResponseEntity(HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

}