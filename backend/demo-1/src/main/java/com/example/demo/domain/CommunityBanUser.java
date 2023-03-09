package com.example.demo.domain;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
public class CommunityBanUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "community_ban_user_id")
    private Long communityBanUserId;

    @Column(name = "community_id")
    private Long communityId;

    @Column(name = "user_id")
    private Long userId;

    @Builder
    public CommunityBanUser(Long communityId, Long userId){
        this.communityId = communityId;
        this.userId = userId;
    }
}
