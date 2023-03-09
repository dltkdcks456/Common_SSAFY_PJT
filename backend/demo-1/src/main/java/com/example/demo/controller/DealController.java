package com.example.demo.controller;

import com.example.demo.domain.*;
import com.example.demo.mapping.DealListMapping;
import com.example.demo.repository.*;
import com.example.demo.service.SecurityService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping(value = "/api/deal")
@Log4j2
@CrossOrigin(
        "*"
        // localhost:5500 과 127.0.0.1 구분


//        allowCredentials = "true",
//        allowedHeaders = "*",
//        methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT,RequestMethod.HEAD,RequestMethod.OPTIONS}

)
public class DealController {
    @Autowired
    private DongRepository dongRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    DealRepository dealRepository;

    @Autowired
    DealVisitedRepository dealVisitedRepository;

    @Autowired
    DealImageRepository dealImageRepository;

    @Autowired
    SalesRepository salesRepository;

    @Autowired
    ZzimRepository zzimRepository;

    @Autowired
    SecurityService securityService; // jwt 토큰 사용


    // 중고거래 검색
    @GetMapping("/search") 
    public ResponseEntity findDealSearch(
//            @PathVariable("boardCode") int boardCode,
            @PageableDefault(size=10, sort = "time", direction = Sort.Direction.DESC)
            Pageable pageable, String keyword) {

        try {
            Page<DealListMapping> result = dealRepository.findByTitleContaining(keyword, pageable);
            // 썸네일
            List<Object> outPut = new ArrayList<>();
            int size = result.getNumberOfElements();
            int page = result.getPageable().getPageNumber();
            Map<Long, String> map = new HashMap<>();
            for (int i=0; i<size; i++) {
                String content = result.getContent().get(i).getContent();
                String res = StringUtils.substringBetween(content, "img src=\"", "\" alt=\"image\" ");

                map.put(result.getContent().get(i).getDealId(), res);
            }
            List<Long> keyList = new ArrayList<>(map.keySet());
            keyList.sort((o1, o2) -> o2.compareTo(o1));

            outPut.add(result);
            outPut.add(map);
            return new ResponseEntity<>(outPut, HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

    }

    // 중고거래 게시판 글 상세 내용 보기
    @GetMapping(value = "/detail", headers = "userId")
    public ResponseEntity findDealInfo(Long dealId,
                                        @RequestHeader("userId") Long userId) {
        try {
            Deal result = dealRepository.findByDealId(dealId);

            Map<String, Object> map = new HashMap<>();
            map.put("dealId", result.getDealId());
            map.put("userId", result.getUserId().getUserId());
            map.put("userNickname", result.getUserId().getNickname());
            map.put("kind", result.getKind());
            map.put("title", result.getTitle());
            map.put("content", result.getContent());
            map.put("visited", result.getVisited());
            map.put("name", result.getName());
            map.put("price", result.getPrice());
            map.put("time", result.getTime());
            map.put("onSale", result.isOnSale());


            // deal_visited 확인
            DealPK dealPK = DealPK.builder()
                    .dealId(dealId)
                    .userId(userId)
                    .build();
            DealVisited dealVisited = dealVisitedRepository
                    .findByDealPKDealIdAndDealPKUserId(dealId, userId);

            // 첫 방문인 경우 visited DB에 추가 AND deal visited 증가
            if (dealVisited == null) {

                DealVisited newDealVisited = DealVisited.builder()
                        .dealPK(dealPK)
                        .build();

                result = result.builder()
                        .dealId(result.getDealId())
                        .userId(result.getUserId())
                        .kind(result.getKind())
                        .title(result.getTitle())
                        .content(result.getContent())
                        .name(result.getName())
                        .price(result.getPrice())
                        .onSale(result.isOnSale())
                        .visited(result.getVisited()+1)
                        .time(result.getTime())
                        .build();

                dealVisitedRepository.save(newDealVisited);
                dealRepository.save(result);
                map.put("visited", result.getVisited());
            }

            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    // 중고거래 게시판 글 상세 내용 보기 - 앱용
    @GetMapping(value = "/app/detail", headers = "userId")
    public ResponseEntity findDealInfoApp(Long dealId,
                                       @RequestHeader("userId") Long userId) {
        try {
            Deal result = dealRepository.findByDealId(dealId);

            Map<String, Object> map = new HashMap<>();
            map.put("dealId", result.getDealId());
            map.put("userId", result.getUserId().getUserId());
            map.put("userNickname", result.getUserId().getNickname());
            map.put("kind", result.getKind());
            map.put("title", result.getTitle());

            // 이미지 태그 빼고 보내기
            String outputContent = result.getContent();
            while (true) { //이미지 태그가 더 없을 때까지 반복해서 모든 이미지 태그를 찾기
                String res = StringUtils.substringBetween(outputContent, "<img", "alt=\"image\" contenteditable=\"false\">");
                if (res == null || res == "") { // 더이상 이미지 태그가 없으면 끝
                    break;
                }
                // 이미지 태그 제거
                outputContent = outputContent.replace(res, "");
                String remove = "<p><imgalt=\"image\" contenteditable=\"false\"><br></p>";
                outputContent = outputContent.replace(remove, "");
            }

            map.put("content", outputContent);
            map.put("visited", result.getVisited());
            map.put("name", result.getName());
            map.put("price", result.getPrice());
            map.put("time", result.getTime());
            map.put("onSale", result.isOnSale());


            // deal_visited 확인
            DealPK dealPK = DealPK.builder()
                    .dealId(dealId)
                    .userId(userId)
                    .build();
            DealVisited dealVisited = dealVisitedRepository
                    .findByDealPKDealIdAndDealPKUserId(dealId, userId);

            // 첫 방문인 경우 visited DB에 추가 AND deal visited 증가
            if (dealVisited == null) {

                DealVisited newDealVisited = DealVisited.builder()
                        .dealPK(dealPK)
                        .build();

                result = result.builder()
                        .dealId(result.getDealId())
                        .userId(result.getUserId())
                        .kind(result.getKind())
                        .title(result.getTitle())
                        .content(result.getContent())
                        .name(result.getName())
                        .price(result.getPrice())
                        .onSale(result.isOnSale())
                        .visited(result.getVisited()+1)
                        .time(result.getTime())
                        .build();

                dealVisitedRepository.save(newDealVisited);
                dealRepository.save(result);
                map.put("visited", result.getVisited());
            }

            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    // 게시판 리스트 - pagination
    @GetMapping("/")
    public ResponseEntity DealList(@PageableDefault(size=9, sort = "time", direction = Sort.Direction.DESC)
                                    Pageable pageable) {
        try {
            Page<DealListMapping> result = dealRepository.findAllBy(pageable);

            // 썸네일
            List<Object> outPut = new ArrayList<>();
//            int totalSize = Long.valueOf(result.getTotalElements()).intValue();
            int size = result.getNumberOfElements();
            int page = result.getPageable().getPageNumber();
//            System.out.println(result.getContent().size());
            Map<Long, String> map = new HashMap<>();
//            System.out.println("size: " + size);
            for (int i=0; i<size; i++) {
                String content = result.getContent().get(i).getContent();
//                System.out.println("dfd   " + content);
                String res = StringUtils.substringBetween(content, "img src=\"", "\" alt=\"image\" ");
//                System.out.println(res);
//                if (res != null) {
//                    res = res.replace("http://localhost:8080", "https://i8e102.p.ssafy.io/api");
//                }

                map.put(result.getContent().get(i).getDealId(), res);
            }
            List<Long> keyList = new ArrayList<>(map.keySet());
            keyList.sort((o1, o2) -> o2.compareTo(o1));
//            for (Long key : keyList) {
//                System.out.println(key + "번 : " +map.get(key));
//            }

            outPut.add(result);
            outPut.add(map);
            return new ResponseEntity<>(outPut, HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    // 게시판 리스트 - kind 필터링
    @GetMapping("/kind")
    public ResponseEntity DealListKind(@PageableDefault(size=9, sort = "time", direction = Sort.Direction.DESC)
                                   Pageable pageable, String kind) {
        try {
            Page<DealListMapping> result = dealRepository.findAllByKind(kind, pageable);

            // 썸네일
            List<Object> outPut = new ArrayList<>();
            int size = result.getNumberOfElements();
//            System.out.println(result.getContent().size());
            Map<Long, String> map = new HashMap<>();
            for (int i=0; i<size; i++) {
                String content = result.getContent().get(i).getContent();
//                System.out.println("dfd   " + content);
                String res = StringUtils.substringBetween(content, "img src=\"", "\" alt=\"image\" ");
//                System.out.println(res);
//                if (res != null) {
//                    res = res.replace("http://localhost:8080", "https://i8e102.p.ssafy.io/api");
//                }
                map.put(result.getContent().get(i).getDealId(), res);
            }
            List<Long> keyList = new ArrayList<>(map.keySet());
            keyList.sort((o1, o2) -> o2.compareTo(o1));
//            for (Long key : keyList) {
//                System.out.println(key + "번 : " +map.get(key));
//            }


            outPut.add(result);
            outPut.add(map);
            return new ResponseEntity<>(outPut, HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    // 앱용 게시판 검색
    @GetMapping("/app/search")
    public ResponseEntity findDealSearchApp(
//            @PathVariable("boardCode") int boardCode,
            @PageableDefault(size=10, sort = "time", direction = Sort.Direction.DESC)
            Pageable pageable, String keyword) {

        try {
            Page<DealListMapping> result = dealRepository.findByTitleContaining(keyword, pageable);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

    }
    // 앱용 게시판 리스트 - pagination
    @GetMapping("/app")
    public ResponseEntity AppDealList(@PageableDefault(size=10, sort = "time", direction = Sort.Direction.DESC)
                                   Pageable pageable) {
        try {
            Page<DealListMapping> result = dealRepository.findAllBy(pageable);

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    // 게시판 리스트 - kind 필터링
    @GetMapping("/app/kind")
    public ResponseEntity AppDealListKind(@PageableDefault(size=10, sort = "time", direction = Sort.Direction.DESC)
                                       Pageable pageable, String kind) {
        try {
            Page<DealListMapping> result = dealRepository.findAllByKind(kind, pageable);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    // 거래 게시판 글작성
    @PostMapping("/")           // 더미 게시판 생성 후 필요한 내용을 수정하여 보내주는 방법으로 변경됨에 따라 생성함수는 default 값만을 생성해냄
    public ResponseEntity InsertDeal(@RequestBody HashMap<String, Object> param) {
        try {
            // 토큰과 보드 코드가 필요함
            String token = (String) param.get("token");
            String id = securityService.getSubject(token);
            User user = userRepository.findById(id);
            String title = (String) param.get("title");
            String content = (String) param.get("content");

            String kind = (String) param.get("kind");
            String name = (String) param.get("name");
            Long price = Long.valueOf(String.valueOf(param.get("price")));

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String nowDateTime = now.format(dateTimeFormatter);
            LocalDateTime nowTime = LocalDateTime.parse(nowDateTime, dateTimeFormatter);

            Deal deal = Deal.builder()
                    .userId(user)
                    .kind(kind)
                    .title(title)
                    .content(content)
                    .name(name)
                    .price(price)
                    .onSale(true)
                    .visited(Long.parseLong("0"))
                    .time(nowTime)
                    .build();


            Long newDealId = dealRepository.save(deal).getDealId();

            // 이미지 태그만 따로 빼서 저장하기
            Deal newDeal = dealRepository.findByDealId(newDealId);
            while (true) { //이미지 태그가 더 없을 때까지 반복해서 모든 이미지 태그를 찾기
                String res = StringUtils.substringBetween(content, "img src=\"", "\" alt=\"image\" ");
                if (res == null) { // 더이상 이미지 태그가 없으면 끝
                    break;
                }
                System.out.println(res);
                String imgPath = StringUtils.substringAfter(res, "https://i8e102.p.ssafy.io/api/deal/imageDownload/");
                String extension = StringUtils.substringAfterLast(res, ".");

                UUID uuid = UUID.randomUUID(); // 랜덤 파일명
                DealImage dealImage = DealImage.builder()
                        .dealId(newDeal)
                        .name(uuid + "." + extension)
                        .path(imgPath)
                        .type(extension)
                        .build();

                dealImageRepository.save(dealImage);
                content = StringUtils.substringAfter(content, res); // 다음 이미지 태그를 찾기 위해 이번 이미지 태그를 뺌

            }
            return new ResponseEntity(HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    // 거래 게시판 글수정
    @PutMapping("/")        // 토큰과 거래 id를, 새로운 제목과 내용을 넣어야함
    public ResponseEntity UpdateDeal(@RequestBody HashMap<String, Object> param) {
        // 기존의 데이터 불러오기
        String token = (String) param.get("token");
        String id = securityService.getSubject(token);
        User user1 = userRepository.findById(id);

        Long dealId = Long.valueOf(String.valueOf(param.get("deal_id")));
        String title = (String) param.get("title");
        String content = (String) param.get("content");

        String kind = (String) param.get("kind");
        String name = (String) param.get("name");
        Long price = Long.valueOf(String.valueOf(param.get("price")));

        Deal originalDeal = dealRepository.findByDealId(dealId);
        User user2 = originalDeal.getUserId();

        if (!user1.getUserId().equals(user2.getUserId())){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "작성 유저가 아닙니다."
            );
        }

        originalDeal.setTitle(title);
        originalDeal.setContent(content);
        originalDeal.setKind(kind);
        originalDeal.setName(name);
        originalDeal.setPrice(price);

        // 수정한 데이터 업데이트
        try {
            dealRepository.save(originalDeal);
            return new ResponseEntity(HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

    }

    // 거래 게시판 글삭제
    @DeleteMapping("/{dealId}")
    public ResponseEntity DeleteDeal(@PathVariable Long dealId) {
        Deal deal = dealRepository.findByDealId(dealId);





        if (salesRepository.existsByDealId(deal)) {// <- 판매후에는 삭제 불가해야하므로 존재확인
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "판매 후에는 작성글을 지울 수 없습니다.");
        }

        try {

            dealVisitedRepository.deleteByDealPKDealId(dealId); // visit 삭제
            zzimRepository.deleteByDealPKDealId(dealId);// 찜목록 삭제
            dealImageRepository.deleteByDealId(dealRepository.findByDealId(dealId));// 이미지 삭제
            dealRepository.delete(deal);  // 게시글 삭제
            return new ResponseEntity(HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    // 판매 완료 시
    // sales_record에 추가, 모든 찜목록에서 제거
    @GetMapping("/soldOut")        // 보드 id
    public ResponseEntity SoldOut(Long dealId) {
        Deal originalDeal = dealRepository.findByDealId(dealId);

        originalDeal.setOnSale(false); // 판매완료
        SalesRecord salesRecord = SalesRecord.builder()
                .userId(originalDeal.getUserId())
                .dealId(originalDeal)
                .clearlyDeal(true)
                .build();

        // 수정한 데이터 업데이트
        try {
            salesRepository.save(salesRecord);
            dealRepository.save(originalDeal);
            zzimRepository.deleteByDealPKDealId(dealId);// 찜목록 삭제
            return new ResponseEntity(HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

    }

    // 찜목록에 이미 있으면 true, 없으면 false
    @GetMapping("/zzimCheck")
    public ResponseEntity CheckZzim(Long userId, Long dealId) {

        try {
            if ( zzimRepository.findByDealPKUserIdAndDealPKDealId(userId, dealId) != null) {
                return new ResponseEntity<>(true, HttpStatus.OK);
            }
            return new ResponseEntity<>(false, HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    // 찜목록 조회
    @GetMapping("/zzim")
    public ResponseEntity SelectZzim(Long userId) {

        try {
            List<ZzimList> zzimList = zzimRepository.findByDealPKUserId(userId);
            Map<Object, Object> map = new HashMap<>();
            map.put("userId", userId);

            for (int i=0; i<zzimList.size(); i++) {
                System.out.println(zzimList.get(i).getDealPK().getDealId());
                Deal deal = dealRepository.findByDealId(zzimList.get(i).getDealPK().getDealId());
                Map<String, Object> dealMap = new HashMap<>();
                dealMap.put("kind", deal.getKind());
                dealMap.put("title", deal.getTitle()); 
                dealMap.put("content", deal.getContent());
                dealMap.put("name", deal.getName());
                dealMap.put("price", deal.getPrice());
                dealMap.put("onSale", deal.isOnSale());
                map.put(deal.getDealId(), dealMap);
            }
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }



    // 찜목록 추가
    @GetMapping("/addZzim")
    public ResponseEntity AddZzim(Long dealId, Long userId) {
        DealPK dealPK = DealPK.builder()
                .dealId(dealId)
                .userId(userId)
                .build();
        ZzimList zzimList = ZzimList.builder()
                .dealPK(dealPK)
                .build();
        try {
            zzimRepository.save(zzimList);// 찜목록 추가
            return new ResponseEntity(HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    // 찜목록 삭제
    @DeleteMapping(value = "/zzim/{dealId}", headers = "userId")
    public ResponseEntity DeleteZzim(@PathVariable Long dealId, @RequestHeader("userId") Long userId) {

        try {
            zzimRepository.deleteByDealPKDealIdAndDealPKUserId(dealId, userId);// 찜목록 삭제
            return new ResponseEntity(HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }


    // 이미지 올리기
    @PostMapping("/imageUpload") // deal 이미지 올리기, form-data 형식
    public String saveDealImage(@RequestBody MultipartFile imageFile) throws Exception {
        String imagePath = null;
        String absolutePath = new File("").getAbsolutePath() + "/";
        String path = "images/deal";
        File file = new File(path);

        if (!file.exists()) {
            file.mkdirs();
        }

        if (!imageFile.isEmpty()) {
            String contentType = imageFile.getContentType();
            String originalFileExtension;
            if (ObjectUtils.isEmpty(contentType)) {
                throw new Exception("이미지 파일은 jpg, png 만 가능합니다.");
            } else {
                if (contentType.contains("image/jpeg")) {
                    originalFileExtension = ".jpg";
                } else if (contentType.contains("image/png")) {
                    originalFileExtension = ".png";
                } else {
                    throw new Exception("이미지 파일은 jpg, png 만 가능합니다.");
                }
            }
            UUID uuid = UUID.randomUUID(); // 랜덤 파일명
            imagePath = path + "/" + uuid + originalFileExtension;
            file = new File(absolutePath + imagePath);
            imageFile.transferTo(file);

        }
        else {
            throw new Exception("이미지 파일이 비어있습니다.");
        }
        return imagePath;
    }

    //     이미지 불러오기
    @GetMapping("/imageDownload/**")        // 중고거래 이미지 불러오기, imageDownload 뒤에 경로를 그대로 적어주면 된다.
    public ResponseEntity<byte[]> getDealImage(HttpServletRequest request){
        String filePath = request.getRequestURI().split(request.getContextPath() + "/imageDownload/")[1];

        File file=new File(filePath);
        ResponseEntity<byte[]> result=null;
        try {
            HttpHeaders headers=new HttpHeaders();
            headers.add("Content-Type", Files.probeContentType(file.toPath()));
            result=new ResponseEntity<>(FileCopyUtils.copyToByteArray(file),headers,HttpStatus.OK );
        }catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @GetMapping("/imageListDownload/")        // 중고거래 이미지 불러오기, 중고거래 id로 해당 보드의 모든 이미지들을 byte array형태로 불러온다.
    @ResponseBody
    public List<byte[]> getUserDealFileById(Long deal_id){
        Deal deal = dealRepository.findByDealId(deal_id);
        List<DealImage> dealImageList = dealImageRepository.findByDealId(deal);
        List<byte[]> returnList = new ArrayList<>();

        for (int i = 0; i < dealImageList.size(); i++){
            String filePath = dealImageList.get(i).getPath();
            File file=new File(filePath);
            byte[] result=null;
            try {
                result=FileCopyUtils.copyToByteArray(file);
                returnList.add(result);
            }catch (IOException e) {
                e.printStackTrace();
            }
        }

        return returnList;


    }



    // 아래는 앱에서 사용하기 위한 API
    @PostMapping("/app")
    public ResponseEntity InsertDealApp(MultipartHttpServletRequest param) {

        // 토큰과 보드 코드가 필요함
        String token = param.getParameter("token");
        String id = securityService.getSubject(token);
        User user = userRepository.findById(id);
        String title = param.getParameter("title");
        String content = param.getParameter("content");

        String kind = param.getParameter("kind");
        String name = param.getParameter("name");
        Long price = Long.valueOf(String.valueOf(param.getParameter("price")));

        //이미지 리스트
        List<MultipartFile> imgList = param.getFiles("images");

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String nowDateTime = now.format(dateTimeFormatter);
        LocalDateTime nowTime = LocalDateTime.parse(nowDateTime, dateTimeFormatter);

        Deal deal = Deal.builder()
                .userId(user)
                .kind(kind)
                .title(title)
                .content(content)
                .name(name)
                .price(price)
                .onSale(true)
                .visited(Long.parseLong("0"))
                .time(nowTime)
                .build();
        try {
            Deal saveDeal = dealRepository.save(deal);
            String absolutePath = new File("").getAbsolutePath() + "/";
            String path = "images/deal";

            File dir = new File(absolutePath + path);
            if(!dir.exists()){
                dir.mkdir();
            }

            for(MultipartFile img : imgList){
                String fileName = img.getOriginalFilename();

                UUID uuid = UUID.randomUUID();

                String extension = fileName.substring(fileName.lastIndexOf(".") + 1);

                String savingFileName = uuid + "." + extension;

                File destFile = new File(absolutePath + path + "/" + savingFileName);

                img.transferTo(destFile);

                DealImage dealImage = DealImage.builder().
                        dealId(saveDeal).
                        name(fileName).
                        path(path + "/" + savingFileName).
                        type(extension).
                        build();
                dealImageRepository.save(dealImage);
            }
            return new ResponseEntity(HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    // 거래 게시판 글수정
    @PutMapping("/app")
    public ResponseEntity UpdateDealApp(MultipartHttpServletRequest param) {

        // 토큰과 보드 코드가 필요함
        String token = param.getParameter("token");
        String id = securityService.getSubject(token);
        User user1 = userRepository.findById(id);
        String title = param.getParameter("title");
        String content = param.getParameter("content");
        Long dealId = Long.valueOf(String.valueOf(param.getParameter("deal_id")));
        String kind = param.getParameter("kind");
        String name = param.getParameter("name");
        Long price = Long.valueOf(String.valueOf(param.getParameter("price")));

        //이미지 리스트
        List<MultipartFile> imgList = param.getFiles("images");

        Deal originalDeal = dealRepository.findByDealId(dealId);
        User user2 = originalDeal.getUserId();

        if (!user1.getUserId().equals(user2.getUserId())){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "작성 유저가 아닙니다."
            );
        }
        dealImageRepository.deleteByDealId(originalDeal);

        originalDeal.setTitle(title);
        originalDeal.setContent(content);
        originalDeal.setKind(kind);
        originalDeal.setName(name);
        originalDeal.setPrice(price);


        try {
            Deal saveDeal = dealRepository.save(originalDeal);
            String absolutePath = new File("").getAbsolutePath() + "/";
            String path = "images/deal";

            for(MultipartFile img : imgList){
                String fileName = img.getOriginalFilename();

                UUID uuid = UUID.randomUUID();

                String extension = fileName.substring(fileName.lastIndexOf(".") + 1);

                String savingFileName = uuid + "." + extension;

                File destFile = new File(absolutePath + path + "/" + savingFileName);

                img.transferTo(destFile);

                DealImage dealImage = DealImage.builder().
                        dealId(saveDeal).
                        name(fileName).
                        path(path + "/" + savingFileName).
                        type(extension).
                        build();
                dealImageRepository.save(dealImage);
            }
            return new ResponseEntity(HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    // 거래 게시판 글삭제
    @DeleteMapping("/app/{dealId}")
    public ResponseEntity DeleteDealApp(@PathVariable Long dealId) {
        Deal deal = dealRepository.findByDealId(dealId);

        try {
            dealVisitedRepository.deleteByDealPKDealId(dealId); // visit 삭제
            zzimRepository.deleteByDealPKDealId(dealId);// 찜목록 삭제
            dealImageRepository.deleteByDealId(deal);
            dealRepository.delete(deal);  // 게시글 삭제
            return new ResponseEntity(HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }



}