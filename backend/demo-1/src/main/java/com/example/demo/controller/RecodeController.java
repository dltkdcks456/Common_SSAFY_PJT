package com.example.demo.controller;

import com.example.demo.domain.ChattingRoomUser;
import com.example.demo.domain.Recode;
import com.example.demo.domain.RecordEveryTime;
import com.example.demo.domain.User;
import com.example.demo.repository.RecodeRepository;
import com.example.demo.repository.RecordEveryTimeRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.SecurityService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@Log4j2
@RequestMapping(value = "/api/recode")
@CrossOrigin(
        "*"
        // localhost:5500 과 127.0.0.1 구분

//
//        allowCredentials = "true",
//        allowedHeaders = "*",
//        methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT,RequestMethod.HEAD,RequestMethod.OPTIONS}

)
public class RecodeController {

    @Autowired
    RecodeRepository recodeRepository;

    @Autowired
    RecordEveryTimeRepository recordEveryTimeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SecurityService securityService; // jwt 사용

    // 특정 유저의 기록을 닉네임을 넣어서 가져온다.
    @GetMapping("/getUserInfo")
    public ResponseEntity<HashMap<String, Object>> getUserRecodeInfo (String nickname) {

        User user = userRepository.findByNickname(nickname);

        Long user_id = user.getUserId();

        Recode recode = recodeRepository.findByUserId(user_id);

        HashMap<String,Object> map = new HashMap<String,Object>();

        // 반환해줄 데이터 목록들
        map.put("total_speed", recode.getTotalSpeed());
        map.put("total_dist", recode.getTotalDist());
        map.put("total_cal", recode.getTotalCal());
        map.put("total_time", recode.getTotalTime());

        map.put("week_speed", recode.getWeekSpeed());
        map.put("week_dist", recode.getWeekDist());
        map.put("week_cal", recode.getWeekCal());
        map.put("week_time", recode.getWeekTime());

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    // 유저의 기록을 시간순으로 모두 불러오기
    @GetMapping("/getUserInfoAll")
    public List<RecordEveryTime> getUserRecordAll (String nickname) {

        User user = userRepository.findByNickname(nickname);

        Long user_id = user.getUserId();
//        List<ChattingRoomUser> rooms = chattingRoomUserRepository.findByUserId(user.getUserId(), Sort.by(Sort.Direction.DESC, "recentChattingTime"));
        return recordEveryTimeRepository.findByUserId(user_id, Sort.by(Sort.Direction.ASC, "updateTime"));
    }

    // 유저의 주행 기록을 넣는 API
    @PostMapping("/putUserRecode")
    public ResponseEntity putUserRecode (@RequestBody HashMap<String, Object> param) {

        String token = (String) param.get("token");
        String Id = securityService.getSubject(token);
        User user = userRepository.findById(Id);

        int int_dist = (int) param.get("dist");
        int int_calorie = (int) param.get("cal");
        int int_time = (int) param.get("time");

        Long distance = Long.valueOf(int_dist);
        Long calorie = Long.valueOf(int_calorie);
        Long time = Long.valueOf(int_time);



//        Long distance = (Long) param.get("dist");   // 미터가 입력됨
//        Long calorie = (Long) param.get("cal");
//        Long time = (Long) param.get("time");       // sec가 입력됨

        double double_speed = (double) int_dist / (double) int_time;
        double_speed = double_speed * 3.6;      // m/s 단위 => km/h

        long speed = (long) double_speed;

//        Recode recode = recodeRepository.findByUserId(user.getUserId());

        if (recodeRepository.existsByUserId(user.getUserId())) {                           // 기존 기록이 있는 유저일 경우 기존 기록에 더해진다.
            Recode recode = recodeRepository.findByUserId(user.getUserId());
            recode.setTotalCal(recode.getTotalCal() + calorie);
            recode.setTotalDist(recode.getTotalDist() + distance);
            recode.setTotalTime(recode.getTotalTime() + time);
            recode.setTotalSpeed(recode.getTotalDist() / recode.getTotalTime());    // 속 = 거/시

            recode.setWeekCal(recode.getWeekCal() + calorie);
            recode.setWeekDist(recode.getWeekDist() + distance);
            recode.setWeekTime(recode.getWeekTime() + time);
            recode.setWeekSpeed(recode.getWeekDist() / recode.getWeekTime());

            recodeRepository.save(recode);

        } else {                                        // 기존에 기록이 없던 유저일 경우

            Recode new_recode = Recode.builder()
                    .userId(user.getUserId())

                    .totalCal(calorie)
                    .totalSpeed(speed)
                    .totalDist(distance)
                    .totalTime(time)

                    .weekCal(calorie)
                    .weekSpeed(speed)
                    .weekDist(distance)
                    .weekTime(time)

                    .build();

            recodeRepository.save(new_recode);
        }


        // 매번 RecordEveryTime 에도 생성해서 넣어줘야한다.

        RecordEveryTime recordEveryTime = RecordEveryTime.builder()
                .userId(user.getUserId())
                .time(time)
                .calories(calorie)
                .distance(distance)
                .speed(speed)
                .updateTime(LocalDateTime.now())
                .build();

        recordEveryTimeRepository.save(recordEveryTime);


        return new ResponseEntity(HttpStatus.OK);
    }

    // 모든 유저의 주간 기록 초기화, admin만 가능
    @DeleteMapping("/resetWeekRecode")
    public ResponseEntity resetWeekRecode(@RequestBody HashMap<String, Object> param){
        String token = (String) param.get("token");
        String id = securityService.getSubject(token);

        User user = userRepository.findById(id);


        if (user.getPermission() == 0){             // admin 계정은 permission이 0
            List<Recode> recodeList = recodeRepository.findAll();
            for(int i = 0; i < recodeList.size(); i++){
                recodeList.get(i).setWeekSpeed(0L);
                recodeList.get(i).setWeekCal(0L);
                recodeList.get(i).setWeekDist(0L);
                recodeList.get(i).setWeekTime(0L);

                recodeRepository.save(recodeList.get(i));
            }

            return new ResponseEntity(HttpStatus.OK);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "관리자만 초기화할 수 있습니다.");
        }
    }

