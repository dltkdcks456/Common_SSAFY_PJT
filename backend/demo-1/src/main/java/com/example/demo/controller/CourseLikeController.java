package com.example.demo.controller;

import com.example.demo.domain.*;
import com.example.demo.repository.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@Log4j2
@RequestMapping(value = "/api/course/like")
@CrossOrigin(
        "*"
        // localhost:5500 과 127.0.0.1 구분


//        allowCredentials = "true",
//        allowedHeaders = "*",
//        methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT,RequestMethod.HEAD,RequestMethod.OPTIONS}

)
public class CourseLikeController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CourseBoardRepository courseBoardRepository;

    @Autowired
    CourseLikeRepository courseLikeRepository;
    @Autowired
    private CourseBoardVisitedRepository courseBoardVisitedRepository;

    // 경로 게시판 좋아요 확인
    @GetMapping("/")
    public ResponseEntity CheckBoardLike(Long courseBoardId, Long userId) {
        try {
//            CourseBoard courseBoard = courseBoardRepository.findByCourseBoardId(boardId);
            CourseBoardLike chk = courseLikeRepository.findByCourseBoardPKCourseBoardIdAndCourseBoardPKUserId(courseBoardId, userId);

            boolean result = false;
            if (chk == null || !chk.isSelected()) { // 좋아요 안한 경우
                result = false;
            } else if (chk.isSelected()) { // 좋아요 한 경우
                result = true;
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    // 좋아요
    @PostMapping("/")
    public ResponseEntity BoardLike(@RequestBody HashMap<String, Long> param) {
        try {
            Long courseBoardId = param.get("course_board_id");
            Long userId = param.get("user_id");

            CourseBoard courseBoard = courseBoardRepository.findByCourseBoardId(courseBoardId);
            CourseBoardLike chk = courseLikeRepository.findByCourseBoardPKCourseBoardIdAndCourseBoardPKUserId(courseBoardId, userId);

            CourseBoardPK CBPK = CourseBoardPK.builder()
                    .courseBoardId(courseBoardId)
                    .userId(userId)
                    .build();
            if (chk == null) {
                chk = CourseBoardLike.builder()
                        .courseBoardPK(CBPK)
                        .selected(true)
                        .build();
                courseBoard.setLikeCount(courseBoard.getLikeCount()+1); // 좋아요 1 증가
            } else if (chk.isSelected()) {
                chk.setSelected(false);
                courseBoard.setLikeCount(courseBoard.getLikeCount()-1); // 좋아요 1 감소
            } else if (!chk.isSelected()){
                chk.setSelected(true);
                courseBoard.setLikeCount(courseBoard.getLikeCount()+1); // 좋아요 1 증가
            }
            courseLikeRepository.save(chk);
            courseBoardRepository.save(courseBoard);
            return new ResponseEntity(HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }


}