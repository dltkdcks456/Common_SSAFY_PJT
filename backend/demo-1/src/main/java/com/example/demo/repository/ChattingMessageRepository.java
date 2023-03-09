package com.example.demo.repository;

import com.example.demo.domain.ChattingMessage;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface ChattingMessageRepository extends JpaRepository<ChattingMessage, Long> {
    List<ChattingMessage> findByRoomId(String roomId);
    List<ChattingMessage> findByRoomId(String roomId, Sort sort);

}