    // 가장 total_time이 긴 사람들을 정렬하여 닉네임과 기록들 반환
    @GetMapping("/getBestTotalTime")
    public List<HashMap<String, Object>> getBestTotalTime() {
        List<Recode> recodeList = recodeRepository.findAll(Sort.by(Sort.Direction.DESC, "totalTime"));
        List<HashMap<String, Object>> returnList = new ArrayList<>();
        // 기록을 50명 까지만 보여줄 예정
        if (recodeList.size() > 50){    // 기록등록 회원이 50명 넘을 경우
            for (int i = 0; i < 50; i++) {
                HashMap<String,Object> map = new HashMap<String,Object>();
                User user = userRepository.findByUserId(recodeList.get(i).getUserId());
                map.put("nickname", user.getNickname());
                map.put("profile", user.getImagePath());
                map.put("total_time", recodeList.get(i).getTotalTime());
                returnList.add(map);
            }
        } else {                        // 기록등록 회원이 50명이 안될 경우 (서비스 초반)
            for (int i = 0; i < recodeList.size(); i++) {
                HashMap<String,Object> map = new HashMap<String,Object>();
                User user = userRepository.findByUserId(recodeList.get(i).getUserId());
                map.put("nickname", user.getNickname());
                map.put("profile", user.getImagePath());
                map.put("total_time", recodeList.get(i).getTotalTime());
                returnList.add(map);
            }
        }
        return returnList;
    }

    // 가장 total_dist가 긴 사람들을 정렬하여 닉네임과 기록들 반환
    @GetMapping("/getBestTotalDist")
    public List<HashMap<String, Object>> getBestTotalDist() {
        List<Recode> recodeList = recodeRepository.findAll(Sort.by(Sort.Direction.DESC, "totalDist"));
        List<HashMap<String, Object>> returnList = new ArrayList<>();
        // 기록을 50명 까지만 보여줄 예정
        if (recodeList.size() > 50){    // 기록등록 회원이 50명 넘을 경우
            for (int i = 0; i < 50; i++) {
                HashMap<String,Object> map = new HashMap<String,Object>();
                User user = userRepository.findByUserId(recodeList.get(i).getUserId());
                map.put("nickname", user.getNickname());
                map.put("profile", user.getImagePath());
                map.put("total_dist", recodeList.get(i).getTotalDist());
                returnList.add(map);
            }
        } else {                        // 기록등록 회원이 50명이 안될 경우 (서비스 초반)
            for (int i = 0; i < recodeList.size(); i++) {
                HashMap<String,Object> map = new HashMap<String,Object>();
                User user = userRepository.findByUserId(recodeList.get(i).getUserId());
                map.put("nickname", user.getNickname());
                map.put("profile", user.getImagePath());
                map.put("total_dist", recodeList.get(i).getTotalDist());
                returnList.add(map);
            }
        }
        return returnList;
    }

