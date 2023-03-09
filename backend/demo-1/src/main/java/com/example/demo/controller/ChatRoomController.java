package com.example.demo.controller;

import com.example.demo.domain.*;

import com.example.demo.repository.*;
import com.example.demo.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RequiredArgsConstructor
@Controller
@CrossOrigin("*")
@RequestMapping("/api/chat")
public class ChatRoomController {

    private final ChattingRoomRepository chattingRoomRepository;

    private final ChattingRoomUserRepository chattingRoomUserRepository;
    @Autowired
    UserRepository userRepository; // 유저 검색
    @Autowired
    SecurityService securityService; // jwt 토큰 사용
    private final LikeRepository likeRepository;
    private final ChattingMessageRepository chattingMessageRepository;
    private final ReportRepository reportRepository;

//    @GetMapping("/room")        // 채팅방 초기화면을 보여주는 API, 우리는 모임방과 연결되니 필요없을 듯 하다.
//    public String rooms(Model model) {
//        return "/chat/room";
//    }

    @GetMapping("/rooms")           // 모든 채팅방을 리턴하는 API
    @ResponseBody
    public List<ChattingRoom> room() {
        return chattingRoomRepository.findAll();
    }


    @GetMapping("/rooms/user")           // 유저가 포함된 채팅방을 모두 반환
    @ResponseBody
    public List<ChattingRoomUser> roomSpecificUser(@RequestHeader Map<String, Object> requestHeader) {

        String id = securityService.getSubject((String) requestHeader.get("token"));
        User user = userRepository.findById(id);
        return chattingRoomUserRepository.findByUserId(user.getUserId());
    }
//    public List<ChattingRoom> room() {
//        return chattingRoomRepository.findAll();
//    }


    @GetMapping("/rooms/user/nameAndId")    // 유저가 포함된 채팅방을 방 ID와 이름, 알람여부 반환
    @ResponseBody
    public List<HashMap<String, Object>> roomSpecificUserNameId(@RequestHeader Map<String, Object> requestHeader) {

        String id = securityService.getSubject((String) requestHeader.get("token"));
        User user = userRepository.findById(id);
        List<HashMap<String, Object>> returnList = new ArrayList<>();
        List<ChattingRoomUser> rooms = chattingRoomUserRepository.findByUserId(user.getUserId(), Sort.by(Sort.Direction.DESC, "recentChattingTime"));
        for (int i = 0; i < rooms.size(); i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("chattingRoomId", rooms.get(i).getChattingRoomId());
            map.put("name", findRoomNameById(rooms.get(i).getChattingRoomId()));
            map.put("alarm", rooms.get(i).isAlarm());
            ChattingRoom chattingRoom = chattingRoomRepository.findRoomByRoomId(rooms.get(i).getChattingRoomId());
            map.put("type", chattingRoom.getRoomType());
//            if(rooms.get(i).isAlarm()){
//                returnList.add(0, map);                 // 새로운 메시지가 들어온 채팅방이면 제일 앞으로 댕겨온다.
//            } else {
//        }
            // 가장 최근 채팅을 불러오자
            List<ChattingMessage> chattingMessageList = chattingMessageRepository.findByRoomId(rooms.get(i).getChattingRoomId(), Sort.by(Sort.Direction.DESC, "sendTime"));
            if (chattingMessageList.size() > 0) {
                map.put("recent_chatting_sender_nickname", chattingMessageList.get(0).getSenderNickName());
                map.put("recent_chatting_sender_id", chattingMessageList.get(0).getSender());
                map.put("recent_chatting_message", chattingMessageList.get(0).getMessage());
                map.put("recent_chatting_time", chattingMessageList.get(0).getSendTime());
            }


            returnList.add(map);
        }
        return returnList;
    }

    @GetMapping("/rooms/user/alarm")    // 유저가 포함된 채팅방에 알람이 떴는지를 확인
    @ResponseBody
    public boolean roomUserAlarmCheck(@RequestHeader Map<String, Object> requestHeader) {
        String id = securityService.getSubject((String) requestHeader.get("token"));
        User user = userRepository.findById(id);

        List<ChattingRoomUser> rooms = chattingRoomUserRepository.findByUserId(user.getUserId());
        for (int i = 0; i < rooms.size(); i++){
            if (rooms.get(i).isAlarm()){
                return true;
            }
        }
        return false;
    }



    @GetMapping("/findRoomNameById")    // 채팅방 번호로 채팅방 이름을 찾는 함수
    @ResponseBody
    public String findRoomNameById(String roomId) {
        return chattingRoomRepository.findRoomByRoomId(roomId).getName();
    }

