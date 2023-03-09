package com.example.demo.controller;

import com.example.demo.domain.*;
import com.example.demo.mapping.BoardListMapping;
import com.example.demo.mapping.CommentMapping;
import com.example.demo.repository.*;
import com.example.demo.repository.BoardCodeRepository;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.SecurityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.digest.DigestUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.mindrot.jbcrypt.BCrypt;
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

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping(value = "/api/board")
@Log4j2
@CrossOrigin(
        "*"

//        allowCredentials = "true",
//        allowedHeaders = "*",
//        methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT,RequestMethod.HEAD,RequestMethod.OPTIONS}

)
public class BoardController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    BoardCodeRepository boardCodeRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    BoardVisitedRepository boardVisitedRepository;

    @Autowired
    LikeRepository likeRepository;

    @Autowired
    BoardImageRepository boardImageRepository;

    @Autowired
    SecurityService securityService; // jwt 토큰 사용


    // {userId} 유저가 작성한 모든 글
    @GetMapping("/test")
    public List<Board> findUserInfo(Long userId){
        User user = userRepository.findByUserId(userId);
        return boardRepository.findByUserId(user);
    }

    // 게시판 코드
    @GetMapping("/code")
    public ResponseEntity codeList() {
        try {
            return new ResponseEntity<>(boardCodeRepository.findAll(), HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    // 게시판 검색
    @GetMapping("/search") 
    public ResponseEntity findBoardSearch(
//            @PathVariable("boardCode") int boardCode,
            @PageableDefault(size=10, sort = "regTime", direction = Sort.Direction.DESC)
            Pageable pageable, String keyword, int boardCode) {
//        System.out.println("boardCode : " + boardCode);
//        System.out.println("page : " + pageable);
//        System.out.println("word : " + keyword);
        try {
            Page<BoardListMapping> result = boardRepository.findByBoardCodeAndTitleContaining(boardCode, keyword, pageable);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    // {boardId} 게시판 글 상세 내용 보기
    @GetMapping(value = "/{boardCode}/", headers = "userId")
    public ResponseEntity findBoardInfo(@PathVariable("boardCode") int boardCode, Long boardId,
                                        @RequestHeader("userId") Long userId) {
        try {
            Board result = boardRepository.findByBoardId(boardId);
            List<CommentMapping> comment = commentRepository.findAllByBoardIdOrderByTimeDesc(result);

            Map<String, Object> map = new HashMap<>();
            map.put("boardId", result.getBoardId());
            map.put("userId", result.getUserId().getUserId());
            map.put("userNickname", result.getUserId().getNickname());
            map.put("boardCode", result.getBoardCode());
            map.put("count", result.getCount());
            map.put("title", result.getTitle());
            map.put("content", result.getContent());
            map.put("visited", result.getVisited());
            map.put("likeCount", result.getLikeCount());
            map.put("hateCount", result.getHateCount());
            map.put("regTime", result.getRegTime());

            map.put("comment", comment);


            // boardVisited 확인
//            Long userId = Long.valueOf(1); // 로그인되어 있는 사용자의 userId

            BoardGoodPK boardGoodPK = BoardGoodPK.builder()
                    .boardId(boardId)
                    .userId(userId)
                    .build();
            BoardVisited boardVisited = boardVisitedRepository
                    .findByBoardGoodPKBoardIdAndBoardGoodPKUserId(boardId, userId);

            // 첫 방문인 경우 visited DB에 추가 AND board visited 증가
            if (boardVisited == null) {

                BoardVisited newBoardVisited = BoardVisited.builder()
                        .boardGoodPK(boardGoodPK)
                        .build();

                result = result.builder()
                        .boardId(result.getBoardId())
                        .userId(result.getUserId())
                        .boardCode(result.getBoardCode())
                        .count(result.getCount())
                        .title(result.getTitle())
                        .content(result.getContent())
                        .visited(result.getVisited()+1)
                        .likeCount(result.getLikeCount())
                        .hateCount(result.getHateCount())
                        .regTime(result.getRegTime())
                        .build();

//                System.out.println(result.getBoardId() + " " + result.getVisited() );
                boardVisitedRepository.save(newBoardVisited);
                boardRepository.save(result);
                map.put("visited", result.getVisited());
            }


            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    // 게시판 리스트 - pagination
    @GetMapping("/{boardCode}")
    public ResponseEntity BoardList(@PathVariable("boardCode") int boardCode,
                                    @PageableDefault(size=10, sort = "regTime", direction = Sort.Direction.DESC)
                                    Pageable pageable) {
        try {
            Page<BoardListMapping> result = boardRepository.findAllByBoardCode(pageable, boardCode);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }






    // 게시판 글작성
    @PostMapping("/")           // 더미 게시판 생성 후 필요한 내용을 수정하여 보내주는 방법으로 변경됨에 따라 생성함수는 default 값만을 생성해냄
    public ResponseEntity InsertBoard(@RequestBody HashMap<String, Object> param) {


        // 토큰과 보드 코드가 필요함

        String token = (String) param.get("token");
        String id = securityService.getSubject(token);
        User user = userRepository.findById(id);
//        Long userId = Long.valueOf(String.valueOf(param.get("user_id")));
//        String title = (String) param.get("title");
//        String content = (String) param.get("content");

//        System.out.println(param);
//        Long userId = Long.valueOf(String.valueOf(param.get("user_id")));
        String title = (String) param.get("title");
        String content = (String) param.get("content");

        int bc = Integer.parseInt(String.valueOf(param.get("board_code")));
//        int bc = Integer.parseInt(param.get("board_code"));




        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String nowDateTime = now.format(dateTimeFormatter);
        LocalDateTime nowTime = LocalDateTime.parse(nowDateTime, dateTimeFormatter);
//        System.out.println(nowTime);

        // BoardCode의 count 읽어와서 +1
        BoardCode boardCode = boardCodeRepository.findByCode(bc);
        Long cnt = boardCode.getCount() + 1;
        boardCode.builder()
                .count(cnt)
                .build();
        boardCodeRepository.save(boardCode);

        Board board = Board.builder()
                .userId(user)
                .boardCode(bc)
                .count(cnt)
                .title(title)
                .content(content)
//                .title("dummy_title")
//                .content("dummy_content")
                .visited(Long.parseLong("0"))
                .likeCount(Long.parseLong("0"))
                .hateCount(Long.parseLong("0"))
                .regTime(nowTime)
                .build();
        try {
            boardRepository.save(board);
            return new ResponseEntity(HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }


    // 앱 게시판 글작성
    @PostMapping("/app")
    public ResponseEntity InsertBoardApp(MultipartHttpServletRequest param) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            //키값과 파일 순서
            Map<String, String> imgMap = null;

            String absolutePath = new File("").getAbsolutePath() + "/";
            String path = "images/board";

            // 토큰과 보드 코드가 필요함
            String token = (String) param.getParameter("token");

            //이미지 리스트
            List<MultipartFile> imgList = param.getFiles("images");

            String preImgMap = param.getParameter("imgMap");
            try {//json 문자열 파싱
                imgMap = mapper.readValue(preImgMap, Map.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String id = securityService.getSubject(token);
            User user = userRepository.findById(id);

            String title = param.getParameter("title");
            String content = param.getParameter("content");

            //img src 교체작업
            Document doc = Jsoup.parse(content);
            Elements ele = doc.getAllElements();

            StringBuilder sb = new StringBuilder();
            List<String[]> imgNameList = new ArrayList<>();

            for (int i = 1; i < ele.size(); i++) {
                if (ele.get(i).tagName().equals("html") || ele.get(i).tagName().equals("head") || ele.get(i).tagName().equals("body"))
                    continue;
                //이미지인 경우에만 src 교체작업 수행
                if (ele.get(i).tagName().equals("img")) {
                    int imgNum = -1;
                    String src = ele.get(i).attr("src");
                    String rowNum = imgMap.get(src);
                    //맞는 이미지가 없다면 그대로 넣고 넘어감 (update 수행때 사용될것)
                    if (rowNum == null) {
                        sb.append(ele.get(i));
                        continue;
                    }
                    imgNum = Integer.parseInt(rowNum);

                    MultipartFile img = imgList.get(imgNum);

                    String fileName = img.getOriginalFilename();

                    UUID uuid = UUID.randomUUID();

                    String extension = fileName.substring(fileName.lastIndexOf(".") + 1);

                    String savingFileName = uuid + "." + extension;

                    File destFile = new File(absolutePath + path + "/" + savingFileName);

                    img.transferTo(destFile);

                    sb.append("<img src=\"" + "https://i8e102.p.ssafy.io:8080/api/board/imageDownload/" + path + "/" + savingFileName + "\">");

                    imgNameList.add(new String[]{uuid.toString(), path + "/" + savingFileName, extension});
                } else {
                    sb.append(ele.get(i));
                }
            }

            //콘텐츠 재정의
            System.out.println("재정의된 콘텐츠 : " + sb.toString());
            content = sb.toString();

            int bc = Integer.parseInt(String.valueOf(param.getParameter("board_code")));
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String nowDateTime = now.format(dateTimeFormatter);
            LocalDateTime nowTime = LocalDateTime.parse(nowDateTime, dateTimeFormatter);

            // BoardCode의 count 읽어와서 +1
            BoardCode boardCode = boardCodeRepository.findByCode(bc);
            Long cnt = boardCode.getCount() + 1;
            boardCode.builder()
                    .count(cnt)
                    .build();
            boardCodeRepository.save(boardCode);


            Board board = Board.builder()
                    .userId(user)
                    .boardCode(bc)
                    .count(cnt)
                    .title(title)
                    .content(content)
                    .visited(Long.parseLong("0"))
                    .likeCount(Long.parseLong("0"))
                    .hateCount(Long.parseLong("0"))
                    .regTime(nowTime)
                    .build();

            //게시글 번호 반환
            Board getboard = boardRepository.save(board);

            //이미지 테이블 저장
            for (String[] imgname : imgNameList) {

                final BoardImage boardImage = BoardImage.builder()

                        .boardId(getboard)
                        .name(imgname[0])
                        .path(imgname[1])
                        .type(imgname[2])
                        .build();
                boardImageRepository.save(boardImage);
            }
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }



    // 게시판 글수정
    @PutMapping("/")        // 토큰과 보드 id를, 새로운 제목과 내용을 넣어야함
    public ResponseEntity UpdateBoard(@RequestBody HashMap<String, Object> param) {
        // 기존의 데이터 불러오기
        String token = (String) param.get("token");
        String id = securityService.getSubject(token);
        User user1 = userRepository.findById(id);

        Long boardId = Long.valueOf(String.valueOf(param.get("board_id")));
//        Long userId = Long.valueOf(String.valueOf(param.get("user_id")));
//        int boardCode = Integer.parseInt(String.valueOf(param.get("board_code")));
        String title = (String) param.get("title");
        String content = (String) param.get("content");
//        Long likeCount = (Long) param.get("like");
//        Long hateCount = (Long) param.get("hate");
 

        Board originalBoard = boardRepository.findByBoardId(boardId);
        User user2 = originalBoard.getUserId();

        if (!user1.getUserId().equals(user2.getUserId())){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "작성 유저가 아닙니다."
            );
        }
//        originalBoard = originalBoard.builder()
//                .userId(originalBoard.getUserId())
//                .count((originalBoard.getCount()))
//                .title(title)
//                .content(content)
//                .boardCode(boardCode)
//                .visited((originalBoard.getVisited()))
//                .likeCount(originalBoard.getLikeCount())
//                .hateCount(originalBoard.getHateCount())
//                .regTime(originalBoard.getRegTime())
//                .build();
//        System.out.println(originalBoard.getTitle());


        originalBoard.setTitle(title);
        originalBoard.setContent(content);
//        originalBoard.setBoardCode(boardCode);
//        originalBoard.setTime(board.getTime());
//        originalBoard.setLikeCount(likeCount);
//        originalBoard.setHateCount(hateCount);

        // 수정한 데이터 업데이트
        try {
            boardRepository.save(originalBoard);
            return new ResponseEntity(HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

    }

    // 게시판 글수정
    @PutMapping("/app")
    public ResponseEntity UpdateBoardApp(MultipartHttpServletRequest param) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            //키값과 파일 순서
            Map<String, String> imgMap = null;

            String absolutePath = new File("").getAbsolutePath() + "/";
            String path = "images/board";

            // 기존의 데이터 불러오기
            String token = param.getParameter("token");
            String id = securityService.getSubject(token);
            User user1 = userRepository.findById(id);

            Long boardId = Long.parseLong(param.getParameter("board_id"));

            String title = param.getParameter("title");
            String content = param.getParameter("content");

            System.out.println("content : " + content);

            List<MultipartFile> imgList = param.getFiles("images");
            String preImgMap = param.getParameter("imgMap");
            try {//json 문자열 파싱
                imgMap = mapper.readValue(preImgMap, Map.class);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Board originalBoard = boardRepository.findByBoardId(boardId);
            User user2 = originalBoard.getUserId();

            if (!user1.getUserId().equals(user2.getUserId())) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "작성 유저가 아닙니다."
                );
            }
            Board board = boardRepository.findByBoardId(boardId);
            //기존에 있던 이미지 경로 리스트를 받아온다.
            List<BoardImage> delImgPathList = boardImageRepository.findByBoardId(board);
            //일단 이미지 테이블 전부 지워줌
            boardImageRepository.deleteByBoardId(board);

            //content 교체 처리 하며 imgMap에 존재하지 않는 태그의 경우 이미 있는 이미지이므로 preImgNameList에서 제거해주고 넘어감

            Document doc = Jsoup.parse(content);
            Elements ele = doc.getAllElements();

            StringBuilder sb = new StringBuilder();
            List<String[]> imgNameList = new ArrayList<>();

            for (int i = 1; i < ele.size(); i++) {
//                if (imgMap == null) break;
                if (ele.get(i).tagName().equals("html") || ele.get(i).tagName().equals("head") || ele.get(i).tagName().equals("body"))
                    continue;
                //이미지인 경우에만 src 교체작업 수행
                if (ele.get(i).tagName().equals("img")) {
                    int imgNum = -1;
                    String src = ele.get(i).attr("src");
                    System.out.println(src);

                    String rowNum = imgMap.get(src);
                    //맞는 이미지가 없다면 그대로 넣고 넘어감 여긴 수정되지 않은 이미지인 경우임 삭제할 이미지 목록에서 지워야함
                    if (rowNum == null) {

                        String[] names = ele.get(i).attr("src").split("/");

                        for (int j = 0; j < delImgPathList.size(); j++) {
                            String[] dels = delImgPathList.get(j).getPath().split("/");
                            //삭제할 이미지 목록에서 해당 이미지를 제거한다. (수정되지 않은 이미지)
                            if (dels[dels.length - 1].equals(names[names.length - 1])) {
                                delImgPathList.remove(j);//제거하고 종료
                                break;
                            }
                        }

                        sb.append(ele.get(i));
                        continue;
                    }
                    imgNum = Integer.parseInt(rowNum);

                    MultipartFile img = imgList.get(imgNum);

                    String fileName = img.getOriginalFilename();

                    UUID uuid = UUID.randomUUID();

                    String extension = fileName.substring(fileName.lastIndexOf(".") + 1);

                    String savingFileName = uuid + "." + extension;

                    File destFile = new File(absolutePath + path + "/" + savingFileName);

                    img.transferTo(destFile);

                    sb.append("<img src=\"" + "https://i8e102.p.ssafy.io:8080/api/board/imageDownload/" + path + "/" + savingFileName + "\">");

                    imgNameList.add(new String[]{uuid.toString(), path + "/" + savingFileName, extension});
                } else {
                    sb.append(ele.get(i));
                }

            }

            //콘텐츠 재정의
            System.out.println("재정의된 콘텐츠 : " + sb.toString());
            content = sb.toString();

            //실제 이미지 제거 (delImgPathList에 남아있는 이미지만 삭제)

            //폴더 내 파일 삭제
            for(BoardImage fileUrl : delImgPathList) {
                File file = new File(absolutePath + fileUrl.getPath());
                if(file.exists()) {
                    file.delete();
                }
            }

            //새로 추가된 이미지만 path에 저장

            //이미지 테이블 저장
            for (String[] imgname : imgNameList) {

                final BoardImage boardImage = BoardImage.builder()

                        .boardId(originalBoard)
                        .name(imgname[0])
                        .path(imgname[1])
                        .type(imgname[2])
                        .build();
                boardImageRepository.save(boardImage);
            }


            originalBoard.setTitle(title);
            originalBoard.setContent(content);


            // 수정한 데이터 업데이트
            try {
                boardRepository.save(originalBoard);
                return new ResponseEntity(HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity(HttpStatus.OK);
    }


    // 게시판 글삭제
    @DeleteMapping("/{boardId}")
    public ResponseEntity DeleteBoard(@PathVariable Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(IllegalArgumentException::new);


        try {
            boardVisitedRepository.deleteByBoardGoodPKBoardId(boardId); // visit 삭제
            likeRepository.deleteByBoardGoodPKBoardId(boardId);// like 삭제
            commentRepository.deleteByBoardId(board); // 댓글 삭제
            boardRepository.delete(board);  // 게시글 삭제 -> 이미지 삭제
            return new ResponseEntity(HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }


    // 게시판 글삭제
    @DeleteMapping("/app/{boardId}")
    public ResponseEntity DeleteBoardApp(@PathVariable Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(IllegalArgumentException::new);
        System.out.println(boardId);

        try {
            String absolutePath = new File("").getAbsolutePath() + "/";
            List<BoardImage> delImgPathList = boardImageRepository.findByBoardId(board);
            System.out.println(delImgPathList.toString());
            //먼저 이미지 제거
            for(BoardImage fileUrl : delImgPathList) {
                File file = new File(absolutePath + fileUrl.getPath());
                if(file.exists()) {
                    file.delete();
                }
            }

            boardVisitedRepository.deleteByBoardGoodPKBoardId(boardId); // visit 삭제
            likeRepository.deleteByBoardGoodPKBoardId(boardId);// like 삭제
            commentRepository.deleteByBoardId(board); // 댓글 삭제
            boardImageRepository.deleteByBoardId(board); //이미지 삭제
            boardRepository.delete(board);  // 게시글 삭제
            return new ResponseEntity(HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    // 이미지 올리기
    @PostMapping("/imageUpload")        // board 이미지 올리기, form-data 형식
    public String saveBoardImage(MultipartFile imageFile) throws Exception {
        String imagePath = null;
        String absolutePath = new File("").getAbsolutePath() + "/";
//        Long longBoardId = Long.parseLong(board_id);        // 보드 id 를 롱으로 미리 바꿔둔다.
//        Board board = boardRepository.findByBoardId(longBoardId); // 저장할 보드를 미리 가져온다.
        String path = "images/board";
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



//            final BoardImage boardImage = BoardImage.builder()
//
//                    .boardId(board)
//                    .name(uuid.toString())
//                    .path(imagePath)
//                    .type(originalFileExtension)
//                    .build();
//
//            boardImageRepository.save(boardImage);

        }
        else {
            throw new Exception("이미지 파일이 비어있습니다.");
        }
//        System.out.println(longBoardId);
//        return boardImageRepository.findByBoardId(board);
        return imagePath;
    }

//     이미지 불러오기
    @GetMapping("/imageDownload/**")        // 보드 이미지 불러오기, imageDownload 뒤에 경로를 그대로 적어주면 된다.
    @ResponseBody
    public ResponseEntity<byte[]> getBoardImageFile(HttpServletRequest request){
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



    @GetMapping("/imageListDownload/")        // 보드 이미지 불러오기, 보드 id로 해당 보드의 모든 이미지들을 byte array형태로 불러온다.
    @ResponseBody
    public List<byte[]> getUserBoardFileById(Long board_id){
        Board board = boardRepository.findByBoardId(board_id);
        List<BoardImage> boardImageList = boardImageRepository.findByBoardId(board);
        List<byte[]> returnList = new ArrayList<>();

        for (int i = 0; i < boardImageList.size(); i++){
            String filePath = boardImageList.get(i).getPath();
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




}