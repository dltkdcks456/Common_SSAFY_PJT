package com.example.demo.mapping;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

public interface CourseBoardListMapping {
    Long getCourseBoardId();
    String getTitle();
    LocalDateTime getRegTime();
    Long getVisited();


    default String getUserNickname() {
        return getUserIdNickname();
    }

    @JsonIgnore
    String getUserIdNickname();
}
