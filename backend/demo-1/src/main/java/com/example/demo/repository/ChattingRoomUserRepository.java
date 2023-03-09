package com.example.demo.repository;

import com.example.demo.domain.ChattingRoomUser;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChattingRoomUserRepository extends JpaRepository<ChattingRoomUser, Long> {

    List<ChattingRoomUser> findByUserId(Long user_id);
    List<ChattingRoomUser> findByUserId(Long user_id, Sort sort);       // 시간순정렬을 위해 추가
    List<ChattingRoomUser> findByChattingRoomId(String roomId);

    ChattingRoomUser findByChattingRoomIdAndUserId(String roomId, Long User_id);

    boolean existsByUserIdAndChattingRoomId (Long userId, String roomId);

}
