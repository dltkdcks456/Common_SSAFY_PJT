package com.example.demo.controller;

import com.example.demo.domain.*;
import com.example.demo.repository.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@RestController
@Log4j2
@RequestMapping(value = "/api/course/comment")
@CrossOrigin(
        "*"
        // localhost:5500 과 127.0.0.1 구분

//
//        allowCredentials = "true",
//        allowedHeaders = "*",
//        methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT,RequestMethod.HEAD,RequestMethod.OPTIONS}

)
public class CourseCommentController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CourseBoardRepository courseBoardRepository;

    @Autowired
    CourseBoardCommentRepository courseBoardCommentRepository;

    // 댓글 작성
    @PostMapping("/")
    public ResponseEntity InsertCourseComment(@RequestBody HashMap<String, Object> param) {

//        System.out.println(param);
        Long userId = Long.valueOf(String.valueOf(param.get("user_id")));
        Long courseBoardId = Long.valueOf(String.valueOf(param.get("course_board_id")));
        String content = (String) param.get("content");

        User user = userRepository.findByUserId(userId);
        CourseBoard courseBoard = courseBoardRepository.findByCourseBoardId(courseBoardId);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String nowDateTime = now.format(dateTimeFormatter);
        LocalDateTime nowTime = LocalDateTime.parse(nowDateTime, dateTimeFormatter);
//        System.out.println(nowTime);

        CourseBoardComment courseBoardComment = CourseBoardComment.builder()
                .userId(user)
                .courseBoardId(courseBoard)
                .content(content)
                .time(nowTime)
                .build();
        try {
            courseBoardCommentRepository.save(courseBoardComment);
            return new ResponseEntity(HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    // 댓글 수정
    @PutMapping("/")
    public ResponseEntity UpdateCourseComment(@RequestBody HashMap<String, Object> param) {
        // 기존의 데이터 불러오기

        Long courseBoardCommentId = Long.valueOf(String.valueOf(param.get("course_board_comment_id")));
//        Long courseBoardId = Long.valueOf(String.valueOf(param.get("course_board_id")));
//        Long userId = Long.valueOf(String.valueOf(param.get("user_id")));
        String content = (String) param.get("content");

        CourseBoardComment originalCourseComment = courseBoardCommentRepository.findByCourseBoardCommentId(courseBoardCommentId);
        originalCourseComment.setContent(content);

        // 수정한 데이터 업데이트
        try {
            courseBoardCommentRepository.save(originalCourseComment);
            return new ResponseEntity(HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

    }

    // 댓글 삭제
    @DeleteMapping("/{courseBoardCommentId}")
    public ResponseEntity DeleteCourseComment(@PathVariable Long courseBoardCommentId) {
        CourseBoardComment courseBoardComment = courseBoardCommentRepository.findByCourseBoardCommentId(courseBoardCommentId);
        try {
            courseBoardCommentRepository.delete(courseBoardComment);
            return new ResponseEntity(HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

}