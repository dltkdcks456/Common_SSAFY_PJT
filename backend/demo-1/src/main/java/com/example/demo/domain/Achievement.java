package com.example.demo.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "achievement")
public class Achievement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "achievement_id")
    private Long achievementId;

    @Column(name = "name")
    private String name;

    @Column(name = "terms")
    private String terms;

    @Column(name = "achievement")
    private String achievement;


    @Builder
    public Achievement(Long achievementId, String name, String terms, String achievement) {
		this.achievementId = achievementId;
		this.name = name;
        this.terms = terms;
        this.achievement = achievement;
    }

}