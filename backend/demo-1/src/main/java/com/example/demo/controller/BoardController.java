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
    SecurityService securityService; // jwt ?????? ??????


    // {userId} ????????? ????????? ?????? ???
    @GetMapping("/test")
    public List<Board> findUserInfo(Long userId){
        User user = userRepository.findByUserId(userId);
        return boardRepository.findByUserId(user);
    }

    // ????????? ??????
    @GetMapping("/code")
    public ResponseEntity codeList() {
        try {
            return new ResponseEntity<>(boardCodeRepository.findAll(), HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    // ????????? ??????
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

    // {boardId} ????????? ??? ?????? ?????? ??????
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


            // boardVisited ??????
//            Long userId = Long.valueOf(1); // ??????????????? ?????? ???????????? userId

            BoardGoodPK boardGoodPK = BoardGoodPK.builder()
                    .boardId(boardId)
                    .userId(userId)
                    .build();
            BoardVisited boardVisited = boardVisitedRepository
                    .findByBoardGoodPKBoardIdAndBoardGoodPKUserId(boardId, userId);

            // ??? ????????? ?????? visited DB??? ?????? AND board visited ??????
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

    // ????????? ????????? - pagination
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






    // ????????? ?????????
    @PostMapping("/")           // ?????? ????????? ?????? ??? ????????? ????????? ???????????? ???????????? ???????????? ???????????? ?????? ??????????????? default ????????? ????????????
    public ResponseEntity InsertBoard(@RequestBody HashMap<String, Object> param) {


        // ????????? ?????? ????????? ?????????

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

        // BoardCode??? count ???????????? +1
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


    // ??? ????????? ?????????
    @PostMapping("/app")
    public ResponseEntity InsertBoardApp(MultipartHttpServletRequest param) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            //????????? ?????? ??????
            Map<String, String> imgMap = null;

            String absolutePath = new File("").getAbsolutePath() + "/";
            String path = "images/board";

            // ????????? ?????? ????????? ?????????
            String token = (String) param.getParameter("token");

            //????????? ?????????
            List<MultipartFile> imgList = param.getFiles("images");

            String preImgMap = param.getParameter("imgMap");
            try {//json ????????? ??????
                imgMap = mapper.readValue(preImgMap, Map.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String id = securityService.getSubject(token);
            User user = userRepository.findById(id);

            String title = param.getParameter("title");
            String content = param.getParameter("content");

            //img src ????????????
            Document doc = Jsoup.parse(content);
            Elements ele = doc.getAllElements();

            StringBuilder sb = new StringBuilder();
            List<String[]> imgNameList = new ArrayList<>();

            for (int i = 1; i < ele.size(); i++) {
                if (ele.get(i).tagName().equals("html") || ele.get(i).tagName().equals("head") || ele.get(i).tagName().equals("body"))
                    continue;
                //???????????? ???????????? src ???????????? ??????
                if (ele.get(i).tagName().equals("img")) {
                    int imgNum = -1;
                    String src = ele.get(i).attr("src");
                    String rowNum = imgMap.get(src);
                    //?????? ???????????? ????????? ????????? ?????? ????????? (update ????????? ????????????)
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

            //????????? ?????????
            System.out.println("???????????? ????????? : " + sb.toString());
            content = sb.toString();

            int bc = Integer.parseInt(String.valueOf(param.getParameter("board_code")));
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String nowDateTime = now.format(dateTimeFormatter);
            LocalDateTime nowTime = LocalDateTime.parse(nowDateTime, dateTimeFormatter);

            // BoardCode??? count ???????????? +1
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

            //????????? ?????? ??????
            Board getboard = boardRepository.save(board);

            //????????? ????????? ??????
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



    // ????????? ?????????
    @PutMapping("/")        // ????????? ?????? id???, ????????? ????????? ????????? ????????????
    public ResponseEntity UpdateBoard(@RequestBody HashMap<String, Object> param) {
        // ????????? ????????? ????????????
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
                    HttpStatus.BAD_REQUEST, "?????? ????????? ????????????."
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

        // ????????? ????????? ????????????
        try {
            boardRepository.save(originalBoard);
            return new ResponseEntity(HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

    }

    // ????????? ?????????
    @PutMapping("/app")
    public ResponseEntity UpdateBoardApp(MultipartHttpServletRequest param) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            //????????? ?????? ??????
            Map<String, String> imgMap = null;

            String absolutePath = new File("").getAbsolutePath() + "/";
            String path = "images/board";

            // ????????? ????????? ????????????
            String token = param.getParameter("token");
            String id = securityService.getSubject(token);
            User user1 = userRepository.findById(id);

            Long boardId = Long.parseLong(param.getParameter("board_id"));

            String title = param.getParameter("title");
            String content = param.getParameter("content");

            System.out.println("content : " + content);

            List<MultipartFile> imgList = param.getFiles("images");
            String preImgMap = param.getParameter("imgMap");
            try {//json ????????? ??????
                imgMap = mapper.readValue(preImgMap, Map.class);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Board originalBoard = boardRepository.findByBoardId(boardId);
            User user2 = originalBoard.getUserId();

            if (!user1.getUserId().equals(user2.getUserId())) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "?????? ????????? ????????????."
                );
            }
            Board board = boardRepository.findByBoardId(boardId);
            //????????? ?????? ????????? ?????? ???????????? ????????????.
            List<BoardImage> delImgPathList = boardImageRepository.findByBoardId(board);
            //?????? ????????? ????????? ?????? ?????????
            boardImageRepository.deleteByBoardId(board);

            //content ?????? ?????? ?????? imgMap??? ???????????? ?????? ????????? ?????? ?????? ?????? ?????????????????? preImgNameList?????? ??????????????? ?????????

            Document doc = Jsoup.parse(content);
            Elements ele = doc.getAllElements();

            StringBuilder sb = new StringBuilder();
            List<String[]> imgNameList = new ArrayList<>();

            for (int i = 1; i < ele.size(); i++) {
//                if (imgMap == null) break;
                if (ele.get(i).tagName().equals("html") || ele.get(i).tagName().equals("head") || ele.get(i).tagName().equals("body"))
                    continue;
                //???????????? ???????????? src ???????????? ??????
                if (ele.get(i).tagName().equals("img")) {
                    int imgNum = -1;
                    String src = ele.get(i).attr("src");
                    System.out.println(src);

                    String rowNum = imgMap.get(src);
                    //?????? ???????????? ????????? ????????? ?????? ????????? ?????? ???????????? ?????? ???????????? ????????? ????????? ????????? ???????????? ????????????
                    if (rowNum == null) {

                        String[] names = ele.get(i).attr("src").split("/");

                        for (int j = 0; j < delImgPathList.size(); j++) {
                            String[] dels = delImgPathList.get(j).getPath().split("/");
                            //????????? ????????? ???????????? ?????? ???????????? ????????????. (???????????? ?????? ?????????)
                            if (dels[dels.length - 1].equals(names[names.length - 1])) {
                                delImgPathList.remove(j);//???????????? ??????
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

            //????????? ?????????
            System.out.println("???????????? ????????? : " + sb.toString());
            content = sb.toString();

            //?????? ????????? ?????? (delImgPathList??? ???????????? ???????????? ??????)

            //?????? ??? ?????? ??????
            for(BoardImage fileUrl : delImgPathList) {
                File file = new File(absolutePath + fileUrl.getPath());
                if(file.exists()) {
                    file.delete();
                }
            }

            //?????? ????????? ???????????? path??? ??????

            //????????? ????????? ??????
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


            // ????????? ????????? ????????????
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


    // ????????? ?????????
    @DeleteMapping("/{boardId}")
    public ResponseEntity DeleteBoard(@PathVariable Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(IllegalArgumentException::new);


        try {
            boardVisitedRepository.deleteByBoardGoodPKBoardId(boardId); // visit ??????
            likeRepository.deleteByBoardGoodPKBoardId(boardId);// like ??????
            commentRepository.deleteByBoardId(board); // ?????? ??????
            boardRepository.delete(board);  // ????????? ?????? -> ????????? ??????
            return new ResponseEntity(HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }


    // ????????? ?????????
    @DeleteMapping("/app/{boardId}")
    public ResponseEntity DeleteBoardApp(@PathVariable Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(IllegalArgumentException::new);
        System.out.println(boardId);

        try {
            String absolutePath = new File("").getAbsolutePath() + "/";
            List<BoardImage> delImgPathList = boardImageRepository.findByBoardId(board);
            System.out.println(delImgPathList.toString());
            //?????? ????????? ??????
            for(BoardImage fileUrl : delImgPathList) {
                File file = new File(absolutePath + fileUrl.getPath());
                if(file.exists()) {
                    file.delete();
                }
            }

            boardVisitedRepository.deleteByBoardGoodPKBoardId(boardId); // visit ??????
            likeRepository.deleteByBoardGoodPKBoardId(boardId);// like ??????
            commentRepository.deleteByBoardId(board); // ?????? ??????
            boardImageRepository.deleteByBoardId(board); //????????? ??????
            boardRepository.delete(board);  // ????????? ??????
            return new ResponseEntity(HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    // ????????? ?????????
    @PostMapping("/imageUpload")        // board ????????? ?????????, form-data ??????
    public String saveBoardImage(MultipartFile imageFile) throws Exception {
        String imagePath = null;
        String absolutePath = new File("").getAbsolutePath() + "/";
//        Long longBoardId = Long.parseLong(board_id);        // ?????? id ??? ????????? ?????? ????????????.
//        Board board = boardRepository.findByBoardId(longBoardId); // ????????? ????????? ?????? ????????????.
        String path = "images/board";
        File file = new File(path);

        if (!file.exists()) {
            file.mkdirs();
        }

        if (!imageFile.isEmpty()) {
            String contentType = imageFile.getContentType();
            String originalFileExtension;
            if (ObjectUtils.isEmpty(contentType)) {
                throw new Exception("????????? ????????? jpg, png ??? ???????????????.");
            } else {
                if (contentType.contains("image/jpeg")) {
                    originalFileExtension = ".jpg";
                } else if (contentType.contains("image/png")) {
                    originalFileExtension = ".png";
                } else {
                    throw new Exception("????????? ????????? jpg, png ??? ???????????????.");
                }
            }
            UUID uuid = UUID.randomUUID(); // ?????? ?????????
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
            throw new Exception("????????? ????????? ??????????????????.");
        }
//        System.out.println(longBoardId);
//        return boardImageRepository.findByBoardId(board);
        return imagePath;
    }

//     ????????? ????????????
    @GetMapping("/imageDownload/**")        // ?????? ????????? ????????????, imageDownload ?????? ????????? ????????? ???????????? ??????.
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



    @GetMapping("/imageListDownload/")        // ?????? ????????? ????????????, ?????? id??? ?????? ????????? ?????? ??????????????? byte array????????? ????????????.
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