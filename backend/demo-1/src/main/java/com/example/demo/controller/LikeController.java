package com.example.demo.controller;

import com.example.demo.domain.Board;
import com.example.demo.domain.BoardGood;
import com.example.demo.domain.BoardGoodPK;
import com.example.demo.domain.Comment;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.LikeRepository;
import com.example.demo.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@Log4j2
@RequestMapping(value = "/api/board/like")
@CrossOrigin(
        "*"
        // localhost:5500 과 127.0.0.1 구분

//        allowCredentials = "true",
//        allowedHeaders = "*",
//        methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT,RequestMethod.HEAD,RequestMethod.OPTIONS}

)
public class LikeController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    LikeRepository likeRepository;

    // 좋아요 확인
    @GetMapping("/")
    public ResponseEntity CheckBoardLike(Long boardId, Long userId) {

        try {
//            Board board = boardRepository.findByBoardId(boardId);
            BoardGood chk = likeRepository.findByBoardGoodPKBoardIdAndBoardGoodPKUserId(boardId, userId);

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
            Long boardId = param.get("board_id");
            Long userId = param.get("user_id");

            Board board = boardRepository.findByBoardId(boardId);
            BoardGood chk = likeRepository.findByBoardGoodPKBoardIdAndBoardGoodPKUserId(boardId, userId);

            BoardGoodPK BGPK = BoardGoodPK.builder()
                    .boardId(boardId)
                    .userId(userId)
                    .build();
            if (chk == null) {
                chk = BoardGood.builder()
                        .boardGoodPK(BGPK)
                        .selected(true)
                        .build();
                board.setLikeCount(board.getLikeCount()+1); // 좋아요 1 증가
            } else if (chk.isSelected()) {
                chk.setSelected(false);
                board.setLikeCount(board.getLikeCount()-1); // 좋아요 1 감소
            } else if (!chk.isSelected()){
                chk.setSelected(true);
                board.setLikeCount(board.getLikeCount()+1); // 좋아요 1 증가
            }
            likeRepository.save(chk);
            boardRepository.save(board);
            return new ResponseEntity(HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }


}