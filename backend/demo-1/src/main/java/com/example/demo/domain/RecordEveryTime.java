package com.example.demo.domain;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
public class RecordEveryTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_every_time_id")
    private Long recordEveryTime;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "time")
    private Long time;

    @Column(name = "calories")
    private Long calories;

    @Column(name = "distance")
    private Long distance;

    @Column(name = "speed")
    private Long speed;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Builder
    public RecordEveryTime(Long userId, Long time, Long speed, Long calories, Long distance, LocalDateTime updateTime){
//        this.recordEveryTime = recordEveryTime;
        this.userId = userId;
        this.time = time;
        this.speed = speed;
        this.calories = calories;
        this.distance = distance;
        this.updateTime = updateTime;
    }



}