    @GetMapping("/getRoomUsers")        // 특정 채팅방 안의 유저들을 모두 닉네임들의 배열 형태로 반환
    @ResponseBody
    public List<HashMap> getRoomUsers(String roomId){
        List<ChattingRoomUser> roomUserList = chattingRoomUserRepository.findByChattingRoomId(roomId);
        List<HashMap> returnList = new ArrayList<>();
        for (int i = 0; i < roomUserList.size(); i++){
            HashMap<String, Object> map = new HashMap<String,Object>();
            User user = userRepository.findById(roomUserList.get(i).getUserId()).orElse(null);
            if (user != null) {
                map.put("userId", user.getUserId());
                map.put("nickname", user.getNickname());
//                returnList.add(user.getNickname());
            } else {
//                returnList.add("unknown");
                map.put("userId", "unknown");
                map.put("nickname", "unknown");
            }
            returnList.add(map);
        }
        return returnList;
    }


    @PostMapping("/roomForCommunity")           // 채팅방을 생성하는 API, 생성된 채팅방 형태 반환
    @ResponseBody
    public ChattingRoom createRoomForCommunity(@RequestBody HashMap<String, Object> param) {    // 모임용 채팅방 생성

        String token = (String) param.get("token");
        String id = securityService.getSubject(token);


        User user = userRepository.findById(id);
        UUID uuid = UUID.randomUUID();
//        return chattingRoomRepository.createChatRoom(uuid, param.get("name"));
        String new_room_name = (String) param.get("name");
//        Integer new_room_type = (Integer) param.get("room_type");
        ChattingRoom chattingRoom = ChattingRoom.builder()
                .name(new_room_name)
                .roomId(uuid.toString())
                .roomType(99)   // 커뮤니티 룸타입은 99로 넣자
                .build();
        chattingRoomRepository.save(chattingRoom);

        ChattingRoomUser chattingRoomUser = ChattingRoomUser.builder()
                .userId(user.getUserId())
                .chattingRoomId(uuid.toString())
                .build();
        chattingRoomUserRepository.save(chattingRoomUser);

        return chattingRoom;
    }

    @PostMapping("/roomForDeal")           // 채팅방을 생성하는 API, 생성된 채팅방 형태 반환
    @ResponseBody
    public ChattingRoom createRoomForDeal(@RequestBody HashMap<String, Object> param) {    // 중고거래용 채팅방 생성

        String token = (String) param.get("token"); // 자신도 들어감
        String id = securityService.getSubject(token);
        User user = userRepository.findById(id);
        UUID uuid = UUID.randomUUID();
//        return chattingRoomRepository.createChatRoom(uuid, param.get("name"));
        String new_room_name = (String) param.get("name");                  // 채팅방 이름 설정
//        Integer new_room_type = (Integer) param.get("room_type");

        String opposite_user_nickname = (String) param.get("opposite_nickname");
        User opposite_user = userRepository.findByNickname(opposite_user_nickname);


        // 먼저 두 사람이 공통으로 있는 1대1 채팅방이 있는지 확인하자
        List<ChattingRoomUser> buyer = chattingRoomUserRepository.findByUserId(user.getUserId());
        List<ChattingRoomUser> seller = chattingRoomUserRepository.findByUserId(opposite_user.getUserId());

        // 두 사람이 속한 채팅방의 id를 뽑아보자
        List<String> buyer_chatting_room_id = new ArrayList<>();
        List<String> seller_chatting_room_id = new ArrayList<>();

        for (int i = 0; i < buyer.size(); i++) {
            buyer_chatting_room_id.add(buyer.get(i).getChattingRoomId());

        }

        for (int j = 0; j < seller.size(); j++) {
            seller_chatting_room_id.add(seller.get(j).getChattingRoomId());

        }
        // set으로 공통된 부분만 찾음
        Set<String> shareChatting = new HashSet<>(buyer_chatting_room_id);
        shareChatting.retainAll(seller_chatting_room_id);
        List<String> shareChattingList = new ArrayList<>(shareChatting);
//        System.out.println("--------------");
//        System.out.println(buyer_chatting_room_id);
//        System.out.println(seller_chatting_room_id);
//        System.out.println(shareChatting);
//
//        System.out.println("--------------");

        // 공통된 방이 있을 경우 그것이 1대1 채팅방인지 확인하여 맞다면 그 채팅방 정보를 return
        if(shareChatting.size() > 0){
            for (int k = 0; k < shareChattingList.size(); k++){
                ChattingRoom foundChattingRoom = chattingRoomRepository.findRoomByRoomId(shareChattingList.get(k));
                if (foundChattingRoom.getRoomType() == 1){
                    return foundChattingRoom;
                }
            }
        }

        ChattingRoom chattingRoom = ChattingRoom.builder()
                .name(new_room_name)
                .roomId(uuid.toString())
                .roomType(1)        // 중고거래용 룸타입은 1로 넣자
                .build();
        chattingRoomRepository.save(chattingRoom);

        ChattingRoomUser chattingRoomUser = ChattingRoomUser.builder()      // 자기 자신을 만들어진 채팅방에 매핑
                .userId(user.getUserId())
                .chattingRoomId(uuid.toString())
                .build();
        chattingRoomUserRepository.save(chattingRoomUser);

        ChattingRoomUser chattingRoomUser2 = ChattingRoomUser.builder()     // 상대방도 채팅방에 매핑해서 넣기
                .userId(opposite_user.getUserId())
                .chattingRoomId(uuid.toString())
                .build();
        chattingRoomUserRepository.save(chattingRoomUser2);


        return chattingRoom;
    }

