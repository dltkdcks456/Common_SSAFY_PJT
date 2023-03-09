package com.example.demo.repository;

import com.example.demo.domain.ChattingRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface ChattingRoomRepository extends JpaRepository<ChattingRoom, UUID> {

//    private Map<String, ChatRoom> chatRoomMap;

//    @PostConstruct
//    private void init() {
//        chatRoomMap = new LinkedHashMap<>();
//    }

//    public List<ChatRoom> findAllRoom() {
//        // 채팅방 생성순서 최근 순으로 반환
//        List chatRooms = new ArrayList<>(chatRoomMap.values());
//        Collections.reverse(chatRooms);
//        return chatRooms;
//    }
    List<ChattingRoom> findAll();


//    public ChatRoom findRoomById(String id) {
//        return chatRoomMap.get(id);
//    }
    ChattingRoom findRoomByRoomId(String roomId);

//    public ChatRoom createChatRoom(String name) {
//        ChatRoom chatRoom = ChatRoom.create(name);
//        chatRoomMap.put(chatRoom.getRoomId(), chatRoom);
//        return chatRoom;
//    }
//    ChattingRoom createChatRoom(UUID uuid, String name);
}
