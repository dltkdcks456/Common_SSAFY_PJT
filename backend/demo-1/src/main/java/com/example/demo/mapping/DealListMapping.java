package com.example.demo.mapping;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

public interface DealListMapping {
    Long getDealId();
    String getKind();
    String getTitle();
    String getContent();
    String getName();
    Long getPrice();
    boolean getOnSale();
    LocalDateTime getTime();
    Long getVisited();

    default String getUserNickname() {
        return getUserIdNickname();
    }


    @JsonIgnore
    String getUserIdNickname();
}