    @PostMapping("/enterUserRoom")                      // 특정 유저를 방으로 넣기 위한 API, 채팅방 생성시 상대방을 집어넣거나, 다른 사람을 초대할때 사용
    @ResponseBody
    public ChattingRoomUser enterUserRoom(@RequestBody HashMap<String, Object> param) {
        String nickname = (String) param.get("nickname");
        User user = userRepository.findByNickname(nickname);

        String roomId = (String) param.get("roomId");

        System.out.println(chattingRoomUserRepository.existsByUserIdAndChattingRoomId(user.getUserId(), roomId));
        if (!chattingRoomUserRepository.existsByUserIdAndChattingRoomId(user.getUserId(), roomId)) {
            ChattingRoomUser chattingRoomUser = ChattingRoomUser.builder()
                    .userId(user.getUserId())
                    .chattingRoomId(roomId)
                    .build();

            chattingRoomUserRepository.save(chattingRoomUser);
            return chattingRoomUser;
        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "이미 채팅방에 존재하는 유저입니다."
            );
        }
    }

    @DeleteMapping("/roomOut")                  // 유저가 방에서 나갈 때, 또는 방에서 내보내질때 사용
    @ResponseBody
    public ResponseEntity roomOut(@RequestBody HashMap<String, Object> param) {
        String nickname = (String) param.get("nickname");
        User user = userRepository.findByNickname(nickname);

        String roomId = (String) param.get("roomId");
        try {
            List<ChattingRoomUser> roomUserList = chattingRoomUserRepository.findByUserId(user.getUserId());    // 해당 유저가 포함된 채팅방 불러오기
            for (int i = 0; i < roomUserList.size(); i++){
                if (roomUserList.get(i).getChattingRoomId().equals(roomId)){    // 그 중 특정 roomId로 나가고자 하는 방 식별
                    chattingRoomUserRepository.delete(roomUserList.get(i));
                }
            }
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "채팅방에 있는 유저가 아닙니다."
            );
        }
    }

//    @GetMapping("/room/enter/{roomId}")     // 채팅방 입장 API,
//    public String roomDetail(Model model, @PathVariable String roomId) {
//        model.addAttribute("roomId", roomId);
//        return "/chat/roomdetail";          // roomdetail 화면을 보여준다는 의미, 이 부분은 마음껏 수정해서 쓰면 될듯
//    }

    @GetMapping("/room/{roomId}")       // 특정 채팅방 정보조회
    @ResponseBody
    public ChattingRoom roomInfo(@PathVariable String roomId) {
        return chattingRoomRepository.findRoomByRoomId(roomId);
    }

    @DeleteMapping("/turnOffAlarm")     // 알람끄는 API
    public ResponseEntity turnoffAlarm(@RequestBody HashMap<String, Object> param){
        String nickname = (String) param.get("nickname");
        User user = userRepository.findByNickname(nickname);

        String roomId = (String) param.get("roomId");

        ChattingRoomUser chattingRoomUser = chattingRoomUserRepository.findByChattingRoomIdAndUserId(roomId, user.getUserId());

        if (chattingRoomUser != null){
            if (chattingRoomUser.isAlarm()){
                chattingRoomUser.setAlarm(false);           // 알람 종료
                chattingRoomUserRepository.save(chattingRoomUser);
                return new ResponseEntity(HttpStatus.OK);
            } else {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "이미 알람이 꺼져있음"
                );
            }
        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "잘못된 요청, roomId나 유저 닉네임 재확인 필요"
            );
        }
    }


}