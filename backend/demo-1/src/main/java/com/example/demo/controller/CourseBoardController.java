package com.example.demo.controller;

import com.example.demo.domain.*;
import com.example.demo.mapping.BoardListMapping;
import com.example.demo.mapping.CourseBoardCommentMapping;
import com.example.demo.mapping.CourseBoardListMapping;
import com.example.demo.repository.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
@RequestMapping(value = "/api/course")
@CrossOrigin(
        "*"
        // localhost:5500 과 127.0.0.1 구분

//        allowCredentials = "true",
//        allowedHeaders = "*",
//        methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT,RequestMethod.HEAD,RequestMethod.OPTIONS}

)
public class CourseBoardController {
    @Autowired
    private MyCourseRepository myCourseRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CourseBoardRepository courseBoardRepository;

    @Autowired
    CourseBoardCommentRepository courseBoardCommentRepository;
    @Autowired
    private CustomCourseRepository customCourseRepository;
    @Autowired
    private CourseBoardVisitedRepository courseBoardVisitedRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CourseLikeRepository courseLikeRepository;


    // 경로 게시판 검색
    @GetMapping("/search") 
    public ResponseEntity findCourseBoardSearch(@PageableDefault(size=10, sort = "regTime", direction = Sort.Direction.DESC)
                                                    Pageable pageable, String keyword) {
//        System.out.println("page : " + pageable);
//        System.out.println("word : " + keyword);
        try {
            Page<CourseBoardListMapping> result = courseBoardRepository.findByTitleContaining(keyword, pageable);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    // 경로 게시판 글 상세 내용 보기
    @GetMapping(value = "/{courseBoardId}", headers = "userId")
    public ResponseEntity findCourseBoardInfo(@PathVariable("courseBoardId") Long courseBoardId,
                                        @RequestHeader("userId") Long userId) {
        try {
            CourseBoard result = courseBoardRepository.findByCourseBoardId(courseBoardId);
            List<CourseBoardCommentMapping> comment = courseBoardCommentRepository.findAllByCourseBoardIdOrderByTimeDesc(result);

            // course_id 확인
            CustomCourse customCourse = customCourseRepository.findByCourseId(result.getCourseId().getCourseId());
            Map<String, Object> courseMap = new HashMap<>();
            courseMap.put("courseId", customCourse.getCourseId());
            courseMap.put("userId", customCourse.getUserId().getUserId());
            courseMap.put("title", customCourse.getTitle());
            courseMap.put("name", customCourse.getName());
            courseMap.put("path", customCourse.getPath());
            courseMap.put("type", customCourse.getType());

            Map<String, Object> map = new HashMap<>();
            map.put("courseBoardId", result.getCourseBoardId());
            map.put("userId", result.getUserId().getUserId());
            map.put("userNickname", result.getUserId().getNickname());
            map.put("courseId", courseMap);
            map.put("title", result.getTitle());
            map.put("content", result.getContent());
            map.put("visited", result.getVisited());
            map.put("likeCount", result.getLikeCount());
            map.put("hateCount", result.getHateCount());
            map.put("regTime", result.getRegTime());

            map.put("comment", comment);

            // 조회수 확인
            CourseBoardPK courseBoardPK = CourseBoardPK.builder()
                    .courseBoardId(courseBoardId)
                    .userId(userId)
                    .build();
            CourseBoardVisited courseBoardVisited = courseBoardVisitedRepository
                    .findByCourseBoardPKCourseBoardIdAndCourseBoardPKUserId(courseBoardId, userId);

            // 첫 방문인 경우 visited DB에 추가 AND board visited 증가
            if (courseBoardVisited == null) {

                CourseBoardVisited newCourseBoardVisited = CourseBoardVisited.builder()
                        .courseBoardPK(courseBoardPK)
                        .build();

                result = result.builder()
                        .courseBoardId(result.getCourseBoardId())
                        .userId(result.getUserId())
                        .title(result.getTitle())
                        .courseId(result.getCourseId())
                        .content(result.getContent())
                        .visited(result.getVisited()+1)
                        .likeCount(result.getLikeCount())
                        .hateCount(result.getHateCount())
                        .regTime(result.getRegTime())
                        .build();

                courseBoardVisitedRepository.save(newCourseBoardVisited);
                courseBoardRepository.save(result);
                map.put("visited", result.getVisited());
            }

            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    // 경로 게시판 리스트 - pagination
    @GetMapping("/")
    public ResponseEntity CourseBoardList(@PageableDefault(size=10, sort = "regTime", direction = Sort.Direction.DESC)
                                              Pageable pageable) {
        try {
            Page<CourseBoardListMapping> result = courseBoardRepository.findAllBy(pageable);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }


    // 경로 게시판 글작성 course_id를 포함해야 함(custom_course가 있어야 한다)
    // 글 작성 -> 첨부파일(custom_course 등록 -> course_id 획득) -> 제목, 내용 작성 -> 글 등록
    // 경로 등록은 /course/custom (post, Long user_id, String title, String name, MultipartFile gpxFile) -> CustomCourseController
    @PostMapping("/")
    public ResponseEntity InsertCourseBoard(@RequestBody HashMap<String, Object> param) {

//        System.out.println(param);
        Long userId = Long.valueOf(String.valueOf(param.get("user_id")));
        Long courseId = Long.valueOf(String.valueOf(param.get("course_id")));
        String title = (String) param.get("title");
        String content = (String) param.get("content");

        User user = userRepository.findByUserId(userId);
        CustomCourse customCourse = customCourseRepository.findByCourseId(courseId);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String nowDateTime = now.format(dateTimeFormatter);
        LocalDateTime nowTime = LocalDateTime.parse(nowDateTime, dateTimeFormatter);
//        System.out.println(nowTime);

        CourseBoard courseBoard = CourseBoard.builder()
                .userId(user)
                .courseId(customCourse)
                .title(title)
                .content(content)
                .visited(Long.parseLong("0"))
                .likeCount(Long.parseLong("0"))
                .hateCount(Long.parseLong("0"))
                .regTime(nowTime)
                .build();
        try {
            courseBoardRepository.save(courseBoard);
            return new ResponseEntity(HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    // 경로 게시판 수정 페이지
    @GetMapping("/update")
    public ResponseEntity UpdateCourseBoardPage(Long courseBoardId) {
        // 기존의 데이터 불러오기
        CourseBoard originalCourse = courseBoardRepository.findByCourseBoardId(courseBoardId);

        // course_id 확인
        CustomCourse customCourse = customCourseRepository.findByCourseId(originalCourse.getCourseId().getCourseId());
        Map<String, Object> courseMap = new HashMap<>();
        courseMap.put("courseId", customCourse.getCourseId());
        courseMap.put("userId", customCourse.getUserId().getUserId());
        courseMap.put("name", customCourse.getName());
        courseMap.put("path", customCourse.getPath());
        courseMap.put("type", customCourse.getType());

        Map<String, Object> map = new HashMap<>();
        map.put("courseBoardId", originalCourse.getCourseBoardId());
        map.put("userId", originalCourse.getUserId().getUserId());
        map.put("userNickname", originalCourse.getUserId().getNickname());
        map.put("courseId", courseMap);
        map.put("title", originalCourse.getTitle());
        map.put("content", originalCourse.getContent());
        map.put("visited", originalCourse.getVisited());
        map.put("likeCount", originalCourse.getLikeCount());
        map.put("hateCount", originalCourse.getHateCount());
        map.put("regTime", originalCourse.getRegTime());

        try {
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

    }

    // 경로 게시판 글수정
    @PutMapping("/")
    public ResponseEntity UpdateCourseBoard(@RequestBody HashMap<String, Object> param) {
        // 기존의 데이터 불러오기
        Long courseBoardId = Long.valueOf(String.valueOf(param.get("course_board_id")));
        Long courseId = Long.valueOf(String.valueOf(param.get("course_id")));
        Long userId = Long.valueOf(String.valueOf(param.get("user_id")));
        String title = (String) param.get("title");
        String content = (String) param.get("content");

        CourseBoard originalCourse = courseBoardRepository.findByCourseBoardId(courseBoardId);

        originalCourse.setTitle(title);
        originalCourse.setContent(content);
//        originalCourse.setCourseId(courseId);

        // 수정한 데이터 업데이트
        try {
            courseBoardRepository.save(originalCourse);
            return new ResponseEntity(HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

    }

    // 경로 게시판 글삭제
    @DeleteMapping("/{courseBoardId}")
    public ResponseEntity DeleteCourseBoard(@PathVariable Long courseBoardId) {
        CourseBoard courseBoard = courseBoardRepository.findByCourseBoardId(courseBoardId);

        try {
            courseBoardVisitedRepository.deleteByCourseBoardPKCourseBoardId(courseBoardId); // visit 삭제
            courseLikeRepository.deleteByCourseBoardPKCourseBoardId(courseBoardId); // like 삭제
            Long courseId = courseBoard.getCourseId().getCourseId();
            courseBoardCommentRepository.deleteByCourseBoardId(courseBoard); // 경로 게시판 댓글 삭제
            myCourseRepository.deleteByCourseId(courseId); // 내경로
            courseBoardRepository.delete(courseBoard); // 경로 게시판 글 삭제
            customCourseRepository.deleteByCourseId(courseId); // 경로 게시판 경로파일 삭제
            return new ResponseEntity(HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
//
//    // 경로 게시판 좋아요 올리기
//    @PostMapping("/likeUp")
//    public ResponseEntity boardLikeUp(@RequestBody HashMap<String, Object> param) {
//        Long boardId = Long.valueOf(String.valueOf(param.get("board_id")));
//        Board originalBoard = boardRepository.findByBoardId(boardId);
//
//        Long original_like = originalBoard.getLikeCount();
//        Long new_like = original_like + 1;
//
//        originalBoard.setLikeCount(new_like);
//
//        boardRepository.save(originalBoard);
//        return new ResponseEntity(HttpStatus.OK);
//    }
//
//    // 게시글 좋아요 내리기
//    @PostMapping("/likeDown")
//    public ResponseEntity boardLikeDown(@RequestBody HashMap<String, Object> param) {
//        Long boardId = Long.valueOf(String.valueOf(param.get("board_id")));
//        Board originalBoard = boardRepository.findByBoardId(boardId);
//
//        Long original_like = originalBoard.getLikeCount();
//        Long new_like = original_like - 1;
//
//        originalBoard.setLikeCount(new_like);
//
//        boardRepository.save(originalBoard);
//        return new ResponseEntity(HttpStatus.OK);
//    }
//
//    // 게시글 싫어요 올리기
//    @PostMapping("/hateUp")
//    public ResponseEntity boardHateUp(@RequestBody HashMap<String, Object> param) {
//        Long boardId = Long.valueOf(String.valueOf(param.get("board_id")));
//        Board originalBoard = boardRepository.findByBoardId(boardId);
//
//        Long original_hate = originalBoard.getLikeCount();
//        Long new_hate = original_hate + 1;
//
//        originalBoard.setLikeCount(new_hate);
//
//        boardRepository.save(originalBoard);
//        return new ResponseEntity(HttpStatus.OK);
//    }
//
//    // 게시글 싫어요 내리기
//    @PostMapping("/hateDown")
//    public ResponseEntity boardHateDown(@RequestBody HashMap<String, Object> param) {
//        Long boardId = Long.valueOf(String.valueOf(param.get("board_id")));
//        Board originalBoard = boardRepository.findByBoardId(boardId);
//
//        Long original_hate = originalBoard.getLikeCount();
//        Long new_hate = original_hate - 1;
//
//        originalBoard.setLikeCount(new_hate);
//
//        boardRepository.save(originalBoard);
//        return new ResponseEntity(HttpStatus.OK);
//    }

}