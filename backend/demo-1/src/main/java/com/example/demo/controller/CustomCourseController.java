package com.example.demo.controller;

import com.example.demo.domain.*;
import com.example.demo.repository.*;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.mail.Multipart;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.*;

@RestController
@Log4j2
@RequestMapping(value = "/api/course/custom")
@CrossOrigin(
        "*"
        // localhost:5500 과 127.0.0.1 구분


//        allowCredentials = "true",
//        allowedHeaders = "*",
//        methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT,RequestMethod.HEAD,RequestMethod.OPTIONS}

)
public class CustomCourseController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CourseBoardRepository courseBoardRepository;

    @Autowired
    CustomCourseRepository customCourseRepository;

    @Autowired
    MyCourseRepository myCourseRepository;

    // @PostMapping("/")
    // public ResponseEntity TestGpx() throws Exception {
    //     System.out.println("꺄홋");
    // return new ResponseEntity(HttpStatus.OK);
    // }

    // gpx 파일 저장 -> multipart/form-data
    @PostMapping("/")
    public ResponseEntity SaveGpx(@RequestParam MultipartFile gpxFile, Long user_id) throws Exception {
//        Long user_id = Long.parseLong(String.valueOf(param.get("user_id")));
        System.out.println("userId : " + user_id + " gpxFile : " + gpxFile);
        log.info("userId : " + user_id + " gpxFile : " + gpxFile);

        String filePath = null;
        String absolutePath = new File("").getAbsolutePath() + "/";
        String path = "GPXDIR";
        File file = new File(path);
        User user = userRepository.findByUserId(user_id);

        if (!file.exists()) {
            file.mkdirs();
        }

        if (!gpxFile.isEmpty()) {
            String fileName = gpxFile.getOriginalFilename(); // 첨부파일명
            String name = fileName.substring(0, fileName.length()-4);
            String extension = gpxFile.getContentType();
            System.out.println("name " + name);
//            StringTokenizer st = new StringTokenizer(fileName, ".");
//            String name = st.nextToken();
//            String extension = st.nextToken();
            if (!fileName.contains("gpx") && !fileName.contains("xml")) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }

            // 랜덤 파일명으로 저장시
            UUID uuid = UUID.randomUUID(); // 랜덤 파일명
            filePath = path + "/" + uuid + "." + "gpx";

//            filePath = path + "/" + fileName;

            file = new File(absolutePath + filePath);
            gpxFile.transferTo(file); // 파일 저장

            String dummyTitle = DigestUtils.sha256Hex("dummyTitle"); // sha 256 16진수로 반환

            // db에 파일 저장
            CustomCourse customCourse = CustomCourse.builder()
                    .userId(user)
                    .name(name)
                    .title(dummyTitle)
                    .path(filePath)
                    .type("gpx")
                    .build();

            Long courseId = customCourseRepository.save(customCourse).getCourseId();
            return new ResponseEntity<>(courseId, HttpStatus.OK);
        }
        else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    // gpx title 설정
    @PutMapping("/title")
    public ResponseEntity UpdateGpxTitle(@RequestBody HashMap<String, Object> param) throws Exception {
        Long course_id = Long.valueOf(String.valueOf(param.get("course_id")));
        String title = (String) param.get("title");

        CustomCourse customCourse = customCourseRepository.findByCourseId(course_id);

        CustomCourse newCustomCourse = CustomCourse.builder()
                .courseId(course_id)
                .userId(customCourse.getUserId())
                .name(customCourse.getName())
                .title(title)
                .path(customCourse.getPath())
                .type(customCourse.getType())
                .build();

        try {
            customCourseRepository.save(newCustomCourse);
            return new ResponseEntity(HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    // gpx 파일 수정(재업로드)
    @PutMapping("/")
    public ResponseEntity UpdateGpx(@RequestParam Long user_id, Long course_id, MultipartFile gpxFile) throws Exception {
//        Long user_id = Long.valueOf(String.valueOf(param.get("user_id")));
//        Long course_id = Long.valueOf(String.valueOf(param.get("course_id")));

        String filePath = null;
        String absolutePath = new File("").getAbsolutePath() + "/";
        String path = "GPXDIR";
        File file = new File(path);
        User user = userRepository.findByUserId(user_id);
        CustomCourse customCourse = customCourseRepository.findByCourseId(course_id);

        if (!file.exists()) {
            file.mkdirs();
        }

        if (!gpxFile.isEmpty()) {
            String fileName = gpxFile.getOriginalFilename(); // 첨부파일명
            String name = fileName.substring(0, fileName.length()-4);
            String extension = gpxFile.getContentType();
//            StringTokenizer st = new StringTokenizer(fileName, ".");
//            String name = st.nextToken();
//            String extension = st.nextToken();

            if (!fileName.contains("gpx") && !fileName.contains("xml")) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }

            // 랜덤 파일명으로 저장시
            UUID uuid = UUID.randomUUID(); // 랜덤 파일명
            filePath = path + "/" + uuid + "." + "gpx";

//            filePath = path + "/" + fileName;

            file = new File(absolutePath + filePath);
            gpxFile.transferTo(file); // 파일 저장

            // db에 파일 저장
            CustomCourse newCustomCourse = CustomCourse.builder()
                    .courseId(course_id)
                    .userId(user)
                    .title(customCourse.getTitle())
                    .name(name)
                    .path(filePath)
                    .type("gpx")
                    .build();

            Long courseId = customCourseRepository.save(newCustomCourse).getCourseId();
            return new ResponseEntity<>(courseId, HttpStatus.OK);
        }
        else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }


    // gpx 읽어와서 위도 경도 고도 보내기
    @GetMapping("/")
    public ResponseEntity ReadGpx(Long course_id) throws Exception {
        // GPX 파일 읽어오기
        String absolutePath = new File("").getAbsolutePath() + "/";
        CustomCourse customCourse = customCourseRepository.findByCourseId(course_id);
        String file = absolutePath + "/" + customCourse.getPath();

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dbuilder = dbFactory.newDocumentBuilder();
        Document doc = dbuilder.parse(file);
        // root tag
        doc.getDocumentElement().normalize();
//        System.out.println("Root element : " + doc.getDocumentElement().getNodeName());

        // 파싱할 정보
        // 출발지 / 목적지
//        NodeList nList = doc.getElementsByTagName("wpt");
        // 중간점
        NodeList nList2 = doc.getElementsByTagName("trkpt");
        int len = nList2.getLength();

        List<Double[]> list = new ArrayList<>();
        Map<Integer, Object> map = new HashMap<>();
        Double [] Arr = new Double[3]; // lat, lon, ele(위도, 경도, 고도)

        // 출발지
//        BigDecimal b = new BigDecimal(nList.item(0).getAttributes().getNamedItem("lat").getNodeValue());
//        Arr[0] = b.doubleValue();
//        b = new BigDecimal(nList.item(0).getAttributes().getNamedItem("lon").getNodeValue());
//        Arr[1] =  b.doubleValue();
//        b = new BigDecimal(doc.getElementsByTagName("ele").item(0).getLastChild().getNodeValue());
//        Arr[2] =  b.doubleValue();
//        map.put(0, Arr);
//        list.add(map);

        // 목적지
//        map = new HashMap<>();
//        Arr = new Double[3];
//        b = new BigDecimal(nList.item(1).getAttributes().getNamedItem("lat").getNodeValue());
//        Arr[0] = b.doubleValue();
//        b = new BigDecimal(nList.item(1).getAttributes().getNamedItem("lon").getNodeValue());
//        Arr[1] =  b.doubleValue();
//        b = new BigDecimal(doc.getElementsByTagName("ele").item(1).getLastChild().getNodeValue());
//        Arr[2] =  b.doubleValue();
//        map.put(1, Arr);
//        list.add(map);

        boolean flag = false;
        if ( doc.getElementsByTagName("wpt").item(0) != null) {
            flag = true;
        }
        if (flag) {
            for(int i=0; i<len; i++) {
                map = new HashMap<>();
                Arr = new Double[3];
                BigDecimal b = new BigDecimal(nList2.item(i).getAttributes().getNamedItem("lat").getNodeValue());
                Arr[0] =  b.doubleValue();
                b = new BigDecimal(nList2.item(i).getAttributes().getNamedItem("lon").getNodeValue());
                Arr[1] =  b.doubleValue();
                b = new BigDecimal(doc.getElementsByTagName("ele").item(i+2).getLastChild().getNodeValue());
                Arr[2] =  b.doubleValue();
                map.put(i+2, Arr);
                list.add(Arr);
            }
        } else {
            for(int i=0; i<len; i++) {
                map = new HashMap<>();
                Arr = new Double[3];
                BigDecimal b = new BigDecimal(nList2.item(i).getAttributes().getNamedItem("lat").getNodeValue());
                Arr[0] =  b.doubleValue();
                b = new BigDecimal(nList2.item(i).getAttributes().getNamedItem("lon").getNodeValue());
                Arr[1] =  b.doubleValue();
                b = new BigDecimal(doc.getElementsByTagName("ele").item(i).getLastChild().getNodeValue());
                Arr[2] =  b.doubleValue();
                map.put(i+2, Arr);
                list.add(Arr);
            }
        }
        // 경유지


        try {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/download")   // course_id를 이용하여 파일 다운로드
    public ResponseEntity<byte[]> getCustomCourseFile(@RequestParam Long courseId, HttpServletResponse response) throws IOException{
        CustomCourse customCourse = customCourseRepository.findByCourseId(courseId);
        String filePath = customCourse.getPath();

        File file=new File(filePath);
        try {
            response.setContentType("application/download");
            response.setContentLength((int)file.length());
            response.setHeader("Content-disposition", "attachment;filename=\"" +
                    customCourse.getName() + "." + customCourse.getType() + "\"");
            // response 객체를 통해서 서버로부터 파일 다운로드
            OutputStream os = response.getOutputStream();
            // 파일 입력 객체 생성
            FileInputStream fis = new FileInputStream(file);
            FileCopyUtils.copy(fis, os);
            fis.close();
            os.close();
            return new ResponseEntity(HttpStatus.OK);
        }catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

    }

    // 내 경로에 있는지 없는지 확인
    @GetMapping("/myCourseCheck")
    public ResponseEntity CheckMyCourse(Long userId, Long courseId) {

        try {
            if ( myCourseRepository.findByCourseIdAndUserId(courseId, userId) != null) {
                return new ResponseEntity<>(true, HttpStatus.OK);
            }
            return new ResponseEntity<>(false, HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    // 내 경로(my course) 저장
    @PostMapping("/myCourse")
    public ResponseEntity SaveMyCourse(@RequestBody HashMap<String, Object> param) throws Exception {
        Long userId = Long.valueOf(String.valueOf(param.get("user_id")));
        Long courseId = Long.valueOf(String.valueOf(param.get("course_id")));
//        MyCoursePK myCoursePK = MyCoursePK.builder()
//                .courseId(courseId)
//                .userId(userId)
//                .build();
        MyCourse myCourse = MyCourse.builder()
//                .myCoursePK(myCoursePK)
                .courseId(courseId)
                .userId(userId)
                .build();
        try {
            myCourseRepository.save(myCourse);
            return new ResponseEntity(HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    // 내 경로(my course) 리스트 불러오기 (타이틀, 유저닉네임)
    @GetMapping("/myCourse")
    public ResponseEntity LoadMyCourse(Long user_id) throws Exception {
        try {
//            List<MyCourse> myCourseList = myCourseRepository.findByMyCoursePKUserId(user_id);
            List<MyCourse> myCourseList = myCourseRepository.findByUserId(user_id);
            List<Object> courseList = new ArrayList<>();

            for (int i=0; i<myCourseList.size(); i++) {
                User user = userRepository.findByUserId(myCourseList.get(i).getUserId());
                CustomCourse customCourse = customCourseRepository.findByCourseId(myCourseList.get(i).getCourseId());
                CourseBoard courseBoard = courseBoardRepository.findByCourseId(customCourse);
                Map<String, Object> courseMap = new HashMap<>();
                courseMap.put("userId", user.getUserId());
                courseMap.put("courseId", customCourse.getCourseId());
                courseMap.put("title", customCourse.getTitle());
                courseMap.put("like", courseBoard.getLikeCount() );
                courseMap.put("visited",courseBoard.getVisited() );
                courseMap.put("regTime", courseBoard.getRegTime() );
                courseList.add(courseMap);
            }

            return new ResponseEntity<>(courseList, HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    // 내 경로(my course) 리스트 불러오기 (타이틀, 유저닉네임) - 앱
    @GetMapping("/myCourseApp")
    public ResponseEntity LoadMyCourseApp(Long user_id) throws Exception {
        try {
            List<MyCourse> myCourseList = myCourseRepository.findByUserId(user_id);
            List<Object> courseList = new ArrayList<>();

            for (int i=0; i<myCourseList.size(); i++) {
                User user = userRepository.findByUserId(myCourseList.get(i).getUserId());
                CustomCourse customCourse = customCourseRepository.findByCourseId(myCourseList.get(i).getCourseId());
                CourseBoard courseBoard = courseBoardRepository.findByCourseId(customCourse);
                Map<String, Object> courseMap = new HashMap<>();
                courseMap.put("courseId", customCourse.getCourseId());
                courseMap.put("title", customCourse.getTitle());
                courseList.add(courseMap);
            }

            return new ResponseEntity<>(courseList, HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    // 내 경로 삭제하기
    @DeleteMapping(value = "/myCourse/{course_id}/{user_id}")
    public ResponseEntity DeleteMyCourse( @PathVariable("course_id") Long course_id, @PathVariable("user_id") Long user_id) throws Exception {
        log.debug("된다!!!!!!!!!!1");
//        MyCourse myCourse = myCourseRepository.findByMyCoursePKCourseIdAndMyCoursePKUserId(course_id, user_id);
        MyCourse myCourse = myCourseRepository.findByCourseIdAndUserId(course_id, user_id);
        try {
            myCourseRepository.delete(myCourse);
            return new ResponseEntity(HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}