package com.example.demo.mapping;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

public interface CommentMapping {
    Long getCommentId();
    LocalDateTime getTime();
    String getContent();

    default String getUserNickname() {
        return getUserIdNickname();
    }
    default Long getUserId() { return getUserIdUserId(); }
    default Long getBoardId() { return getBoardIdBoardId(); }
    @JsonIgnore
    String getUserIdNickname();

    @JsonIgnore
    Long getUserIdUserId();

    @JsonIgnore
    Long getBoardIdBoardId();


}
