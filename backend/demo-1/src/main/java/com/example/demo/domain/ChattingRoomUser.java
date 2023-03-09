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
public class ChattingRoomUser {
    @Id
    @Column(name = "chatting_room_user_pk")     // pk를 위해 존재함, 의미 x
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chattingRoomUserPk;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "chatting_room_id")
    private String chattingRoomId;

    @Column(name = "alarm")
    private boolean alarm;

    @Column(name = "recent_chatting_time")
    private LocalDateTime recentChattingTime;

    @Builder
    public ChattingRoomUser(Long userId, String chattingRoomId){
        this.userId = userId;
        this.chattingRoomId = chattingRoomId;
    }





}
