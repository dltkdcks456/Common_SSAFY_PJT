package com.example.demo.mapping;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

public interface CommunityListMapping {
    Long getCommunityId();



    String getTitle();
    LocalDateTime getDate();

    String getSi();
    String getGun();
    String getDong();

    int getcurrentPerson();

    int getmaxPerson();
    boolean isinProgress();


    //Long getVisited();
    //int getBoardCode();
    //Long getCount();

    default String getUserNickname() {
        return getUserIdNickname();
    }


    default String getUserId() {
        return getUserIdUserId();
    }



    @JsonIgnore
    String getUserIdNickname();

    @JsonIgnore
    String getUserIdUserId();


}
