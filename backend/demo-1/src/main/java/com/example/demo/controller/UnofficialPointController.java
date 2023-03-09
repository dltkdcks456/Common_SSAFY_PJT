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
import java.math.BigDecimal;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping(value = "/api/unofficial")
@Log4j2
@CrossOrigin(
        // localhost:5500 과 127.0.0.1 구분
//        allowCredentials = "true",
//        allowedHeaders = "*",
//        methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT,RequestMethod.HEAD,RequestMethod.OPTIONS}
        "*"

)
public class UnofficialPointController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UnofficialPointRepository unofficialPointRepository;
    @Autowired
    SecurityService securityService; // jwt 토큰 사용


    // 비공식 장소 등록
    @PostMapping("/")
    public ResponseEntity AddUnofficial(@RequestParam Long user_id, String name, String content,
                                        BigDecimal lat, BigDecimal lon, MultipartFile image_name) throws Exception {
//        Long userId = Long.parseLong(String.valueOf(param.get("user_id")));
//        String name = (String) param.get("name");
//        String imageName = (String) param.get("image_name");
//        String content = (String) param.get("content");
//        Long lat = Long.parseLong(String.valueOf(param.get("lat")));
//        Long lon = Long.parseLong(String.valueOf(param.get("lon")));

        String filePath = null;
        String absolutePath = new File("").getAbsolutePath() + "\\";
        String path = "Unofficial";
        File file = new File(path);
        User user = userRepository.findById(user_id).orElseThrow(IllegalArgumentException::new);

        if (!file.exists()) {
            file.mkdirs();
        }

        if (!image_name.isEmpty()) {
            String fileName = image_name.getOriginalFilename(); // 첨부파일명
            StringTokenizer st = new StringTokenizer(fileName, ".");
            String fileOriginalName = st.nextToken();
            String extension = st.nextToken();
            String fileExtension = image_name.getContentType();

            if (!extension.contains("jpg") && !extension.contains("png") && !extension.contains("jpeg")) {
                return new ResponseEntity("이미지 파일은 jpg, png 만 가능합니다.",HttpStatus.BAD_REQUEST);
            }

            // 랜덤 파일명으로 저장시
            UUID uuid = UUID.randomUUID(); // 랜덤 파일명
            filePath = path + "/" + uuid + "." + extension;

//            filePath = path + "/" + fileName;

            file = new File(absolutePath + filePath);
            image_name.transferTo(file); // 파일 저장

            // db에 파일 저장
            UnofficialPoint unofficialPoint = UnofficialPoint.builder()
                    .userId(user)
                    .name(name)
                    .imageName(filePath)
                    .content(content)
                    .lat(lat)
                    .lon(lon)
                    .build();

            unofficialPointRepository.save(unofficialPoint);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    // 비공식 장소 수정
    @PutMapping("/")
    public ResponseEntity UpdateUnofficial(@RequestParam Long unofficial_point_id, Long user_id, String name, String content,
                                           BigDecimal lat, BigDecimal lon, MultipartFile image_name) throws Exception {

        String filePath = null;
        String absolutePath = new File("").getAbsolutePath() + "\\";
        String path = "Unofficial";
        File file = new File(path);
        User user = userRepository.findById(user_id).orElseThrow(IllegalArgumentException::new);
        UnofficialPoint unofficialPoint = unofficialPointRepository.findByUnofficialPointId(unofficial_point_id);

        if (!file.exists()) {
            file.mkdirs();
        }

        if (!image_name.isEmpty()) {
            String fileName = image_name.getOriginalFilename(); // 첨부파일명
            StringTokenizer st = new StringTokenizer(fileName, ".");
            String fileOriginalName = st.nextToken();
            String extension = st.nextToken();
            String fileExtension = image_name.getContentType();

            if (!extension.contains("jpg") && !extension.contains("png") && !extension.contains("jpeg")) {
                return new ResponseEntity("이미지 파일은 jpg, png 만 가능합니다.",HttpStatus.BAD_REQUEST);
            }

            // 랜덤 파일명으로 저장시
            UUID uuid = UUID.randomUUID(); // 랜덤 파일명
            filePath = path + "/" + uuid + "." + extension;

//            filePath = path + "/" + fileName;

            file = new File(absolutePath + filePath);
            image_name.transferTo(file); // 파일 저장

            // db에 파일 저장
            UnofficialPoint newUnofficialPoint = UnofficialPoint.builder()
                    .unofficialPointId(unofficialPoint.getUnofficialPointId())
                    .userId(user)
                    .name(name)
                    .imageName(filePath)
                    .content(content)
                    .lat(lat)
                    .lon(lon)
                    .build();

            unofficialPointRepository.save(newUnofficialPoint);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    // 비공식 장소 조회 // 특정 위치 정보를 받아서 근처에 있는 장소를 찾아서 전송?
    // 위도 1도 = 110km / 경도 1도 = 88km
    // 0.01 = 1.1km /   0.001 = 110m    /   0.0001 = 11m
    // 근처? -> 2km 정도? 오차가 좀 있음(현재 위도 37로 입력 시 37.007로 나옴 경도는 37 -> 37.27) 입력값에서 오차가 생겨서 이렇게 해놨음
    @GetMapping("/")
    public ResponseEntity SelectUnofficial(BigDecimal lat, BigDecimal lon) throws Exception {
        BigDecimal slat = lat.subtract((BigDecimal.valueOf(0.03))); // 경도 -1km
        BigDecimal elat = lat.add((BigDecimal.valueOf(0.001))); // 경도 +1km
        BigDecimal slon = lon.subtract((BigDecimal.valueOf(0.03))); // 위도 -1km
        BigDecimal elon = lon.add((BigDecimal.valueOf(0.001))); // 위도 +1km
//        System.out.println("slat : " + slat + " elat : " + elat + " slon : " + slon + " elon : " + elon);
        List<UnofficialPoint> unofficialPoints = unofficialPointRepository.findByLatBetweenAndLonBetween(slat, elat, slon, elon);
        List unofficialList = new ArrayList();
        for (int i=0; i<unofficialPoints.size(); i++) {
            UnofficialPoint unofficialPoint = unofficialPoints.get(i);
            User user = unofficialPoint.getUserId();
            Map<String, Object> unOfficialMap = new HashMap<>();

            unOfficialMap.put("unofficialPointId", unofficialPoint.getUnofficialPointId());
            unOfficialMap.put("userNickname", user.getNickname());
            unOfficialMap.put("name", unofficialPoint.getName());
            unOfficialMap.put("imageName", unofficialPoint.getImageName());
            unOfficialMap.put("content", unofficialPoint.getContent());
            unOfficialMap.put("lat", unofficialPoint.getLat());
            unOfficialMap.put("lon", unofficialPoint.getLon());

            unofficialList.add(unOfficialMap);
        }

        try {
            return new ResponseEntity<>(unofficialList, HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    // 비공식 장소 삭제
    @DeleteMapping("/{unofficial_point_id}")
    public ResponseEntity DeleteUnofficial(@PathVariable Long unofficial_point_id) throws Exception {
        UnofficialPoint unofficialPoint = unofficialPointRepository.findByUnofficialPointId(unofficial_point_id);
        try {
            unofficialPointRepository.delete(unofficialPoint);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }



}