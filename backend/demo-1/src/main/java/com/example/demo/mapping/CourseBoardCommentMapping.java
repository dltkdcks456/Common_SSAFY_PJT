package com.example.demo.mapping;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

public interface CourseBoardCommentMapping {
    Long getCourseBoardCommentId();
    LocalDateTime getTime();
    String getContent();

    default String getUserNickname() {
        return getUserIdNickname();
    }
    default Long getUserId() { return getUserIdUserId(); }
    default Long getCourseBoardId() { return getCourseBoardIdCourseBoardId(); }
    @JsonIgnore
    String getUserIdNickname();

    @JsonIgnore
    Long getUserIdUserId();

    @JsonIgnore
    Long getCourseBoardIdCourseBoardId();


}
