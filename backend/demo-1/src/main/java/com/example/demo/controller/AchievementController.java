package com.example.demo.controller;

import com.example.demo.domain.*;
import com.example.demo.mapping.BoardListMapping;
import com.example.demo.mapping.CommentMapping;
import com.example.demo.repository.*;
import com.example.demo.service.SecurityService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping(value = "/api/achievement")
@Log4j2
@CrossOrigin(
        "*"
        // localhost:5500 과 127.0.0.1 구분

//        allowCredentials = "true",
//        allowedHeaders = "*",
//        methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT,RequestMethod.HEAD,RequestMethod.OPTIONS}

)
public class AchievementController {
    @Autowired
    CourseBoardRepository courseBoardRepository;
    @Autowired
    BoardRepository boardRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AchievementRepository achievementRepository;

    @Autowired
    UserAchievementRepository userAchievementRepository;

    @Autowired
    RecodeRepository recodeRepository;

    @Autowired
    SalesRepository salesRepository;

    @Autowired
    SecurityService securityService; // jwt 토큰 사용


    // 자신의 업적들을 기록을 보고 갱신하는 API
    @PostMapping("/update")
    public ResponseEntity updateAchievement(@RequestBody HashMap<String, Object> param){

        String id = securityService.getSubject((String) param.get("token"));

        User user = userRepository.findById(id);

//        List<UserAchievement> userAchievementList = userAchievementRepository.findByUserId(user);

        Recode recode = recodeRepository.findByUserId(user.getUserId());

        //주행기록에서 값을 불러온다.
        Long total_dist = recode.getTotalDist();
        Long total_time = recode.getTotalTime();

        // int로 형변환 해준다.
        Integer total_dist_int = Long.valueOf(total_dist).intValue();
        Integer total_time_int = Long.valueOf(total_time).intValue();

        // 게시판 갯수를 불러오기 위해 리스트로 뽑아낸다.
        List<Board> boardList = boardRepository.findByUserId(user);
        List<CourseBoard> courseBoardList = courseBoardRepository.findByUserId(user);

        //작성한 게시판 글 수 확인
        Integer boardCount = boardList.size();
        Integer courseBoardCount = courseBoardList.size();

        //성사 중고거래 수 확인
        List<SalesRecord> salesRecordList = salesRepository.findByUserId(user);
        Integer salesCount = salesRecordList.size();


        // 이미 유저가 해낸 목록들을 정수 리스트로 가져와서 두자
        List<UserAchievement> userAchievementList = userAchievementRepository.findByUserId(user);
        List<Integer> doneList = new ArrayList<>();

        for (int i = 0; i < userAchievementList.size(); i++) {
            Integer done_number = Long.valueOf(userAchievementList.get(i).getAchievementId().getAchievementId()).intValue();
            doneList.add(done_number);
        }

        // 주행거리 업적
        if (total_dist_int >= 42195) { // 1번 항목 달성, 안쪽 if로 들어갈수록 확인하는 주행거리가 더 길어지는 구조

            if (!doneList.contains(1)) {
                UserAchievement userAchievement1 = UserAchievement.builder()
                        .userId(user)
                        .achievementId(achievementRepository.findByAchievementId(1L))
                        .build();

                userAchievementRepository.save(userAchievement1);
            }

            if (total_dist_int >= 157000) { // 2번 항목 달성
                if (!doneList.contains(2)) {
                    UserAchievement userAchievement2 = UserAchievement.builder()
                            .userId(user)
                            .achievementId(achievementRepository.findByAchievementId(2L))
                            .build();

                    userAchievementRepository.save(userAchievement2);


                    if (total_dist_int >= 600000) { // 3번 항목 달성
                        if (!doneList.contains(3)) {
                            UserAchievement userAchievement3 = UserAchievement.builder()
                                    .userId(user)
                                    .achievementId(achievementRepository.findByAchievementId(3L))
                                    .build();

                            userAchievementRepository.save(userAchievement3);
                        }


                    }
                }
            }
        }

        // 주행시간 업적
        if (total_time_int >= 3600*10) { // 7번 항목 달성, 안쪽 if로 들어갈수록 확인하는 주행시간 더 길어지는 구조

            if (!doneList.contains(7)) {
                UserAchievement userAchievement7 = UserAchievement.builder()
                        .userId(user)
                        .achievementId(achievementRepository.findByAchievementId(7L))
                        .build();

                userAchievementRepository.save(userAchievement7);
            }

            if (total_time_int >= 3600*100) { // 8번 항목 달성
                if (!doneList.contains(8)) {
                    UserAchievement userAchievement8 = UserAchievement.builder()
                            .userId(user)
                            .achievementId(achievementRepository.findByAchievementId(8L))
                            .build();

                    userAchievementRepository.save(userAchievement8);


                    if (total_time_int >= 3600*2000) { // 3번 항목 달성
                        if (!doneList.contains(9)) {
                            UserAchievement userAchievement9 = UserAchievement.builder()
                                    .userId(user)
                                    .achievementId(achievementRepository.findByAchievementId(9L))
                                    .build();

                            userAchievementRepository.save(userAchievement9);
                        }
                    }
                }
            }
        }

        // 게시판 글 수 업적
        if (boardCount >= 10) { // 4번 항목 달성, 안쪽 if로 들어갈수록 확인하는 게시판 갯수 더 많아짐

            if (!doneList.contains(4)) {
                UserAchievement userAchievement4 = UserAchievement.builder()
                        .userId(user)
                        .achievementId(achievementRepository.findByAchievementId(4L))
                        .build();

                userAchievementRepository.save(userAchievement4);
            }

            if (boardCount >= 100) { // 5번 항목 달성
                if (!doneList.contains(5)) {
                    UserAchievement userAchievement5 = UserAchievement.builder()
                            .userId(user)
                            .achievementId(achievementRepository.findByAchievementId(5L))
                            .build();

                    userAchievementRepository.save(userAchievement5);


                    if (boardCount >= 2000) { // 6번 항목 달성
                        if (!doneList.contains(6)) {
                            UserAchievement userAchievement6 = UserAchievement.builder()
                                    .userId(user)
                                    .achievementId(achievementRepository.findByAchievementId(6L))
                                    .build();

                            userAchievementRepository.save(userAchievement6);
                        }
                    }
                }
            }
        }

        // 경로 게시판 글 수 업적
        if (courseBoardCount >= 5) { // 10번 항목 달성, 안쪽 if로 들어갈수록 확인하는 경로게시판 갯수 더 많아짐

            if (!doneList.contains(10)) {
                UserAchievement userAchievement10 = UserAchievement.builder()
                        .userId(user)
                        .achievementId(achievementRepository.findByAchievementId(10L))
                        .build();

                userAchievementRepository.save(userAchievement10);
            }

            if (courseBoardCount >= 30) { // 11번 항목 달성
                if (!doneList.contains(11)) {
                    UserAchievement userAchievement11 = UserAchievement.builder()
                            .userId(user)
                            .achievementId(achievementRepository.findByAchievementId(11L))
                            .build();

                    userAchievementRepository.save(userAchievement11);


                    if (courseBoardCount >= 100) { // 12번 항목 달성
                        if (!doneList.contains(12)) {
                            UserAchievement userAchievement12 = UserAchievement.builder()
                                    .userId(user)
                                    .achievementId(achievementRepository.findByAchievementId(12L))
                                    .build();

                            userAchievementRepository.save(userAchievement12);
                        }
                    }
                }
            }
        }

        // 중고거래 성사 수 업적
        if (salesCount >= 5) { // 13번 항목 달성, 안쪽 if로 들어갈수록 확인하는 거래성사 갯수 더 많아짐

            if (!doneList.contains(13)) {
                UserAchievement userAchievement13 = UserAchievement.builder()
                        .userId(user)
                        .achievementId(achievementRepository.findByAchievementId(13L))
                        .build();

                userAchievementRepository.save(userAchievement13);
            }

            if (courseBoardCount >= 30) { // 14번 항목 달성
                if (!doneList.contains(14)) {
                    UserAchievement userAchievement14 = UserAchievement.builder()
                            .userId(user)
                            .achievementId(achievementRepository.findByAchievementId(14L))
                            .build();

                    userAchievementRepository.save(userAchievement14);


                    if (courseBoardCount >= 100) { // 15번 항목 달성
                        if (!doneList.contains(15)) {
                            UserAchievement userAchievement15 = UserAchievement.builder()
                                    .userId(user)
                                    .achievementId(achievementRepository.findByAchievementId(15L))
                                    .build();

                            userAchievementRepository.save(userAchievement15);
                        }
                    }
                }
            }
        }


        return new ResponseEntity(HttpStatus.OK);
    }


    // 특정 유저가 달성한 업적 목록을 반환
    @GetMapping("/getUserAchievement")
    public List<HashMap<String, Object>> getUserAchievement(String nickname){
        User user = userRepository.findByNickname(nickname);
        List<UserAchievement> userAchievementList = userAchievementRepository.findByUserId(user);
        List<HashMap<String, Object>> returnList = new ArrayList<>();

        for (int i = 0; i < userAchievementList.size(); i++){
            HashMap<String,Object> map = new HashMap<String,Object>();
            Achievement achievement = userAchievementList.get(i).getAchievementId();

            map.put("achievement_id", achievement.getAchievementId());
            map.put("achievement_name", achievement.getName());
            map.put("achievement_terms", achievement.getTerms());
            map.put("achievement", achievement.getAchievement());

            returnList.add(map);
        }

        return returnList;
    }


}