package com.example.demo.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class ChattingMessage {

    // 메시지 타입 : 입장, 채팅
//    public enum MessageType {
//        ENTER, TALK
//    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_message_id")
    private Long chatMessageId; // pk

    @Column(name = "sender")
    private Long sender; // 보낸 사람의 유저 id, 직접적으로 보여주진 않을 것이지만 기록하는데 사용

    @Column(name = "sender_nickname")
    private String senderNickName;

    @Column(name = "type")
    private Integer MessageType; // 메시지 타입 0 : ENTER, 1 : TALK

    @Column(name = "chatting_room_id")
    private String roomId; // 방번호
    @Column(name = "content")
    private String message; // 메시지

    @Column(name = "send_time")
    private LocalDateTime sendTime;


    @Builder
    public ChattingMessage(Long sender, String senderNickName, Integer messageType, String roomId, String message, LocalDateTime sendTime){
        this.sender = sender;
        this.senderNickName = senderNickName;
        this.MessageType = messageType;
        this.roomId = roomId;
        this.message = message;
        this.sendTime = sendTime;
    }
}