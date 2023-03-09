package com.example.demo.controller;

import com.example.demo.domain.ChattingMessage;


import com.example.demo.domain.ChattingRoomUser;
import com.example.demo.domain.User;
import com.example.demo.repository.ChattingMessageRepository;
import com.example.demo.repository.ChattingRoomUserRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.springframework.web.bind.annotation.*;


import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Controller
@CrossOrigin("*")
public class ChatController {

    private final ChattingMessageRepository chattingMessageRepository;
    private final UserRepository userRepository;
    private final SimpMessageSendingOperations messagingTemplate;
    private final ChattingRoomUserRepository chattingRoomUserRepository;


//    @MessageMapping("/chat/message")    // 1대1 채팅용 메시지 전송 - 원본
//    public void message(ChattingMessage message) {
//
//        if (message.getMessageType().equals(0))         // 0이면 ENTER, 1이면 TALK
//            message.setMessage(message.getSender() + "님이 입장하셨습니다.");
//        messagingTemplate.convertAndSend("/topic/chat/room/" + message.getRoomId(), message);
//
////        User user = userRepository.findByNickname(message.getSender());
//
//        ChattingMessage new_message = ChattingMessage.builder() // 받은 메시지를 새 메시지로 DB에 저장한다.
//                .sender(message.getSender())
//                .messageType(message.getMessageType())
//                .roomId(message.getRoomId())
//                .message(message.getMessage())
//                .build();
//
//        chattingMessageRepository.save(new_message);
//    }

    @MessageMapping("/chat/message")    // 1대1 채팅용 메시지 전송 -> param으로 변환
    public void message(@RequestBody HashMap<String, String> param) {

        String userNickname = param.get("sender");
        User user = userRepository.findByNickname(userNickname);

        ChattingMessage new_message = ChattingMessage.builder() // 받은 메시지를 새 메시지로 DB에 저장한다.
                .sender(user.getUserId())
                .senderNickName(user.getNickname())
                .messageType(Integer.parseInt(param.get("messageType")))
                .roomId(param.get("roomId"))
                .message(param.get("message"))
                .sendTime(LocalDateTime.now())
                .build();

        if (new_message.getMessageType().equals(0))         // 0이면 ENTER, 1이면 TALK
            new_message.setMessage(new_message.getMessage()+ "  -------  " + userNickname + "님이 입장하셨습니다.");
        messagingTemplate.convertAndSend("/topic/chat/room/" + new_message.getRoomId(), new_message);

        chattingMessageRepository.save(new_message);

        List<ChattingRoomUser> chattingRoomUserList = chattingRoomUserRepository.findByChattingRoomId(param.get("roomId"));
        for(int i = 0; i < chattingRoomUserList.size(); i++){
            ChattingRoomUser chattingRoomUser = chattingRoomUserList.get(i);
            chattingRoomUser.setRecentChattingTime(LocalDateTime.now());
        }
    }

//    @PostMapping ("/testmessage")
//    protected void testmessage(ChattingMessage message) {
//        System.out.println("--------");
//        System.out.println(message.getMessage());
//        System.out.println(message.getMessageType());
//        System.out.println(message.getSender());
//        System.out.println(message.getRoomId());
//        System.out.println("--------");
//        message(message);
//    }

//    @MessageMapping("/chat/messageToCommunity")     // 1대 다수 채팅용 메시지 전송 - 원본
//    public void messageToCommunity(ChattingMessage message) {
//        if (message.getMessageType().equals(0))         // 0이면 ENTER, 1이면 TALK
//            message.setMessage(message.getSender() + "님이 입장하셨습니다.");
//        messagingTemplate.convertAndSend("/queue/chat/room/" + message.getRoomId(), message);
//
//
//        ChattingMessage new_message = ChattingMessage.builder() // 받은 메시지를 새 메시지로 DB에 저장한다.
//                .sender(message.getSender())
//                .messageType(message.getMessageType())
//                .roomId(message.getRoomId())
//                .message(message.getMessage())
//                .build();
//
//    }
@MessageMapping("/chat/messageToCommunity")    // 1대1 채팅용 메시지 전송 -> param으로 변환
public void messageToCommunity(@RequestBody HashMap<String, String> param) {

    String userNickname = param.get("sender");
    User user = userRepository.findByNickname(userNickname);


    ChattingMessage new_message = ChattingMessage.builder() // 받은 메시지를 새 메시지로 DB에 저장한다.
            .sender(user.getUserId())
            .senderNickName(user.getNickname())
            .messageType(Integer.parseInt(param.get("messageType")))
            .roomId(param.get("roomId"))
            .message(param.get("message"))
            .sendTime(LocalDateTime.now())
            .build();

    if (new_message.getMessageType().equals(0))         // 0이면 ENTER, 1이면 TALK
        new_message.setMessage(new_message.getMessage()+ "  -------  " + userNickname + "님이 입장하셨습니다.");
    messagingTemplate.convertAndSend("/queue/chat/room/" + new_message.getRoomId(), new_message);

    chattingMessageRepository.save(new_message);

    List<ChattingRoomUser> chattingRoomUserList = chattingRoomUserRepository.findByChattingRoomId(param.get("roomId"));
    for(int i = 0; i < chattingRoomUserList.size(); i++){
        ChattingRoomUser chattingRoomUser = chattingRoomUserList.get(i);
        chattingRoomUser.setRecentChattingTime(LocalDateTime.now());

        chattingRoomUserRepository.save(chattingRoomUser);
    }
}



