package com.example.demo.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Data
@Table(name = "user_achievement")
public class UserAchievement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_achievement_id")
    private Long userAchievementId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "achievement_id")
    private Achievement achievementId;


    @Builder
    public UserAchievement(User userId, Achievement achievementId) {
//		this.userAchievementId = userAchievementId;
		this.userId = userId;
        this.achievementId = achievementId;
    }

}