    @GetMapping("/getBestTotalCal")
    public List<HashMap<String, Object>> getBestTotalCal() {
        List<Recode> recodeList = recodeRepository.findAll(Sort.by(Sort.Direction.DESC, "totalCal"));
        List<HashMap<String, Object>> returnList = new ArrayList<>();
        // 기록을 50명 까지만 보여줄 예정
        if (recodeList.size() > 50){    // 기록등록 회원이 50명 넘을 경우
            for (int i = 0; i < 50; i++) {
                HashMap<String,Object> map = new HashMap<String,Object>();
                User user = userRepository.findByUserId(recodeList.get(i).getUserId());
                map.put("nickname", user.getNickname());
                map.put("profile", user.getImagePath());
                map.put("total_cal", recodeList.get(i).getTotalCal());
                returnList.add(map);
            }
        } else {                        // 기록등록 회원이 50명이 안될 경우 (서비스 초반)
            for (int i = 0; i < recodeList.size(); i++) {
                HashMap<String,Object> map = new HashMap<String,Object>();
                User user = userRepository.findByUserId(recodeList.get(i).getUserId());
                map.put("nickname", user.getNickname());
                map.put("profile", user.getImagePath());
                map.put("total_cal", recodeList.get(i).getTotalCal());
                returnList.add(map);
            }
        }
        return returnList;
    }

    @GetMapping("/getBestWeekTime")
    public List<HashMap<String, Object>> getBestWeekTime() {
        List<Recode> recodeList = recodeRepository.findAll(Sort.by(Sort.Direction.DESC, "weekTime"));
        List<HashMap<String, Object>> returnList = new ArrayList<>();
        // 기록을 50명 까지만 보여줄 예정
        if (recodeList.size() > 50){    // 기록등록 회원이 50명 넘을 경우
            for (int i = 0; i < 50; i++) {
                HashMap<String,Object> map = new HashMap<String,Object>();
                User user = userRepository.findByUserId(recodeList.get(i).getUserId());
                map.put("nickname", user.getNickname());
                map.put("profile", user.getImagePath());
                map.put("week_time", recodeList.get(i).getWeekTime());
                returnList.add(map);
            }
        } else {                        // 기록등록 회원이 50명이 안될 경우 (서비스 초반)
            for (int i = 0; i < recodeList.size(); i++) {
                HashMap<String,Object> map = new HashMap<String,Object>();
                User user = userRepository.findByUserId(recodeList.get(i).getUserId());
                map.put("nickname", user.getNickname());
                map.put("profile", user.getImagePath());
                map.put("week_time", recodeList.get(i).getWeekTime());
                returnList.add(map);
            }
        }
        return returnList;
    }

    @GetMapping("/getBestWeekDist")
    public List<HashMap<String, Object>> getBestWeekDist() {
        List<Recode> recodeList = recodeRepository.findAll(Sort.by(Sort.Direction.DESC, "weekDist"));
        List<HashMap<String, Object>> returnList = new ArrayList<>();
        // 기록을 50명 까지만 보여줄 예정
        if (recodeList.size() > 50){    // 기록등록 회원이 50명 넘을 경우
            for (int i = 0; i < 50; i++) {
                HashMap<String,Object> map = new HashMap<String,Object>();
                User user = userRepository.findByUserId(recodeList.get(i).getUserId());
                map.put("nickname", user.getNickname());
                map.put("profile", user.getImagePath());
                map.put("week_dist", recodeList.get(i).getWeekDist());
                returnList.add(map);
            }
        } else {                        // 기록등록 회원이 50명이 안될 경우 (서비스 초반)
            for (int i = 0; i < recodeList.size(); i++) {
                HashMap<String,Object> map = new HashMap<String,Object>();
                User user = userRepository.findByUserId(recodeList.get(i).getUserId());
                map.put("nickname", user.getNickname());
                map.put("profile", user.getImagePath());
                map.put("week_dist", recodeList.get(i).getWeekDist());
                returnList.add(map);
            }
        }
        return returnList;
    }


    @GetMapping("/getBestWeekCal")
    public List<HashMap<String, Object>> getBestWeekCal() {
        List<Recode> recodeList = recodeRepository.findAll(Sort.by(Sort.Direction.DESC, "weekCal"));
        List<HashMap<String, Object>> returnList = new ArrayList<>();
        // 기록을 50명 까지만 보여줄 예정
        if (recodeList.size() > 50){    // 기록등록 회원이 50명 넘을 경우
            for (int i = 0; i < 50; i++) {
                HashMap<String,Object> map = new HashMap<String,Object>();
                User user = userRepository.findByUserId(recodeList.get(i).getUserId());
                map.put("nickname", user.getNickname());
                map.put("profile", user.getImagePath());
                map.put("week_cal", recodeList.get(i).getWeekCal());
                returnList.add(map);
            }
        } else {                        // 기록등록 회원이 50명이 안될 경우 (서비스 초반)
            for (int i = 0; i < recodeList.size(); i++) {
                HashMap<String,Object> map = new HashMap<String,Object>();
                User user = userRepository.findByUserId(recodeList.get(i).getUserId());
                map.put("nickname", user.getNickname());
                map.put("profile", user.getImagePath());
                map.put("week_cal", recodeList.get(i).getWeekCal());
                returnList.add(map);
            }
        }
        return returnList;
    }


}