    @GetMapping("/api/chat/callChattingDetail")                           // 특정 채팅방의 채팅 내역 모든 정보 날것으로 불러오기, sender가 유저 pk임
    @ResponseBody
    public List<ChattingMessage> callChattingDetail(String roomId){
        return chattingMessageRepository.findByRoomId(roomId);
    }

    @GetMapping("/api/chat/callChatting")                                 // 특정 채팅방의 채팅 내역 타입, 내용, 보낸사람 별명, 보낸시간 가져오기
    @ResponseBody
    public List<HashMap<String, Object>> callChatting(String roomId){
        List<HashMap<String, Object>> returnList = new ArrayList<>();

        List<ChattingMessage> chattings = chattingMessageRepository.findByRoomId(roomId);
        if (chattings.size() > 40) {                                        // 채팅 내역 40개만 불러오자
            for (int i = chattings.size() - 1; i > chattings.size() - 41; i--) {    // 제일 끝값부터 넣자
                HashMap<String, Object> map = new HashMap<String,Object>();
                try {
                    map.put("sender_id", userRepository.findByUserId(chattings.get(i).getSender()).getUserId());
                    map.put("sender_nickname", chattings.get(i).getSenderNickName());
                } catch (Exception e){
                    map.put("sender_id", "탈퇴한 유저");
                    map.put("sender_nickname", "탈퇴한 유저");
                }
                map.put("message", chattings.get(i).getMessage());
                map.put("messageType", chattings.get(i).getMessageType());
                map.put("sendTime", chattings.get(i).getSendTime());
                returnList.add(0,map);
            }
        } else {
            for (int i = chattings.size() - 1; i >= 0; i--) {
                HashMap<String, Object> map = new HashMap<String,Object>();
                try {
                    map.put("sender_id", userRepository.findByUserId(chattings.get(i).getSender()).getUserId());
                    map.put("sender_nickname", chattings.get(i).getSenderNickName());
                } catch (Exception e){
                    map.put("sender_id", "탈퇴한 유저");
                    map.put("sender_nickname", "탈퇴한 유저");
                }
                map.put("message", chattings.get(i).getMessage());
                map.put("messageType", chattings.get(i).getMessageType());
                map.put("sendTime", chattings.get(i).getSendTime());
                returnList.add(0,map);
            }
        }
        return returnList;
    }
}