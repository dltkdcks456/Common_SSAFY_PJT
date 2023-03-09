package com.example.demo.mapping;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

public interface BoardListMapping {
    Long getBoardId();
    String getTitle();
    LocalDateTime getRegTime();
    Long getVisited();
    int getBoardCode();
    Long getCount();

    default String getUserNickname() {
        return getUserIdNickname();
    }

    @JsonIgnore
    String getUserIdNickname();
}
