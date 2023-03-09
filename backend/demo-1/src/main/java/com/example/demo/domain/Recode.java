package com.example.demo.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
public class Recode {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "total_speed")
    private Long totalSpeed;

    @Column(name = "total_dist")
    private Long totalDist;

    @Column(name = "total_cal")
    private Long totalCal;

    @Column(name = "total_time")
    private Long totalTime;

    @Column(name = "week_speed")
    private Long weekSpeed;

    @Column(name = "week_dist")
    private Long weekDist;

    @Column(name = "week_cal")
    private Long weekCal;

    @Column(name = "week_time")
    private Long weekTime;

    @Builder
    public Recode(Long userId, Long totalSpeed, Long totalDist, Long totalCal, Long totalTime, Long weekSpeed, Long weekDist, Long weekCal, Long weekTime){
        this.userId = userId;
        this.totalSpeed = totalSpeed;
        this.totalDist = totalDist;
        this.totalCal = totalCal;
        this.totalTime = totalTime;
        this.weekSpeed = weekSpeed;
        this.weekDist = weekDist;
        this.weekCal = weekCal;
        this.weekTime = weekTime;

    }



}
