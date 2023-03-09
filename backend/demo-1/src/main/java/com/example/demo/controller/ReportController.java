package com.example.demo.controller;

import com.example.demo.domain.Report;
import com.example.demo.domain.User;
import com.example.demo.repository.ReportRepository;
import com.example.demo.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@Log4j2
@RequestMapping(value = "/api/report")
@CrossOrigin(
        "*"
        // localhost:5500 과 127.0.0.1 구분


//        allowCredentials = "true",
//        allowedHeaders = "*",
//        methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT,RequestMethod.HEAD,RequestMethod.OPTIONS}

)
public class ReportController {

    @Autowired
    UserRepository userRepository; // 유저 검색

    @Autowired
    ReportRepository reportRepository;

    // 리포트로 가서 유저까지 불러오기
    @GetMapping("/test")
    public List<Report> findRepoAll(){
        return reportRepository.findAll();
    }

    // userId번 사람이 신고한 내용
    @GetMapping("/test2")
    public List<Report> findRepo(Long userId){
        User user = userRepository.findByUserId(userId);
        return reportRepository.findByUserId(user);
    }

    // 유저가 다른 유저 신고하기
    @PostMapping("/doReport")
    public String doReport(@RequestBody HashMap<String, Object> param) {
        Long userId = Long.valueOf(String.valueOf(param.get("user_id")));
        Long userIdReported =  Long.valueOf(String.valueOf(param.get("user_id_reported")));
        String type = (String) param.get("type");
        String content = (String) param.get("content");

        User user = userRepository.findByUserId(userId);
        User userReported = userRepository.findByUserId(userIdReported);

        Report repo = Report.builder()
                .userId(user)
                .userIdReported(userReported)
                .type(type)
                .content(content)
                .build();
        reportRepository.save(repo);
        return "됐냐!!!";
    }




}