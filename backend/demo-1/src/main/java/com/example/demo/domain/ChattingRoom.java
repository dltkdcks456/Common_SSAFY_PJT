package com.example.demo.domain;



import lombok.Builder;
import lombok.Getter;

import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class ChattingRoom {
    @Id
    @Column(name = "chatting_room_id")
    private String roomId;
    @Column(name = "name")
    private String name;
    @Column(name = "room_type")
    private Integer roomType;

//    @Column(name = "writer")
//    private Integer writer; // user의 pk, user_id로 저장해두자.

//    @Column
//    private String content;

    @Builder
    public ChattingRoom(String roomId, String name, Integer roomType) {
        this.roomId = roomId;
        this.name = name;
        this.roomType = roomType;

    }



//    public static ChatRoom create(String name) {
//        ChatRoom chatRoom = new ChatRoom();
//        chatRoom.roomId = UUID.randomUUID().toString();
//        chatRoom.name = name;
//        return chatRoom;
//    }
}